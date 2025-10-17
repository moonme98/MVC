package spring.web.user;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.domain.User;
import spring.service.user.UserService;

/*
 *  FileName : User001Controller.java
 *  - Controller Coding Policy 
 *  	: return type : ModelAndView 적용 
 *  	: Method parameter  
 *  		- Client Data : @ModelAttribute / @RequestParam 적절이 사용
 *  	    - 필요시 : Servlet API 사용
 */
@Controller
public class UserController {
	
	///////////////////////////////////////////
	///Field
	@Autowired
	@Qualifier("userService")
	public UserService userService;
	//////////////////////////////////////////
	
	///Constructor
	public UserController(){
		System.out.println("==> UserController default Constructor call.............");
	}
	
	// 단순 navigation
	// logon
	@RequestMapping("/logon.do")
	public ModelAndView logon(){
		
		System.out.println("[logon() start....]");
		
		String message = "[logon()]";
				
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/logon.jsp");
		modelAndView.addObject("message", message);
		
		System.out.println("[logon() end...]\n");
		
		return modelAndView;
	}
	
	// 단순 navigation
	// home
	@RequestMapping("/home.do")
	public ModelAndView home(HttpSession session){
		
		System.out.println("[home() start....]");
		
		String message = "[home()] WELCOME";

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/user/home.jsp");
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
										HttpSession session ) throws Exception {
		
		System.out.println("[logonAction() method=RequestMethod.POST start....]");
		
		String viewName = "/user/logon.jsp";
	
		////////////////////////////////////////////////////
		//UserDAO userDAO = new UserDAO();
		//userDAO.getUser(user);
		User returnUser = userService.getUser(user.getUserId());
		if( returnUser.getPassword().equals(user.getPassword())) {
			returnUser.setActive(true);
			user = returnUser;
		}
		///////////////////////////////////////////////////
		
		if(user.isActive()) {
			viewName = "/user/home.jsp";
			session.setAttribute("sessionUser", user);
		}
		
		System.out.println("[ action : " +viewName+ "]");
		
		String message = null;
		if( viewName.equals("/user/home.jsp")) {
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
		modelAndView.setViewName("/user/logon.jsp");
		modelAndView.addObject("message", message);
		
		System.out.println("[logout() end...]\n");
		
		return modelAndView;
	}
}