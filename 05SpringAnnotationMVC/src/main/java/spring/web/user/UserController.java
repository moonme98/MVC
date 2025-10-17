package spring.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.domain.User;

@Controller
public class UserController {
	
	public UserController() {
		System.out.println("==> UserController default constructor call...");
	}
	
	// 00: 단순 Navigation / Business Logic 수행없음
	@RequestMapping("/logonViewModelAndView.do")
	public ModelAndView logonViewModelAndView() {
		System.out.println("::==> logonViewModelAndView() start...");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/logonView.jsp");
		
		return modelAndView;
	}
	
	// 01: HttpServletRequest 를 이용한 Client Data 처리
	//   : HttpServletRequest 를 이용한 HttpSession 처리
	@RequestMapping("/logon01.do")
	public ModelAndView logon01(HttpServletRequest request) {
		System.out.println("::==> logon01() start...");
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		request.setAttribute("userId", userId);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("password", password);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Controller 구현 연습.");
		
		modelAndView.setViewName("/user/logonResult.jsp");
		
		return modelAndView;
	}
	
	// 02: HttpServletRequest, HttpSession 이용 처리
	@RequestMapping("/logon02.do")
	public ModelAndView logon02(HttpServletRequest request, HttpSession session) {
		System.out.println("::==> logon02() start...");
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		request.setAttribute("userId", userId);
		
		session.setAttribute("password", password);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Controller 구현 연습.");
		
		modelAndView.setViewName("/user/logonResult.jsp");
		
		//modelAndView.setViewName("redirect:/user/logonResult.jsp");
		
		return modelAndView;
	}
		
	// 03: HttpServletRequest, HttpSession, @RequestParam 이용 처리
	@RequestMapping("/logon03.do")
	public ModelAndView logon03( @RequestParam("userId") String userId,
									@RequestParam("password") String password,
									HttpServletRequest request, HttpSession session) {
		System.out.println("::==> logon03() start...");
		
		request.setAttribute("userId", userId);
		
		session.setAttribute("password", password);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Controller 구현 연습.");
		
		modelAndView.setViewName("/user/logonResult.jsp");
		
		return modelAndView;
	}
	
	// 04: HttpServletRequest, HttpSession 이용 처리
	//   : @ModelAttribute 사용
	@RequestMapping("/logon04.do")
	public ModelAndView logon04( @ModelAttribute("user") User user,
									HttpServletRequest request, HttpSession session) {
		System.out.println("::==> logon04() start...");
		
		request.setAttribute("userId", user.getUserId());
		session.setAttribute("password", user.getPassword());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Controller 구현 연습.");
		modelAndView.setViewName("/user/logonResult.jsp");
		
		return modelAndView;
	}
	
	// 05: @ModelAttribute 사용
	@RequestMapping("/logon05.do")
	public ModelAndView logon05( @ModelAttribute("user") User user) {
		System.out.println("::==> logon05() start...");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Controller 구현 연습.");
		modelAndView.setViewName("/user/logonResult.jsp");
		
		return modelAndView;
	}
	
	
	
	
	// 00: 단순 Navigation / Business Logic 수행없음
	@RequestMapping("/logonViewString.do")
	public String logonVoewString() {
		System.out.println("::==> logonVoewString() start...");
		
		return "/user/logonView.jsp";
	}
	
	// 06: HttpServletRequest, HttpSession 이용 처리
	//   : @ModelAttribute 사용
	@RequestMapping("/logon06.do")
	public String logon06( @ModelAttribute("user") User user,
								HttpServletRequest request, HttpSession session,
								Map<String, String>map ) {
		System.out.println("::==> logon06() start...");
		
		request.setAttribute("userId", user.getUserId());
		session.setAttribute("password", user.getPassword());
		
		map.put("message", "Controller 구현 연습.");
		
		return "/user/logonResult.jsp";
	}
	
	// 07: @RequestParam
	@RequestMapping("/logon07.do")
	public String logon07( @RequestParam("userId") String userId,
								@RequestParam("password") String password,
								Model model ) {
		System.out.println("::==> logon07() start...");
		
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		
		model.addAttribute("userId", userId);
		model.addAttribute("password", password);
		
		model.addAttribute("message", "Controller 구현 연습.");
		
		return "/user/logonResult.jsp";
	}
}

