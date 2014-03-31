package kr.nerv.antop.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.nerv.antop.entity.model.Attach;
import kr.nerv.antop.entity.model.Board;
import kr.nerv.antop.service.BoardService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardServlet {
	// log4j
	protected final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private BoardService service;
	// 암호글, 수정, 삭제 인증 정보를 잠시 가지고 있을 쿠키의 키
	// conf.properties 파일에서 설정한다.
	@Value("${cookie.view.key}")
	private String COOKIE_VIEW_KEY;
	@Value("${cookie.modify.key}")
	private String COOKIE_MODIFY_KEY;
	@Value("${cookie.delete.key}")
	private String COOKIE_DELETE_KEY;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws ServletException {
		// ?search=
		// 위와 같이 파라미터의 값이 없으면 값은 블링크("")이다.
		// 블링크로 들어온 파라미터 값을 null 로 바꾼다.
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}

//	@RequestMapping(value = "/list.do", method = RequestMethod.POST)
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView mav, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) String search) {

		if (page == null) {
			page = 1;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		// 페이지 번호
		map.put("page", page);
		// 검색어
		map.put("search", search);
		// inquire
		map.putAll(service.inquire(map));

		return mav.addAllObjects(map);
	}

	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public ModelAndView view(ModelAndView mav, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(required = false) Integer page,
			@RequestParam(value = "bid") int boardId, @RequestParam(required = false) String search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("search", search);

		// get
		Board board = service.inquire(boardId);

		if (board.getSecurity() == true) {
			Cookie cookie = getCookie(request, COOKIE_VIEW_KEY);

			if (cookie == null || board.getBoardId().equals(cookie.getValue())) {
				map.put("query", "view");
				map.put("boardId", boardId);
				mav.setViewName("secret");
				return mav.addAllObjects(map);
			}

			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		// hit + 1
		service.increaseCount(board);

		map.put("board", board);
		return mav.addAllObjects(map);
	}

	@RequestMapping(value = "/delete.do", method = RequestMethod.GET)
	public ModelAndView delete(ModelAndView mav, HttpServletRequest request,
			@RequestParam(value = "bid") Integer boardId,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String search) {
		mav.setViewName("redirect:list.do");
		mav.addObject("boardId", boardId);
		mav.addObject("page", page);
		mav.addObject("search", search);

		Cookie cookie = getCookie(request, COOKIE_DELETE_KEY);
		if (cookie == null) {
			mav.addObject("query", "delete");
			mav.setViewName("secret");
		} else {
			// 삭제
			service.remove(boardId);
		}

		return mav;
	}

	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response,
			ModelAndView mav, @RequestParam String query,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String search,
			@RequestParam(value = "bid", required = false) Integer boardId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("query", query);
		map.put("page", page);
		map.put("search", search);
		map.put("boardId", boardId);

		Board board = null;

		if (boardId != null) { // 이 글을 수정할 권한이 있는지 체크

			if (query.equals("modify")) {
				Cookie cookie = getCookie(request, COOKIE_MODIFY_KEY);

				// 쿠키가 아예 없거나, 쿠키에 등록된 수정 권한이 있는 게시물 번호가 아니면 암호 페이지로 이동
				if (cookie == null || !cookie.getValue().equals(boardId.toString())) {
					mav.setViewName("secret");
				} else { // 수정 권한이 확인되면 게시물 내용 불러오기 Board
					board = service.inquire(boardId);
					// 쿠키 삭제
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}

			if (query.equals("reply")) {
				board = service.inquire(boardId);
				board.setAuthor(null);
				board.setSubject(null);
				board.setContent(null);
				board.setAttachs(new ArrayList<Attach>());
			}

			mav.addObject("board", board);
		}

		return mav.addAllObjects(map);
	}

	@RequestMapping(value = "/proc.do", method = RequestMethod.POST)
	public ModelAndView proc(ModelAndView mav, @RequestParam String query,
			@RequestParam(required = false, value = "bid") Integer boardId,
			@RequestParam String subject, @RequestParam String author, @RequestParam String pwd,
			@RequestParam(required = false) String newPwd,
			@RequestParam(required = false) Boolean security,
			@RequestParam(required = false) String content,
			@RequestParam(value = "file_seq", required = false) Integer[] seqs) {
		mav.setViewName("redirect:list.do");

		if (security == null) {
			security = false;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("query", query);
		map.put("boardId", boardId);
		map.put("subject", subject);
		map.put("author", author);
		map.put("pwd", pwd);
		map.put("newPwd", newPwd);
		map.put("security", security);
		map.put("content", content);
		map.put("seqs", seqs);

		service.save(map);

		return mav;
	}

	// ajax
	@RequestMapping(value = "/auth.do", method = RequestMethod.POST)
	public ModelAndView auth(HttpServletRequest request, HttpServletResponse repsonse,
			ModelAndView mav, @RequestParam(value = "board_id") Integer boardId,
			@RequestParam String pwd, @RequestParam(required = false) String query) {
		mav.setViewName("ajax/auth");

		boolean auth = true;
	
		try {
			// 비밀번호 검증
			// 비밀번호가 틀리면 예외를 던진다.
			service.auth(boardId, pwd);

			// query 파라미터가 없으면 쿠키 관련 작업을 안한다.
			if (query != null) {
				Cookie cookie = null;
				if ("view".equals(query)) {
					cookie = new Cookie(COOKIE_VIEW_KEY, boardId.toString());
				} else if ("modify".equals(query)) {
					cookie = new Cookie(COOKIE_MODIFY_KEY, boardId.toString());
				} else if ("delete".equals(query)) {
					cookie = new Cookie(COOKIE_DELETE_KEY, boardId.toString());
				}

				if (cookie != null) {
					repsonse.addCookie(cookie);
				}

			}

		} catch (Exception e) {
			auth = false;
			mav.addObject("msg", e.getMessage());

			e.printStackTrace();
		}

		mav.addObject("auth", auth);

		return mav;
	}

	private Cookie getCookie(HttpServletRequest request, String key) {
		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					return cookie;
				}
			}
			return null;
		} catch (NullPointerException e) {

		}

		return null;
	}
}
