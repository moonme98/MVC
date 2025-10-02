package spring.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.domain.User;
import spring.service.user.impl.UserDAO;

/*
 *  FileName : User001Controller.java
 *  - Controller Coding Policy 
 *  	: return type : ModelAndView 적용 
 *  	: Method parameter  
 *  		- Client Data : @ModelAttribute / @RequestParam 적절이 사용
 *  	    - 필요시 : Servlet API 사용
 */
//@Controller
public class User003Controller {
	
	///Field
	
	///Constructor
	public User003Controller(){
		System.out.println("==> User002Controller default Constructor call.............");
	}
	
	// 권한(logon 유무) 확인 / navigation
	// logon
	@RequestMapping("/logon.do")
	public ModelAndView logon(HttpSession session){
		
		System.out.println("[logon() start....]");
		
		if(session.isNew() || session.getAttribute("sessionUser") == null) {
			session.setAttribute("sessionUser", new User());
		}
		User sessionUser = (User)session.getAttribute("sessionUser");
		
		String viewName = "/user002/logon.jsp";
		
		if(sessionUser.isActive()) {
			viewName = "/user002/home.jsp";
		}
		System.out.println("[ action : " +viewName+ "]");
		
		String message = null;
		if( viewName.equals("/user002/home.jsp")) {
			message = "[logon()] WELCOME";
		} else {
			message = "[logon()] 아이디, 패스워드 3자이상 입력하세요.";
		}
				
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		modelAndView.addObject("message", message);
		
		System.out.println("[logon() end...]\n");
		
		return modelAndView;
	}
	
	// 권한(logon 유무) 확인 / navigation
	// home
	@RequestMapping("/home.do")
	public ModelAndView home(HttpSession session){
		
		System.out.println("[home() start....]");
		
		if(session.isNew() || session.getAttribute("sessionUser") == null) {
			session.setAttribute("sessionUser", new User());
		}
		User sessionUser = (User)session.getAttribute("sessionUser");
		
		String viewName = "/user002/logon.jsp";
		
		if(sessionUser.isActive()) {
			viewName = "/user002/home.jsp";
		}
		System.out.println("[ action : " +viewName+ "]");
		
		String message = null;
		if( viewName.equals("/user002/home.jsp")) {
			message = "[home()] WELCOME";
		} else {
			message = "[home()] 아이디, 패스워드 3자이상 입력하세요.";
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		modelAndView.addObject("message", message);
		
		System.out.println("[home() end...]\n");
		
		return modelAndView;
	}
	
	// 단순 Navigation
	@RequestMapping(value="/logonAction.do", method=RequestMethod.GET)
	public ModelAndView logonAction() {
		
		System.out.println("[logonAction() method=RequestMethod.GET start....]");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/logon.do");
		
		System.out.println("[logonAction() method=RequestMethod.GET end...]\n");
		
		return modelAndView;
	}
	
	// Business Logic 수행 / Navigation
	// logonAction
	@RequestMapping(value="/logonAction.do", method=RequestMethod.POST)
	public ModelAndView logonAction( @ModelAttribute("user") User user,
										HttpSession session ){
		
		System.out.println("[logonAction() method=RequestMethod.POST start....]");
		
		//if(session.isNew() || session.getAttribute("sessionUser") == null) {
		//	session.setAttribute("sessionUser", new User());
		//}
		//User sessionUser = (User)session.getAttribute("sessionUser");
		
		String viewName = "/user002/logon.jsp";
		
		//if(sessionUser.isActive()) {
			//viewName = "/user002/home.jsp";
		//} else {
			
			UserDAO userDAO = new UserDAO();
			userDAO.getUser(user);
			
			if(user.isActive()) {
				viewName = "/user002/home.jsp";
				session.setAttribute("sessionUser", user);
			}
		//}
		System.out.println("[ action : " +viewName+ "]");
		
		String message = null;
		if( viewName.equals("/user002/home.jsp")) {
			message = "[logonAction()] WELCOME";
		} else {
			message = "[logonAction()] 아이디, 패스워드 3자이상 입력하세요.";
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(viewName);
		modelAndView.addObject("message", message);
		
		System.out.println("[logonAction() method=RequestMethod.POST end...]\n");
		
		return modelAndView;
	}
	
	// 권한(logon 정보삭제) 확인 / navigation
	// logout
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session){
		
		System.out.println("[logout() start....]");
		
		session.removeAttribute("sessionUser");
		
		String message = "[logout()] 아이디, 패스워드 3자이상 입력하세요.";
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user002/logon.jsp");
		modelAndView.addObject("message", message);
		
		System.out.println("[logout() end...]\n");
		
		return modelAndView;
	}
}