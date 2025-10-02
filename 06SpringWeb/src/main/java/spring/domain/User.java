package spring.domain;

import java.io.Serializable;

/*
 * FileName : User.java
 *  ㅇ 사용자 정보를 추상화 한 Bean(Value Object)
 *  ㅇ Control 에서 Model 로  (client data  ==> Model 로) 
 *  ㅇ Model 에서 Control 로  ( Model 에서 data 처리결과 ==> Control 로 ) 
 *      즉 Layer(Presentation / Business)간 Data 이동 ( Data Transfer Object :: DTO )
 *  ㅇ POJO(Plain Old Java Object)
*/
public class  User implements Serializable{
	
	///Field
	private String userId;
	private String password;
	private boolean active;

	///Constructor
	public User(){
		System.out.println("UserVO Default Constructor");
	}

	///Method(getter/setter Method)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "User [active=" + active + ", userId=" + userId
				+ ", password=" + password + "]";
	}
}//end of class