package mybatis.service.user.test;

import java.io.Reader;
import java.util.List;

import mybatis.service.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisTestApp01 {

    public static void main(String[] args) throws Exception {

        // 1. mybatis-config01.xml 로드
        String resource = "sql/mybatis-config01.xml";
        Reader reader = Resources.getResourceAsReader(resource);

        // 2. SqlSessionFactory 생성
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(reader);

        // 3. SqlSession 열기 (autoCommit = true)
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {

            System.out.println("\n=== 1. 모든 유저 조회 ===");
            List<User> userList = sqlSession.selectList("UserMapper01.getUserList");
            for (User user : userList) {
                System.out.println(user);
            }

            System.out.println("\n=== 2. userId 로 유저 조회 ===");
            User user = sqlSession.selectOne("UserMapper01.getUser", "user01");
            System.out.println(user);

            System.out.println("\n=== 3. userId + password → userName ===");
            User inputUser = new User();
            inputUser.setUserId("user01");
            inputUser.setPassword("user01");
            String userName = sqlSession.selectOne("UserMapper01.findUserId", inputUser);
            System.out.println("조회된 userName : " + userName);

            System.out.println("\n=== 4. age 로 유저 이름 조회 ===");
            User ageUser = new User();
            ageUser.setAge(30);
            List<String> nameList = sqlSession.selectList("UserMapper01.getUserListAge", ageUser);
            for (String name : nameList) {
                System.out.println("조회된 userName : " + name);
            }
        }
    }
}
