package client.app;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.Search;

/**
 * UserRestController & ProductRestController 테스트용 Java 클라이언트
 * HttpURLConnection + Jackson 사용
 */
public class RestHttpClientApp_Aiassisted {

    ///Field
    private static final String BASE_URL_USER = "http://localhost:8080/user/json/";
    private static final String BASE_URL_PRODUCT = "http://localhost:8080/product/json/";
    private static final String BASE_URL_PURCHASE = "http://localhost:8080/purchase/json/";

    ///Main Method
    public static void main(String[] args) throws Exception {
        // === User API 테스트 ===
//        addUser_test();
//        login_test();
//        getUser_test("user01");
//        getUserList_test();
//        updateUser_test();
//        checkDuplication_test("user01");

        // === Product API 테스트 ===
//        addProduct_test();
//        getProduct_test(10085);
//        getProductList_test();
//        updateProduct_test();
    	
        // === Purchase API 테스트 ===
//        addPurchase_test();
//        getPurchase_test(10061);
//        getPurchaseList_test();
//        updatePurchase_test();
//        updateTranCode_test();
    }

    ////////////////////////////////////////////////////////////////////////
    // ========================== User API ===============================
    ////////////////////////////////////////////////////////////////////////

    public static void addUser_test() throws Exception {
        System.out.println("==> addUser_test()");

        User user = new User();
        user.setUserId("user99");
        user.setUserName("이순신");
        user.setPassword("9999");
        user.setRole("user");
        user.setSsn("9001011234567");
        user.setPhone("010-9999-9999");
        user.setAddr("서울시 강남구");
        user.setEmail("lee99@example.com");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        String result = sendPost(BASE_URL_USER, "addUser", json);
        System.out.println("결과 : " + result);
    }

    public static void login_test() throws Exception {
        System.out.println("==> login_test()");

        User user = new User();
        user.setUserId("user99");
        user.setPassword("9999");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        String result = sendPost(BASE_URL_USER, "login", json);
        System.out.println("로그인 응답 : " + result);
    }

    public static void getUser_test(String userId) throws Exception {
        System.out.println("==> getUser_test()");
        String result = sendGet(BASE_URL_USER, "getUser/" + userId);
        System.out.println("회원 정보 : " + result);
    }

    public static void getUserList_test() throws Exception {
        System.out.println("==> getUserList_test()");

        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(10);
        search.setSearchCondition("");
        search.setSearchKeyword("");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(search);

        String result = sendPost(BASE_URL_USER, "getUserList", json);
        System.out.println("회원 리스트 :\n" + result);

        Map<String, Object> resultMap = mapper.readValue(result, Map.class);
        String prettyResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
        System.out.println("회원 리스트 (Pretty Print):\n" + prettyResult);
    }

    public static void updateUser_test() throws Exception {
        System.out.println("==> updateUser_test()");

        User user = new User();
        user.setUserId("user99");
        user.setUserName("이순신-수정");
        user.setPhone("010-1111-2222");
        user.setAddr("서울시 수정구");
        user.setEmail("a@a.com");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        String result = sendPost(BASE_URL_USER, "updateUser", json);
        System.out.println("수정 결과 : " + result);
    }

    public static void checkDuplication_test(String userId) throws Exception {
        System.out.println("==> checkDuplication_test()");
        String result = sendGet(BASE_URL_USER, "checkDuplication/" + userId);
        System.out.println("중복 여부 : " + result);
    }

    ////////////////////////////////////////////////////////////////////////
    // ========================== Product API ===============================
    ////////////////////////////////////////////////////////////////////////

    // 1. 상품 등록
    public static void addProduct_test() throws Exception {
        System.out.println("==> addProduct_test()");

        Product product = new Product();
        //product.setProdNo(10085);
        product.setProdName("테스트 상품");
        product.setProdDetail("테스트 상품 상세 설명");
        product.setManuDate("20250922");
        product.setPrice(10000);
        product.setFileName("test.jpg");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);

