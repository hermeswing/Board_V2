package kr.nerv.antop.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.nerv.antop.entity.model.Attach;
import kr.nerv.antop.service.FileService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StringMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileServlet {
	// log4j
	protected final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private FileService service;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
	}

	// ajax
	@RequestMapping(value = "file/upload.do", method = RequestMethod.POST)
	public ModelAndView upload(HttpSession session, ModelAndView mav, @RequestParam MultipartFile file) {
		mav.setViewName("ajax/file/upload");
		
		Attach attach = service.save(file, session.getId());
		mav.addObject("attach", attach);

		return mav;
	}

	@RequestMapping(value = "file/download.do", method = RequestMethod.GET)
	public ModelAndView download(@RequestParam int seq) {
		Attach Attach = service.inquire(seq);
		// bean name viww
		return new ModelAndView("download", "attach", Attach);
	}

	// ajax
	@RequestMapping(value = "file/unload.do", method = RequestMethod.POST)
	public ModelAndView unload(ModelAndView mav, @RequestParam int seq, @RequestParam String session) throws Exception {
		mav.setViewName("ajax/file/unload");
		
		boolean success = true;

		try {
			service.remove(seq, session);
		} catch (Exception e) {
			success = false;
			mav.addObject("msg", e.getMessage());
		}

		mav.addObject("success", success);
	
		return mav;
	}

}
