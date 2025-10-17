package mybatis.service.user.test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * FileName : SqlSessionFactoryBean.java
 *  ==> SqlSession 을 생성하는 공통 모듈
 *  ==> Test 용도로 편리하게 사용
 */
public class SqlSessionFactoryBean {

    /**
     * SqlSession 생성
     * @return SqlSession instance (autoCommit = true)
     * @throws IOException
     */
    public static SqlSession getSqlSession() throws IOException {
        // mybatis-config.xml metadata 읽기
        Reader reader = Resources.getResourceAsReader("sql/mybatis-config01.xml");

        // SqlSessionFactory 생성
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        // autoCommit=true 인 SqlSession 생성
        return sqlSessionFactory.openSession(true);
    }

    /**
     * List<User> 또는 List<Object> 출력
     * @param list 출력할 List
     */
    public static <T> void printList(List<T> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("리스트가 비어있습니다.\n");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("<" + (i + 1) + "> 번째 회원: " + list.get(i).toString());
        }
        System.out.println("\n");
    }
}
