package kr.nerv.antop.servlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainServlet {
	// log4j
	protected final Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public ModelAndView main(ModelAndView mav) {
		mav.setViewName("main");
		logger.info("main() ");
		return mav;
	}
	
	@RequestMapping(value = "/blank.do", method = RequestMethod.GET)
	public ModelAndView blank(ModelAndView mav) {
		mav.setViewName("common/blank");
		logger.info("blank() ");
		return mav;
	}
}
