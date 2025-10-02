package spring.service.user.impl;

import spring.domain.User;

/*
 * FileName : UserDAO.java
 * ㅇ DAO : Data Access Object
 * ㅇ Control 단의 독립적 Test 를 위하여 아래와 같은 Test 전략 사용.
 *  	-  	getUser() method 에서는 DBMS와 통신하지 않고 controller 에서 전달되는 
 *  		Domain Object  id !=null 이고 3자 이상이면  logon 유무를 확인하는 active 값을 true 로 처리
 *     - 각계층간 설계가 유연하다면, Control, Model(비지니스계증/퍼시스턴스계층) 각계층의 
 *       완성도가 높아진 다음에 통합 테스트를 진행하는 전략을 사용할 수 있다.
 */
public class UserDAO {
	
	///Field
	
	///Constructor
	public UserDAO() {
		System.out.println("UserDAO Default Constructor");
	}
	
	///Method
	public void  getUser(User user){
		
		System.out.println("[ UserDAO.getUser() start........]");
		
		//Domain Object Value 확인
		System.out.println( user.toString() );
		
		if( user.getUserId() != null && user.getUserId().length() >=3 
			&& user.getPassword() != null && user.getPassword().length() >=3	){
			user.setActive(true);
		}
		
		//Domain Object Value 확인
		System.out.println( user.toString() );
		
		//==> 본예제는 Presentation Layer 의 Controller 을 이해하는 예제
		//==> Business Layer(구체적으로 Persistence Layer)에서의  DAO는  
		//==> 아래의 역할은 진행 한 것으로 한다.
		/*
		 * 1. Connection 객체 획득( DataSource :: ConnectionPool)
		 * 2. Statement 객체 획득 (CRUD 수행)
		 * 3. ResultSet 객체 획득 (CRUD 결과 획득)
		 * 4. Domain Object 의 id / pwd 와 DBMS통신으로 얻은 Value 와 비교
		 * 5. Domain Object  active 변경 
		 */
		System.out.println("[ UserDAO.getUser() end........]");
		
	}//end of getUser()
	
}///end of class