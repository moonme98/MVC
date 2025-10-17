package com.model2.mvc.web.purchase;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.Search;

/**
 * PurchaseRestController.java
 * 구매 관리를 위한 RESTful API 컨트롤러
 * URL Prefix : /purchase/json/
 */
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

    // PurchaseService 주입 (purchaseServiceImpl 빈을 주입)
    @Autowired
    @Qualifier("purchaseServiceImpl")
    private PurchaseService purchaseService;

    public PurchaseRestController() {
        System.out.println("==> PurchaseRestController 실행됨 : " + this.getClass());
    }

    /**
     * 구매 등록
     * @param purchase 구매 정보 (JSON 형식으로 전달됨)
     * @return 성공 여부 (true)
     */
    @PostMapping("json/addPurchase")
    public boolean addPurchase(@RequestBody Purchase purchase) throws Exception {
        System.out.println("/purchase/json/addPurchase : POST 호출됨");
        purchaseService.addPurchase(purchase);
        return true;
    }

    /**
     * 구매 상세 조회
     * @param tranNo 구매 번호
     * @return Purchase 객체 반환
     */
    @GetMapping("json/getPurchase/{tranNo}")
    public Purchase getPurchase(@PathVariable int tranNo) throws Exception {
        System.out.println("/purchase/json/getPurchase : GET 호출됨");
        return purchaseService.getPurchase(tranNo);
    }

    /**
     * 구매 리스트 조회 (검색 + 페이징 지원)
     * @param search 검색 조건 객체 (currentPage, searchKeyword 등 포함)
     * @return 구매 목록 및 전체 수 등의 정보 포함 Map 반환
     */
    @PostMapping("json/getPurchaseList")
    public Map<String, Object> getPurchaseList(@RequestBody Search search) throws Exception {
        System.out.println("/purchase/json/getPurchaseList : POST 호출됨");
        return purchaseService.getPurchaseList(search);
    }

    /**
     * 구매 정보 수정
     * @param purchase 수정할 구매 정보
     * @return 성공 여부 (true)
     */
    @PostMapping("json/updatePurchase")
    public boolean updatePurchase(@RequestBody Purchase purchase) throws Exception {
        System.out.println("/purchase/json/updatePurchase : POST 호출됨");
        purchaseService.updatePurchase(purchase);
        return true;
    }

    /**
     * 거래 상태(TranCode) 수정
     * @param purchase tranNo 와 tranCode 포함된 객체
     * @return 성공 여부 (true)
     */
    @PostMapping("json/updateTranCode")
    public boolean updateTranCode(@RequestBody Purchase purchase) throws Exception {
        System.out.println("/purchase/json/updateTranCode : POST 호출됨");
        purchaseService.updateTranCode(purchase);
        return true;
    }
}
