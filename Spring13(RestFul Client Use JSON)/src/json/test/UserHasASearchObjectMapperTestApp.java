package json.test;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import spring.domain.Search;
import spring.domain.UserHasASearch;

public class UserHasASearchObjectMapperTestApp {

    public static void main(String[] args) throws Exception {
        
        ObjectMapper mapper = new ObjectMapper();

        // ==============================================================
        // 1. Domain Object 생성 및 값 설정
        // ==============================================================
        UserHasASearch userHasASearch = new UserHasASearch("user01", "홍길동", "1111", null, 10);
        userHasASearch.setActive(true);

        Search search = new Search();
        search.setSearchCondition("이름검색");
        userHasASearch.setSearch(search);

        // ==============================================================
        // 2. Domain Object → JSON Value(String) 변환
        // ==============================================================
        String jsonStr = mapper.writeValueAsString(userHasASearch);
        System.out.println("1️ Domain Object → JSON Value(String) : " + jsonStr);

        // ==============================================================
        // 3. JSON Value(String) → Domain Object 변환
        // ==============================================================
        UserHasASearch userObj = mapper.readValue(jsonStr, UserHasASearch.class);
        System.out.println("2️ JSON Value(String) → Domain Object : " + userObj);

        // 값 추출
        System.out.println("👉 userId : " + userObj.getUserId());
        System.out.println("👉 searchCondition : " + userObj.getSearch().getSearchCondition());

        // ==============================================================
        // 4. JSON Value(String) → JSONObject 사용 및 값 추출
        // ==============================================================
        JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonStr);

        String userId = (String) jsonObject.get("userId");
        String userName = (String) jsonObject.get("userName");

        JSONObject searchObj = (JSONObject) jsonObject.get("search");
        String searchCondition = (String) searchObj.get("searchCondition");

        System.out.println("3️ JSON Value(String) → JSONObject 값 추출");
        System.out.println("👉 userId = " + userId);
        System.out.println("👉 userName = " + userName);
        System.out.println("👉 searchCondition = " + searchCondition);
    }
}
