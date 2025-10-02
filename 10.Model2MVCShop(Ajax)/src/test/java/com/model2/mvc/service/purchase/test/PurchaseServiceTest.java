package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

    @Autowired
    @Qualifier("purchaseServiceImpl")
    private PurchaseService purchaseService;

    //@Test
    public void testAddPurchase() throws Exception {
        Purchase purchase = new Purchase();

        User buyer = new User();
        buyer.setUserId("user07");
        purchase.setBuyer(buyer);
        

        Product product = new Product();
        product.setProdNo(10034);
        purchase.setPurchaseProd(product);

        purchase.setPaymentOption("1");
        purchase.setReceiverName("홍길동");
        purchase.setReceiverPhone("010-1111-2222");
        purchase.setDivyAddr("서울시 강남구");
        purchase.setDivyRequest("부재 시 경비실");
        purchase.setDivyDate("20250915");
        purchase.setTranCode("0"); // 판매중

        purchaseService.addPurchase(purchase);

        Purchase result = purchaseService.getPurchase(purchase.getTranNo());
        Assert.assertNotNull(result);
        Assert.assertEquals("user07", result.getBuyer().getUserId());
        Assert.assertEquals(10034, result.getPurchaseProd().getProdNo());
        System.out.println("등록 후 조회 결과: " + result);
    }

    //@Test
    public void testGetPurchase() throws Exception {
    	int tranNo = 10022;
        Purchase purchase = purchaseService.getPurchase(tranNo);
        
        Assert.assertNotNull(purchase);
        System.out.println("testGetPurchase====" + purchase);
        
        Assert.assertEquals("user07", purchase.getBuyer().getUserId());
        Assert.assertEquals(10034, purchase.getPurchaseProd().getProdNo());
    }

    //@Test
    public void testUpdatePurchase() throws Exception {
    	int tranNo = 10022;
        Purchase purchase = purchaseService.getPurchase(tranNo);
        Assert.assertNotNull(purchase);

        purchase.setPaymentOption("2");
        purchase.setReceiverName("김철수");
        purchase.setReceiverPhone("010-3333-4444");
        purchase.setDivyAddr("서울시 서초구");
        purchase.setDivyRequest("배송 전 연락바람");
        purchase.setDivyDate("20250920");

        purchaseService.updatePurchase(purchase);

        Purchase updated = purchaseService.getPurchase(tranNo);
        Assert.assertEquals("2", updated.getPaymentOption().trim());
        Assert.assertEquals("김철수", updated.getReceiverName());
        System.out.println("업데이트 후 결과: " + updated);
    }

    //@Test
    public void testUpdateTranCode() throws Exception {
    	int tranNo = 10022;
        Purchase purchase = purchaseService.getPurchase(tranNo);
        Assert.assertNotNull(purchase);

        purchase.setTranCode("1"); // 구매완료
        purchaseService.updateTranCode(purchase);

        Purchase updated = purchaseService.getPurchase(tranNo);
        Assert.assertEquals("1", updated.getTranCode().trim());
        System.out.println("거래 상태 수정 후: " + updated.getTranCodeName());
    }

    //@Test
    public void testGetPurchaseList() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);

        Map<String, Object> map = purchaseService.getPurchaseList(search);
        List<Purchase> list = (List<Purchase>) map.get("list");
        Assert.assertEquals(3, list.size());
     
        Integer totalCount = (Integer) map.get("totalCount");
        System.out.println("총 구매 거래 수: " + totalCount);
        list.forEach(System.out::println);
    }

    //@Test
    public void testGetSaleList() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);

        Map<String, Object> map = purchaseService.getSaleList(search);
        List<Purchase> list = (List<Purchase>) map.get("list");
        Assert.assertEquals(3, list.size());

        Integer totalCount = (Integer) map.get("totalCount");
        System.out.println("총 판매 거래 수: " + totalCount);
        list.forEach(System.out::println);
    }
}
