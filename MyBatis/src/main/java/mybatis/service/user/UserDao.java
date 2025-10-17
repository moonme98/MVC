package mybatis.service.user;

import java.util.List;

import mybatis.service.domain.User;
import mybatis.service.domain.Search;

/**
 * FileName : UserDAO.java
 * Description: 데이터베이스와 직접 통신하는 퍼시스턴스 계층 인터페이스
 */
public interface UserDao {

    // => 회원정보 :: INSERT (회원가입)
    public int addUser(User user) throws Exception;

    // => 회원정보 :: SELECT (특정 사용자 조회)
    public User getUser(String userId) throws Exception;

    // => 회원정보 :: UPDATE (회원 정보 수정)
    public int updateUser(User user) throws Exception;

    // => 회원정보 :: DELETE (회원 삭제)
    public int removeUser(String userId) throws Exception;

    // => 회원정보 :: SELECT (동적 검색 조건에 따른 회원 목록 조회)
    public List<User> getUserList(Search search) throws Exception;

}