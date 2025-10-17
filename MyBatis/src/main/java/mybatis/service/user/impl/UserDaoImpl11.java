package mybatis.service.user.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.service.domain.User;
import mybatis.service.domain.Search;
import mybatis.service.user.UserDao;

import mybatis.service.user.test.SqlSessionFactoryBean;


public class UserDaoImpl11 implements UserDao {

    // Field
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
    	this.sqlSession = sqlSession;
    	System.out.println("==> UserDAOImpl11.setSqlSession 호출됨 : " + sqlSession);
    }

    // Constructor
    public UserDaoImpl11() {
    }
    
    // Method
    public int addUser(User user) throws Exception {
        return sqlSession.insert("UserMapper10.addUser", user);
    }

    public User getUser(String userId) throws Exception {
        return sqlSession.selectOne("UserMapper10.getUser", userId);
    }

    public int updateUser(User user) throws Exception {
        return sqlSession.update("UserMapper10.updateUser", user);
    }

    public int removeUser(String userId) throws Exception {
        return sqlSession.delete("UserMapper10.removeUser", userId);
    }

    public List<User> getUserList(Search search) throws Exception {
        return sqlSession.selectList("UserMapper10.getUserList", search);
    }

}