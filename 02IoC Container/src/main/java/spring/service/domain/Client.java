package spring.service.domain;

//FileName : Client.java	==> Client 정보를 갖는 Bean
public class  Client{
	///Field
	private String name;
	private String addr;
	private int age;
	private String [] info = { "조선","의적" };
	
	///Constructor
	public Client(){
		this.name = "홍길동";		
		this.addr ="한양";		
		this.age = 100;
		System.out.println("Client default Constructor 호출됨");
	}

	///Method getter/setter
	public String getName(){	
		System.out.println("Client getName() 호출됨");
		return this.name;
	}
	public String getAddr(){	
		System.out.println("Client getAddr() 호출됨");
		return this.addr ;
	}
	public int	 getAge(){	
		System.out.println("Client getAge() 호출됨");
		return this.age;
	}
	public String[] getInfo(){
		System.out.println("Client getInfo 호출됨");
		return this.info;
	}
	public void setName(String name){	
		System.out.println("Client setName() 호출됨");
		 this.name = name;
	}
	public void setAddr(String addr){	
		System.out.println("Client setAddr() 호출됨");
		 this.addr = addr;
	}
	public void	 setAge(int age){	
		System.out.println("Client setAge() 호출됨");
		 this.age = age;
	}
	public void setInfo(String[] info){
		System.out.println("Client setInfo 호출됨");
		 this.info = info;
	}
}