package client.app;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import spring.domain.User;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.Map;


public class RestHttpClientApp_Aiassisted {
	
	// main Method
	public static void main(String[] args) throws Exception{
		
		////////////////////////////////////////////////////////////////////////////////////////////
		// 주석을 하나씩 처리해가며 실습
		////////////////////////////////////////////////////////////////////////////////////////////
		
//		System.out.println("\n====================================\n");
//		// 1.1 Http Get 방식 Request : JsonSimple lib 사용
//		RestHttpClientApp_Aiassisted.ReqeustHttpGet_UseJsonSimple();
		
//		System.out.println("\n====================================\n");
//		// 1.2 Http Get 방식 Request : CodeHaus lib 사용
//		RestHttpClientApp_Aiassisted.ReqeustHttpGet_UseCodeHaus();
		
		
		
//		System.out.println("\n====================================\n");
//		// 2.1 Http Protocol POST 방식 Request 
//		//	: Form Data전달(JSON 이용) / JsonSimple lib 사용 
//		RestHttpClientApp_Aiassisted.ReqeustHttpPostData_UseJsonSimple();
		
//		System.out.println("\n====================================\n");
//		// 2.2 Http Protocol POST 방식 Request 
//		//	: Form Data전달(JSON 이용) / CodeHaus lib 사용
		RestHttpClientApp_Aiassisted.ReqeustHttpPostData_UseCodeHaus();
	
	}
	
	
	//================================================================//
	//1.1 Http Protocol GET Request : JsonSimple 3rd party lib 사용
	public static void ReqeustHttpGet_UseJsonSimple() throws Exception {
	    String url = "http://127.0.0.1:8080/Spring13/app/userAPI/getUser?name=홍길동&age=10";

	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpGet httpGet = new HttpGet(url);
	    httpGet.setHeader("Accept", "application/json");
	    httpGet.setHeader("Content-Type", "application/json");

	    HttpResponse response = client.execute(httpGet);
	    String json = EntityUtils.toString(response.getEntity(), "UTF-8");

	    // JSON 파싱
	    JSONParser parser = new JSONParser();
	    JSONObject obj = (JSONObject) parser.parse(json);

	    System.out.println("json-simple GET 수신 데이터: " + obj.toJSONString());
	    System.out.println("userId: " + obj.get("userId"));
	    System.out.println("userName: " + obj.get("userName"));

	    client.close();
	}


	
	
	//1.2 Http Protocol GET Request : JsonSimple + codehaus 3rd party lib 사용
	public static void ReqeustHttpGet_UseCodeHaus() throws Exception {
	    String url = "http://127.0.0.1:8080/Spring13/app/userAPI/getUserMore/user01?name=홍길동&age=10";

	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpGet httpGet = new HttpGet(url);
	    httpGet.setHeader("Accept", "application/json");
	    httpGet.setHeader("Content-Type", "application/json");

	    HttpResponse response = client.execute(httpGet);
	    String json = EntityUtils.toString(response.getEntity(), "UTF-8");

	    // JSON 구조 예시: {"user": {...User객체...}, "otherInfo":"..." }
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> map = mapper.readValue(json, Map.class);

	    // "user"라는 innerjson에서 User 객체로 변환
	    Object userJson = map.get("user");
	    User user = mapper.convertValue(userJson, User.class);

	    System.out.println("Jackson GET 수신 데이터: " + user);

	    client.close();
	}

	//================================================================//	
	
	
	//================================================================//
	//2.1 Http Protocol POST Request : FromData 전달 / JsonSimple 3rd party lib 사용
	public static void ReqeustHttpPostData_UseJsonSimple() throws Exception {
	    String url = "http://127.0.0.1:8080/Spring13/app/userAPI/getUser";

	    // 요청 바디 JSON 생성(json-simple)
	    JSONObject sendObj = new JSONObject();
	    sendObj.put("userId", "test");
	    sendObj.put("userName", "홍길동");

	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
	    httpPost.setEntity(new StringEntity(sendObj.toJSONString(), "UTF-8"));

	    HttpResponse response = client.execute(httpPost);
	    String json = EntityUtils.toString(response.getEntity(), "UTF-8");

	    // 응답 JSON 파싱
	    JSONParser parser = new JSONParser();
	    JSONObject obj = (JSONObject) parser.parse(json);

	    System.out.println("json-simple POST 수신: " + obj.toJSONString());
	    System.out.println("userId: " + obj.get("userId"));
	    System.out.println("userName: " + obj.get("userName"));

	    client.close();
	}


	
	
	//2.2 Http Protocol POST 방식 Request : FromData전달 
	//==> JsonSimple + codehaus 3rd party lib 사용
	public static void ReqeustHttpPostData_UseCodeHaus() throws Exception {
	    String url = "http://127.0.0.1:8080/Spring13/app/userAPI/getUserMore/user01";

	    // 바디 JSON String 생성
	    String body = "{\"userId\":\"test\",\"userName\":\"홍길동\"}";

	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
	    httpPost.setEntity(new StringEntity(body, "UTF-8"));

	    HttpResponse response = client.execute(httpPost);
	    String json = EntityUtils.toString(response.getEntity(), "UTF-8");

	    // {"user": {...User객체...}, "result":"ok"}
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> map = mapper.readValue(json, Map.class);

	    Object userJson = map.get("user");
	    User user = mapper.convertValue(userJson, User.class);

	    System.out.println("Jackson POST 수신 데이터: " + user);

	    client.close();
	}

}