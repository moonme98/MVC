package mybatis.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mybatis.service.domain.Search;
import mybatis.service.domain.User;
import mybatis.service.user.UserDao;
import mybatis.service.user.UserService;

@Service("userServiceImpl14")
public class UserServiceImpl14 implements UserService {

	// ==> Field
	@Autowired
	@Qualifier("userDaoImpl14")
	private UserDao userDao;

	// ==> Constructor
	public UserServiceImpl14() {
		System.out.println("==> UserServiceImpl14 기본 생성자 호출됨");
	}

	// ==> Method
	@Override
	public int addUser(User user) throws Exception {
		System.out.println("::" + getClass().getName() + ".setUserDao() 호출");
		return userDao.addUser(user);
	}

	
	/*
	 * public int addUser(User user) throws Exception {
	 * 
	 * int result = 0; System.out.println(">>>> 1번째 insert =============="); result
	 * = userDao.addUser(user); System.out.println(">>>> 1번째 insert 결과 :" +result);
	 * System.out.println(">>>> 2번째 insert =============="); result =
	 * userDao.addUser(user); System.out.println(">>>> 2번째 insert 결과 :" +result);
	 * System.out.println(">>>> 결과는 ???? ==============");
	 * 
	 * return 0;
	 * 
	 * }
	 */

	@Override
	public User getUser(String userId) throws Exception {
		return userDao.getUser(userId);
	}

	@Override
	public int updateUser(User user) throws Exception {
		return userDao.updateUser(user);
	}

	@Override
	public int removeUser(String userId) throws Exception {
		return userDao.removeUser(userId);
	}

	@Override
	public List<User> getUserList(Search search) throws Exception {
		return userDao.getUserList(search);
	}
}
