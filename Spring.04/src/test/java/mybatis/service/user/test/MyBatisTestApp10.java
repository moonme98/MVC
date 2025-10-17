package mybatis.service.user.test;

import java.util.ArrayList;
import mybatis.service.domain.User;
import mybatis.service.domain.Search;
import org.apache.ibatis.session.SqlSession;

/**
 * FileName : MyBatisTestApp10.java
 * UserMapper10 Test Application (User 필드 기준)
 */
public class MyBatisTestApp10 {

    public static void main(String[] args) throws Exception {

        // SqlSession 생성
        SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
        System.out.println("\n");

        // Test용 User 객체 생성
        User user = new User();
        user.setUserId("user11");
        user.setUserName("홍길동");
        user.setPassword("user11");
        user.setSsn("900101-1234567");
        user.setPhone("010-1234-5678");
        user.setAddr("서울시");
        user.setEmail("hong@test.com");
        user.setRole("user"); // 기본 role

        // 1. addUser Test
        System.out.println(":: 1. addUser(INSERT) ? ");
        System.out.println(":: " + sqlSession.insert("UserMapper10.addUser", user));
        System.out.println("\n");

        // 2. getUser Test
        System.out.println(":: 2. getUser(SELECT) ? ");
        System.out.println(":: " + sqlSession.selectOne("UserMapper10.getUser", user.getUserId()));
        System.out.println("\n");

        // 3. updateUser Test
        user.setUserName("이몽룡");
        user.setPhone("010-9876-5432");
        user.setAddr("부산시");
        user.setEmail("leem@test.com");
        System.out.println(":: 3. updateUser(UPDATE) ? ");
        System.out.println(":: " + sqlSession.update("UserMapper10.updateUser", user));
        System.out.println("\n");

        // 4. getUser Test (수정 후 조회)
        System.out.println(":: 4. getUser(SELECT) ? ");
        System.out.println(":: " + sqlSession.selectOne("UserMapper10.getUser", user.getUserId()));
        System.out.println("\n");

        // 5. removeUser Test
        System.out.println(":: 5. removeUser(DELETE) ? ");
        System.out.println(":: " + sqlSession.delete("UserMapper10.removeUser", user.getUserId()));
        System.out.println("\n");

        System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////\n");

        // Search 객체 생성
        Search search = new Search();

        // 1. getUserList Test (전체)
        System.out.println(":: 1. getUserList(SELECT) ? ");
        SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper10.getUserList", search));

        // 2. getUserList Test (userId 조건)
        search.setSearchCondition("userId");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("user01");
        search.setSearchKeyword("user01");
        System.out.println(":: 2. getUserList(SELECT) ? ");
        SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper10.getUserList", search));

        // 3. getUserList Test (userName 조건)
        search.setSearchCondition("userName");
        search.setSearchKeyword("홍길동");
        System.out.println(":: 3. getUserList(SELECT) ? ");
        SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper10.getUserList", search));

        // SqlSession 종료
        sqlSession.close();
        System.out.println(":: END :: SqlSession 닫기..");
    }
}
