package test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/*
 * FileName : HelloSpringController
 *  ㅇ Client 의 요청을 처리하는 Controller
 *  ㅇ SpringMVC 에서 Controller 는  
 *  	 org.springframework.web.servlet.mvc.Controller  구현 
 *  ㅇ 오버라이딩할 HandleRequest() Method 는 HttpServletRequest, HttpServletResponse
 *      객체를 인자로 받아... Controller 역할을 수행
 *   ㅇ Client 에 보여줄 Data(Model) / Client 에 보여줄 View 정보를	갖는       
 *       org.springframework.web.servlet.ModelAndView 객체 return 
 */
public class HelloSpringController implements Controller {

	///Method
	public ModelAndView handleRequest(	HttpServletRequest request ,
																HttpServletResponse response) throws Exception {
		
		System.out.println("::"+getClass().getName()+".handleRequest() start....");
		
		//==>RequestObjectScope 에 저장.
		request.setAttribute("request", new String("REQUEST_SCOPE 저장확인"));
		
		//==>SessionObjectScope 에 저장
		HttpSession session = request.getSession(true);
		session.setAttribute("session", new String("SESSION_SCOPE 저장확인"));
		
		//==>ApplicationObjectScope 에 저장
		ServletContext application = session.getServletContext();
		application.setAttribute("application",	 new String("APPLICATION_SCOPE 저장확인"));
		
		System.out.println("::"+getClass().getName()+".handleRequest() end....");
		
		ModelAndView modelAndView = new ModelAndView();
		//==> forward 될 화면 지정(View 지정)
		modelAndView.setViewName("/test/hello.jsp");
		//==> 화면에 사용될 Data 저장 (내부적으로 request Object Scope 사용)
		modelAndView.addObject("message", "Hi Spring MVC(reqeust ObjectScope 사용함)");
		return modelAndView;
		
		//==> 또는 아래와 같이 해도...
		//return new ModelAndView("/test/hello.jsp","message","Hi Spring MVC");
		// test/home.jsp	: viewName 		==> /test01/home.jsp
		// message 			: modelName 	==> requestScope에 저장된 attributeName
		// message 			: ModelObject 	==> requestScope에 저장된 attributeValue
	}
	
}//end of class