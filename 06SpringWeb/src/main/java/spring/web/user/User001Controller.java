package spring.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 *  FileName : User001Controller.java
 *  - Controller Coding Policy 
 *  	: return type : ModelAndView 적용 
 *  	: Method parameter  
 *  		- Client Data : @ModelAttribute / @RequestParam 적절이 사용
 *  	    - 필요시 : Servlet API 사용
 */
//@Controller
public class User001Controller {
	
	///Field
	
	///Constructor
	public User001Controller(){
		System.out.println("==> User001Controller default Constructor call.............");
	}
	
	// 단순 Navigation / Business Logic 수행없음
	@RequestMapping("/logon.do")
	public ModelAndView logon(){
		
		System.out.println("\n:: ==> logon() start....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "/user001/logon.jsp");
		
		return modelAndView;
	}
	
	// 단순 Navigation / Business Logic 수행없음
	@RequestMapping("/home.do")
	public ModelAndView home(){
		
		System.out.println("\n:: ==> home() start....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "/user001/home.jsp");
		
		return modelAndView;
	}
	
	// 단순 Navigation / Business Logic 수행없음
	@RequestMapping("/logonAction.do")
	public ModelAndView logonAction(){
		
		System.out.println("\n:: ==> logonAction() start....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "/user001/logonAction.jsp");
		
		return modelAndView;
	}
	
	// 단순 Navigation / Business Logic 수행없음
	@RequestMapping("/logout.do")
	public ModelAndView logout(){
		
		System.out.println("\n:: ==> logout() start....");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "/user001/logout.jsp");
		
		return modelAndView;
	}
	
}