package spring.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.domain.User;
import spring.service.user.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	///Field
	@Autowired
	@Qualifier("userService")
	public UserService userService;
	
	///Constructor
	public UserController(){
		System.out.println("==> UserController default Constructor call.............");
	}
	
	// 단순 navigation
	// logon
	@RequestMapping("/logon")
	public String logon(Model model){
		
		System.out.println("[logon() start....]");
		
		String message = "[logon()]";
				
		model.addAttribute("message", message);
		
		System.out.println("[logon() end...]\n");
		
		return "/user/logon.jsp";
	}
	
	// 단순 navigation
	// home
	@RequestMapping("/home")
	public String home(Model model){
		
		System.out.println("[home() start....]");
		
		String message = "[home()] WELCOME";

		model.addAttribute("message", message);
		
		System.out.println("[home() end...]\n");
		
		return "/user/home.jsp";
	}
	
	// Business Logic 수행 / Navigation
	// logonAction
	@RequestMapping(value="/logonAction", method=RequestMethod.POST)
	public String logonAction( @ModelAttribute("user") User user,
								Model model, HttpSession session ) throws Exception {
		
		System.out.println("[logonAction() method=RequestMethod.POST start....]");
		
		String viewName = "/user/logon.jsp";
		String message  = "[logonAction()] 아이디, 패스워드 3자이상 입력하세요.";
	
		/*
		 * User returnUser = userService.getUser(user.getUserId()); if(
		 * returnUser.getPassword().equals(user.getPassword())) {
		 * returnUser.setActive(true); user = returnUser; }
		 * 
		 * if(user.isActive()) { viewName = "/user/home.jsp";
		 * session.setAttribute("sessionUser", user); }
		 * 
		 * System.out.println("[ action : " +viewName+ "]");
		 * 
		 * String message = null; if( viewName.equals("/user/home.jsp")) { message =
		 * "[logonAction()] WELCOME"; } else { message =
		 * "[logonAction()] 아이디, 패스워드 3자이상 입력하세요."; }
		 */
		
		if( user.getUserId() != null && user.getUserId().length() >= 3
	        && user.getPassword() != null && user.getPassword().length() >= 3 ) {
	        
	        user.setActive(true);
	        session.setAttribute("sessionUser", user);

	        viewName = "/user/home.jsp";
	        message  = "[logonAction()] WELCOME";
	    }
		
		model.addAttribute("message", message);
		
		System.out.println("[logonAction() method=RequestMethod.POST end...]\n");
		
		return viewName;
	}
	
	// 권한(logon 정보삭제) 확인 / navigation
	// logout
	@RequestMapping("/logout")
	public String logout(Model model, HttpSession session){
		
		System.out.println("[logout() start....]");
		
		session.removeAttribute("sessionUser");
		
		String message = "[logout()] 아이디, 패스워드 3자이상 입력하세요.";
		
		model.addAttribute("message", message);
		
		System.out.println("[logout() end...]\n");
		
		return "/user/logon.jsp";
	}
}