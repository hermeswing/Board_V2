package kr.nerv.antop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import kr.nerv.antop.entity.mapper.AttachMapper;
import kr.nerv.antop.entity.mapper.BoardMapper;
import kr.nerv.antop.entity.model.Attach;
import kr.nerv.antop.entity.model.Board;
import kr.nerv.antop.entity.model.BoardExample;
import kr.nerv.antop.service.BoardService;

import org.apache.ibatis.binding.BindingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import util.mysql.SecurityUtil;

@Service
public class BoardServiceImpl implements BoardService {
	// log4j
	protected final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private TransactionTemplate tx;
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private AttachMapper fileMapper;
	@Value("${row.size}")
	private int ROW_SIZE = 10;

	@Override
	public Map<String, Object> inquire(Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// condition
		BoardExample example = new BoardExample();
		// subject like :search
		if (map.get("search") != null) {
			example.createCriteria().andSubjectLike("%" + map.get("search") + "%");
		}
		example.setOrderByClause("thread asc");
		// paging
		Integer page = (Integer) map.get("page");

		example.setStart((page - 1) * ROW_SIZE);
		example.setLimit(ROW_SIZE);

		// execute query
		List<Board> list = boardMapper.selectByExample(example);
		returnMap.put("list", list);

		int total = boardMapper.countByExample(example);
		returnMap.put("total", total);

		returnMap.put("rowSize", ROW_SIZE);

		return returnMap;
	}

	@Override
	public Board inquire(int boardId) {
		return boardMapper.selectByPrimaryKey(boardId);
	}

	@Override
	public void increaseCount(Board board) {
		// 암호를 그대로 업데이트 치면 또 암호화가 되어버린다.
		// 그러므로 널로 세팅
		board.setPwd(null);
		board.setHit(board.getHit() + 1);

		// 필드 값이 널인거는 업데이트 안한다.
		boardMapper.updateByPrimaryKeySelective(board);
	}

	@Override
	public void save(final Map<String, Object> map) {
		// 트랜잭션 시작
		Object o = tx.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				// 게시글에 대한 처리
				try {
					String query = (String) map.get("query");

					Double thread = 9999999.0000;

					Board board = null;
					if (((Integer) map.get("boardId")) != null) {
						board = boardMapper.selectByPrimaryKey((Integer) map.get("boardId"));

						if ("modify".equals(query)) {
							thread = board.getThread();
						}

						if ("reply".equals(query)) {
							thread = board.getThread() + 0.0001;
						}
					}

					if (board == null) {
						board = new Board();
						// 소수점을 없앤(.intValue()) 수에서 -1 한다.
						try {
							thread = boardMapper.minThread();
							thread = new Double(thread.intValue() - 1);
						} catch (BindingException e) {
							// 게시물을 처음 올릴때 min(thread) 가 널을 리턴하함
						}
					}

					board.setThread(thread);
					board.setAuthor((String) map.get("author"));
					board.setPwd((String) map.get("pwd"));
					board.setSubject((String) map.get("subject"));
					board.setSecurity((Boolean) map.get("security"));
					board.setContent((String) map.get("content"));

					if ("write".equals(query) || "reply".equals(query)) {
						board.setBoardId(null);
						board.setCreated(new Date());
						board.setHit(0);
						board.setDept("reply".equals(query) ? board.getDept() + 1 : 0);
					}

					// 수정일때 세팅
					if ("modify".equals(query)) {
						String newPwd = (String) map.get("newPwd");
						if (newPwd != null && newPwd.trim().length() > 0) {
							board.setPwd((String) map.get("newPwd"));
						}

						board.setModified(new Date());
					}

					int row = boardMapper.updateByPrimaryKey(board);
					if (row == 0) {
						boardMapper.insert(board);
					}

					// 첨부된 파일 처리
					Integer[] seqs = (Integer[]) map.get("seqs");
					if (seqs != null && seqs.length > 0) {
						for (Integer seq : seqs) {
							Attach Attach = new Attach();
							Attach.setSeq(seq);
							Attach.setBoardId(board.getBoardId());
							Attach.setDelFlag(false);

							fileMapper.updateByPrimaryKeySelective(Attach);
						}
					}
				} catch (Exception e) {
					// 에러가 발생하면 롤백
					status.setRollbackOnly();
					return e;
				}

				return null;
			}
		});

		if (o instanceof Throwable) {
			throw new RuntimeException((Exception) o);
		}

	}

	@Override
	public void auth(int boardId, String pwd) throws Exception {
		Board board = boardMapper.selectByPrimaryKey(boardId);

		String encodePwd = SecurityUtil.password(pwd);

		if (!board.getPwd().equals(encodePwd)) {
			throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
		}

	}
	
	@Override
	public void remove(int boardId) {
		boardMapper.deleteByPrimaryKey(boardId);
	}
}
