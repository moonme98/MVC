package mybatis.service.user;

import java.util.List;
import mybatis.service.domain.Search;
import mybatis.service.domain.User;

/**
 * UserService Interface
 * 회원 관리 관련 기능을 정의
 */
public interface UserService {

    // 회원 가입 (INSERT)
    public int addUser(User user) throws Exception;

    // 특정 사용자 조회 (SELECT)
    public User getUser(String userId) throws Exception;

    // 회원 정보 수정 (UPDATE)
    public int updateUser(User user) throws Exception;

    // 회원 삭제 (DELETE)
    public int removeUser(String userId) throws Exception;

    // 동적 검색 조건에 따른 회원 목록 조회 (SELECT)
    public List<User> getUserList(Search search) throws Exception;
}
