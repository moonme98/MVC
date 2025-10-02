package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

//==> 구매 관리에서 서비스할 내용 추상화/캡슐화한 Service Interface Definition  
public interface PurchaseService {
	
	// 구매 등록
	public void addPurchase(Purchase purchase) throws Exception;
	
	// 구매 조회
	public Purchase getPurchase(int tranNo) throws Exception;
	
	// 구매 목록
	public Map<String, Object> getPurchaseList(Search search) throws Exception;
	
	// 판매 목록
	public Map<String, Object> getSaleList(Search search) throws Exception;
	
	// 구매 수정
	public void updatePurchase(Purchase purchase) throws Exception;
	
	// 거래 상태(TranCode) 수정
	public void updateTranCode(Purchase purchase) throws Exception;
	
	// 구매 총 개수 (옵션)
	public int getTotalCount(Search search) throws Exception;
}
