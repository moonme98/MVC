package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {

    // 구매 등록
    public void addPurchase(Purchase purchase) throws Exception;

    // 거래번호로 구매 조회
    public Purchase getPurchase(int tranNo) throws Exception;

    // 구매 수정
    public void updatePurchase(Purchase purchase) throws Exception;

    // 거래 상태 수정
    public void updateTranCode(Purchase purchase) throws Exception;

    // 구매 리스트
    public List<Purchase> getPurchaseList(Search search) throws Exception;

    // 판매 리스트
    public List<Purchase> getSaleList(Search search) throws Exception;

    // 전체 건수
    public int getTotalCount(Search search) throws Exception;
}
