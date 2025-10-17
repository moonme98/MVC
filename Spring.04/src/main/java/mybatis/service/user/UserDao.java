package mybatis.service.user;

import java.util.List;
import mybatis.service.domain.Search;
import mybatis.service.domain.User;

/**
 * UserDao Interface (MyBatis Mapper와 연결)
 */
public interface UserDao {

    // 회원가입
    int insertUser(User user) throws Exception;

    // 특정 회원 조회
    User findUser(String userId) throws Exception;

    // 회원 목록 조회 (검색 + 페이징)
    List<User> getUserList(Search search) throws Exception;

    // 회원 정보 수정
    int updateUser(User user) throws Exception;

    // 회원 삭제
    int removeUser(String userId) throws Exception;
}
