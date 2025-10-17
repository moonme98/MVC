package spring.service.domain.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import spring.service.domain.User;

/*
 * FileName : UserTestApp01.java
 */
public class UserTestApp01 {
	
	///Main Method
	public static void main(String[] args){
		
		//==> Bean / IoC Container : 객체생성,소멸 및 Dependency Injection 관리
		//==> Spring Framework 에서는 상속관계를 갖는 아래의 Container 제공.
		//==> ㅇ BeanFacory : 기본적 인스턴스 생성과 의존성 주입 Container
		//==> ㅇ ApplicationContext : BeanFactory 상속하여  국제화(i18n),
		//==>                                             이벤트 리슨너 등록 등등... 추가적 기능.....
		
		//1. BeanFactory 이용 xml 에 선언적,서술적 기술된 instance 생성 및 의존성주입
		// - BeanFactory는 getBean() 호출시 Bean 생성( lazy loading) <== console 에 확인 할것.
		BeanFactory factory =
				new XmlBeanFactory( new FileSystemResource
															("./src/main/resources/config/userservice01.xml") );

		//2. ApplicatiContext 이용 xml 에 선언적,서술적 기술된 instance 생성 및 의존성주입
				// - ApplicatiContext는 모든 인스턴스를 pre-loading  <== console 에 확인 할것.		
		//ApplicationContext factory =
		//		new ClassPathXmlApplicationContext("/config/userservice01.xml");

		//2. Container 로 부터 각각의 id 를 갖는 instance Look Up 
		//	- Dependency Lookup / Dependency Injection 확인...
		System.out.println("\n=======================");
		User user01 = (User)factory.getBean("user01");
		System.out.println(user01);
		
		System.out.println("======================="); 
		User user02 = (User)factory.getBean("user02");
		System.out.println(user02);
		
		System.out.println("=======================");
		User user03 = (User)factory.getBean("user03");
		System.out.println(user03);
		
		System.out.println("=======================");
		User user04 = (User)factory.getBean("user04");
		System.out.println(user04);
	
		System.out.println("=======================");
		User user05 = (User)factory.getBean("user05");
		System.out.println(user05);
		System.out.println("=======================");
	}
}//end of class