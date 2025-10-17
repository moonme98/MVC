package json.test;

import java.util.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import spring.domain.User;

public class JSONObjectMapperTestApp {

    public static void main(String[] args) throws Exception {
        
        ObjectMapper mapper = new ObjectMapper();
        
        // ==============================================================
        // 1. User → JSON 변환
        // ==============================================================
        User user = new User("user01", "홍길동", "1111", null, 10);
        user.setActive(true);

        String userJson = mapper.writeValueAsString(user);
        System.out.println("1️ User → JSON : " + userJson);

        // ==============================================================
        // 2. JSON → User 변환
        // ==============================================================
        User userObj = mapper.readValue(userJson, User.class);
        System.out.println("2️ JSON → User : " + userObj);

        // ==============================================================
        // 3. List<User> → JSON 변환
        // ==============================================================
        List<User> userList = new ArrayList<User>();
        userList.add(new User("user02", "김철수", "2222", 25, 5));
        userList.add(new User("user03", "이영희", "3333", 30, 7));

        String listJson = mapper.writeValueAsString(userList);
        System.out.println("3️ List<User> → JSON : " + listJson);

        // ==============================================================
        // 4. JSON → List<User> 변환 (TypeReference 사용)
        // ==============================================================
        List<User> userListObj = mapper.readValue(listJson, new TypeReference<List<User>>() {});
        System.out.println("4️ JSON → List<User> : " + userListObj);

        // ==============================================================
        // 5. Map<String, User> → JSON 변환
        // ==============================================================
        Map<String, User> userMap = new HashMap<String, User>();
        userMap.put("one", new User("user04", "박영수", "4444", 40, 3));
        userMap.put("two", new User("user05", "최민호", "5555", 20, 2));

        String mapJson = mapper.writeValueAsString(userMap);
        System.out.println("5️ Map<String,User> → JSON : " + mapJson);

        // ==============================================================
        // 6. JSON → Map<String, User> 변환 (TypeReference 사용)
        // ==============================================================
        Map<String, User> userMapObj = mapper.readValue(mapJson, new TypeReference<Map<String, User>>() {});
        System.out.println("6️ JSON → Map<String,User> : " + userMapObj);
    }
}
