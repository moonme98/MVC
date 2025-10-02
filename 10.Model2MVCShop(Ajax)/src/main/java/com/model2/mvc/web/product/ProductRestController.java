package com.model2.mvc.web.product;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.common.Search;

/**
 * ProductRestController.java
 * 상품 관리를 위한 RESTful API 컨트롤러
 * URL Prefix : /product/json/
 */
@RestController
@RequestMapping("/product/*")
public class ProductRestController {

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    public ProductRestController() {
        System.out.println("==> ProductRestController 실행됨 : " + this.getClass());
    }

    /**
     * 상품 등록
     * @param product 상품 정보 (JSON 형식)
     * @return 성공 여부
     */
    @PostMapping("json/addProduct")
    public boolean addProduct(@RequestBody Product product) throws Exception {
        System.out.println("/product/json/addProduct : POST 호출됨");
        productService.addProduct(product);
        return true;
    }

    /**
     * 상품 상세 조회
     * @param prodNo 상품 번호
     * @return 상품 객체
     */
    @GetMapping("json/getProduct/{prodNo}")
    public Product getProduct(@PathVariable int prodNo) throws Exception {
        System.out.println("/product/json/getProduct : GET 호출됨");
        return productService.getProduct(prodNo);
    }

    /**
     * 상품 목록 조회 (검색 + 페이징)
     * @param search 검색 조건
     * @return 상품 목록 및 전체 개수
     */
    @PostMapping("json/getProductList")
    public Map<String, Object> getProductList(@RequestBody Search search) throws Exception {
        System.out.println("/product/json/getProductList : POST 호출됨");
        return productService.getProductList(search);
    }

    /**
     * 상품 수정
     * @param product 수정할 상품 정보
     * @return 성공 여부
     */
    @PostMapping("json/updateProduct")
    public boolean updateProduct(@RequestBody Product product) throws Exception {
        System.out.println("/product/json/updateProduct : POST 호출됨");
        productService.updateProduct(product);
        return true;
    }
}
