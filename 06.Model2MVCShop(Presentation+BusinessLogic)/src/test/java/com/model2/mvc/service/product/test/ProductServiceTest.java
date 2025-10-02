package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;

/*
 *  FileName : ProductServiceTest.java
 *  JUnit4 + Spring 통합 테스트 예제
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

    //==> Spring Wiring
    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    //@Test
    public void testAddProduct() throws Exception {

        Product product = new Product();
        product.setProdName("테스트상품4");
        product.setProdDetail("테스트 상품 설명4");
        product.setManuDate("20250910");
        product.setPrice(10000);
        product.setFileName("test.png");

        productService.addProduct(product);

        // API 확인
        Assert.assertEquals("테스트상품4", product.getProdName());
        Assert.assertEquals("테스트 상품 설명4", product.getProdDetail());
        Assert.assertEquals("20250910", product.getManuDate());
        Assert.assertEquals(10000, product.getPrice());
        Assert.assertEquals("test.png", product.getFileName());
    }

    //@Test
    public void testGetProduct() throws Exception {
        // 예시로 prodNo 조회
        Product product = productService.getProduct(10000);
        Assert.assertNotNull(product);

        // console 확인
        System.out.println("testGetProduct===="+product);
    }

    //@Test
    public void testUpdateProduct() throws Exception {
        // 예시로 prodNo 수정
        Product product = productService.getProduct(10000);
        Assert.assertNotNull(product);

        product.setProdName("변경상품3");
        product.setProdDetail("변경 상품 설명3");
        product.setManuDate("20251201");
        product.setPrice(30000);
        product.setFileName("change.png");

        productService.updateProduct(product);

        Product updated = productService.getProduct(10000);
        Assert.assertEquals("변경상품3", updated.getProdName());
        Assert.assertEquals("변경 상품 설명3", updated.getProdDetail());
        Assert.assertEquals("20251201", updated.getManuDate());
        Assert.assertEquals(30000, updated.getPrice());
        Assert.assertEquals("change.png", updated.getFileName());
    }

    //@Test
    public void testGetProductListAll() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);

        Map<String, Object> map = productService.getProductList(search);
        List<Product> list = (List<Product>) map.get("list");
        Assert.assertEquals(3, list.size());
        System.out.println(list);

        Integer totalCount = (Integer) map.get("totalCount");
        System.out.println("총 상품 수: " + totalCount);
    }
    
    //@Test
    public void testGetProductListByProdNo() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);          
        search.setSearchCondition("0");  // 0: prod_no
        search.setSearchKeyword("10034");

        Map<String, Object> map = productService.getProductList(search);

        List<Product> list = (List<Product>) map.get("list");
        System.out.println("==list.size() = " + list.size());

        for (Product p : list) {
            System.out.println("검색된 상품번호: " + p.getProdNo());
        }
        System.out.println("상품번호 검색 리스트: " + list);
    }
    
    //@Test
    public void testGetProductListByProdName() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);          
        search.setSearchCondition("1");  // 1: prod_name
        search.setSearchKeyword("하리보");

        Map<String, Object> map = productService.getProductList(search);

        List<Product> list = (List<Product>) map.get("list");
        System.out.println("==list.size() = " + list.size());

        for (Product p : list) {
            System.out.println("검색된 상품명: " + p.getProdName());
        }
        System.out.println("상품명 검색 리스트: " + list);
    }
    
   //@Test
    public void testGetProductListByPrice() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(3);          
        search.setSearchCondition("2");  // 2: price
        search.setSearchKeyword("1000");

        Map<String, Object> map = productService.getProductList(search);

        List<Product> list = (List<Product>) map.get("list");
        System.out.println("==list.size() = " + list.size());

        for (Product p : list) {
            System.out.println("검색된 상품가격: " + p.getPrice());
        }
        System.out.println("상품가격 검색 리스트: " + list);
    }
}