        String result = sendPost(BASE_URL_PRODUCT, "addProduct", json);
        System.out.println("상품 등록 결과 : " + result);
    }

    // 2. 상품 조회
    public static void getProduct_test(int prodNo) throws Exception {
        System.out.println("==> getProduct_test()");
        String result = sendGet(BASE_URL_PRODUCT, "getProduct/" + prodNo);
        System.out.println("상품 정보 : " + result);
    }

    // 3. 상품 목록 조회
    public static void getProductList_test() throws Exception {
        System.out.println("==> getProductList_test()");

        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(5);
        search.setSearchCondition("");
        search.setSearchKeyword("");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(search);

        String result = sendPost(BASE_URL_PRODUCT, "getProductList", json);
        System.out.println("상품 리스트 :\n" + result);

        Map<String, Object> resultMap = mapper.readValue(result, Map.class);
        String prettyResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
        System.out.println("상품 리스트 (Pretty Print):\n" + prettyResult);
    }

    // 4. 상품 수정
    public static void updateProduct_test() throws Exception {
        System.out.println("==> updateProduct_test()");

        Product product = new Product();
        product.setProdNo(10085);
        product.setProdName("테스트 상품 - 수정됨");
        product.setProdDetail("테스트 상품 상세 설명 수정");
        product.setManuDate("20250922");
        product.setPrice(20000);
        product.setFileName("update.jpg");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);

        String result = sendPost(BASE_URL_PRODUCT, "updateProduct", json);
        System.out.println("수정 결과 : " + result);
    }
    
    ////////////////////////////////////////////////////////////////////////
    // ========================== Purchase API =============================
    ////////////////////////////////////////////////////////////////////////

    // 1. 구매 등록
    public static void addPurchase_test() throws Exception {
        System.out.println("==> addPurchase_test()");

        Purchase purchase = new Purchase();

        // 구매자(User) 정보
        User buyer = new User();
        buyer.setUserId("user07");
        purchase.setBuyer(buyer);

        // 상품(Product) 정보
        Product product = new Product();
        product.setProdNo(10024);
        purchase.setPurchaseProd(product);

        // 배송/결제 정보
        purchase.setDivyAddr("서울시 강남구");
        purchase.setDivyRequest("문 앞에 두세요");
        purchase.setDivyDate("20250923");
        purchase.setReceiverName("홍길동");
        purchase.setReceiverPhone("010-1234-5678");
        purchase.setPaymentOption("1");
        purchase.setTranCode("1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(purchase);

        String result = sendPost(BASE_URL_PURCHASE, "addPurchase", json);
        System.out.println("구매 등록 결과 : " + result);
    }

    // 2. 구매 상세 조회
    public static void getPurchase_test(int tranNo) throws Exception {
        System.out.println("==> getPurchase_test()");
        String result = sendGet(BASE_URL_PURCHASE, "getPurchase/" + tranNo);
        System.out.println("구매 정보 : " + result);
    }

    // 3. 구매 목록 조회
    public static void getPurchaseList_test() throws Exception {
        System.out.println("==> getPurchaseList_test()");

        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(5);
        search.setSearchCondition("");
        search.setSearchKeyword("");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(search);

        String result = sendPost(BASE_URL_PURCHASE, "getPurchaseList", json);
        System.out.println("구매 리스트 :\n" + result);

        Map<String, Object> resultMap = mapper.readValue(result, Map.class);
        String prettyResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
        System.out.println("구매 리스트 (Pretty Print):\n" + prettyResult);
    }

    // 4. 구매 정보 수정
    public static void updatePurchase_test() throws Exception {
        System.out.println("==> updatePurchase_test()");
        
        Purchase purchase = new Purchase();
        
        // 구매자(User) 정보
        User buyer = new User();
        buyer.setUserId("user07");
        purchase.setBuyer(buyer);
        
        purchase.setTranNo(10061); // 수정할 구매 번호
        purchase.setDivyAddr("서울시 송파구");
        purchase.setDivyRequest("배송 변경");
        purchase.setDivyDate("20250925");
        purchase.setReceiverName("홍길동");
        purchase.setReceiverPhone("010-1234-5678");
        purchase.setPaymentOption("1");
        purchase.setTranCode("1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(purchase);

        String result = sendPost(BASE_URL_PURCHASE, "updatePurchase", json);
        System.out.println("구매 정보 수정 결과 : " + result);
    }

    // 5. 거래 상태(TranCode) 수정
    public static void updateTranCode_test() throws Exception {
        System.out.println("==> updateTranCode_test()");

        Purchase purchase = new Purchase();
        purchase.setTranNo(10061); // 수정할 구매 번호
        purchase.setTranCode("2"); // 배송중으로 상태 변경

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(purchase);

        String result = sendPost(BASE_URL_PURCHASE, "updateTranCode", json);
        System.out.println("거래 상태 수정 결과 : " + result);
    }

    ////////////////////////////////////////////////////////////////////////
    // ========================== 공통 메서드 ===============================
    ////////////////////////////////////////////////////////////////////////

    private static String sendPost(String baseUrl, String endpoint, String json) throws Exception {
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes("UTF-8"));
            os.flush();
        }
        return readResponse(conn);
    }

    private static String sendGet(String baseUrl, String endpoint) throws Exception {
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return readResponse(conn);
    }

    private static String readResponse(HttpURLConnection conn) throws Exception {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}
