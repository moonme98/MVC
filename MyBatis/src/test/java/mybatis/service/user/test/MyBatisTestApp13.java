package mybatis.service.user.test;

import java.util.ArrayList;

import mybatis.service.domain.Search;
import mybatis.service.domain.User;
import mybatis.service.user.UserService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * FileName : MyBatisTestApp13.java
  * :: Business Layer unit Test : Service + Persistence (Spring +mybatis + DAO)
  */
public class MyBatisTestApp13 {
	
	///main method
	public static void main(String[] args) throws Exception{

		ApplicationContext context =
			new ClassPathXmlApplicationContext(
																new String[] {	"/config/commonservice13.xml",
																					"/config/userservice13.xml" }
								                                  );
		System.out.println("\n");

		//==> Bean/IoC Container 로 부터 획득한 UserService 인스턴스 획득
		UserService userService = (UserService)context.getBean("userServiceImpl12");
		
		System.out.println("\n");
		
		//==> Test 용 User instance 생성  
		User user = new User("user04","주몽","user04",null,0);

		//1. addUser Test  
		System.out.println(":: 1. addUser(INSERT)  ? ");
		System.out.println(":: "+ userService.addUser(user) ); 
		System.out.println("\n");
		
		//2. getUser Test :: 주몽 inert 확인 
		System.out.println(":: 2. getUser(SELECT)  ? ");
		System.out.println(":: "+ userService.getUser(user.getUserId()) );
		System.out.println("\n");

		//3. uadateUser Test  :: 주몽 ==> 온달 수정
		user.setUserName("온달");
		System.out.println(":: 3. update(UPDATE)  ? ");
		System.out.println(":: "+ userService.updateUser(user));
		System.out.println("\n");
		
		//4. getUserList Test ::
		System.out.println(":: 4. getUserList(SELECT)  ? ");
		Search search = new Search();
		search.setSearchCondition("userId");
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("user04");
		search.setUserId( arrayList );
		
		System.out.println("List<User> 내용 : "+userService.getUserList(search) );
		System.out.println("\n");
		
		//5. removeUser Test
		System.out.println(":: 5. removeUser (DELETE)  ? ");
		System.out.println(":: "+userService.removeUser(user.getUserId()) );
		System.out.println("\n");
		
		//6. getUserList Test 
		System.out.println(":: 6. getUserList(SELECT)  ? ");
		System.out.println("List<User> 내용 : "+ userService.getUserList(search) );
		System.out.println("\n");
	
	}//end of main
}//end of class