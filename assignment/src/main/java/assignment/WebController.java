package assignment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
 
@Controller
public class WebController {
 
	@RequestMapping("/")
	ModelAndView home(ModelAndView modelAndView) {
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
	@RequestMapping("/backend-csv")
	ModelAndView backendCsv(ModelAndView modelAndView) {
		modelAndView.setViewName("backend-csv");
		return modelAndView;
	}
	
	@RequestMapping("/backend-xml")
	ModelAndView backendXML(ModelAndView modelAndView) {
		modelAndView.setViewName("backend-xml");
		return modelAndView;
	}
}
