package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {
	
	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}")
	private int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	private int pageSize;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	// === 상품 등록 화면 ===
	@RequestMapping("/addProductView.do")
	public String addProductView() {
		System.out.println("/addProductView.do");
		
		return "forward:/product/addProductView.jsp";
	}
	
	// === 상품 등록 처리 ===
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {
		System.out.println("/addProduct.do");
		productService.addProduct(product);
		
		return "redirect:/listProduct.do?menu=manage";
	}
	
	// === 상품 상세 조회 ===
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model,
								HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		System.out.println("/getProduct.do");
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		String history = null;
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("history".equals(cookie.getName())) {
	                history = java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
	                break;
	            }
	        }
	    }

	    // 상품번호 추가 & 중복 제거, 최대 5개 유지
	    java.util.LinkedHashSet<String> set = new java.util.LinkedHashSet<>();
	    set.add(String.valueOf(prodNo));
	    if (history != null) {
	        for (String h : history.split(",")) {
	            if (!h.equals(String.valueOf(prodNo))) set.add(h);
	        }
	    }
	    // 최대 5개
	    java.util.List<String> list = new java.util.ArrayList<>(set);
	    if (list.size() > 5) list = list.subList(0, 5);

	    // 쿠키 저장
	    String cookieValue = java.net.URLEncoder.encode(String.join(",", list), "UTF-8");
	    Cookie historyCookie = new Cookie("history", cookieValue);
	    historyCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 유지
	    historyCookie.setPath("/");
	    response.addCookie(historyCookie);
	    
		return "forward:/product/getProduct.jsp";
	}
	
	// === 상품 목록 조회 ===
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search, Model model) throws Exception {
		System.out.println("/listProduct.do");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		List<Product> productList = (List<Product>) map.get("list");
		
		// ===== 재고없음 처리 =====
		for (Product product : productList) {
			String code = product.getProTranCode();
			
		    if (product.getProTranCode() == null || product.getProTranCode().trim().isEmpty()) {
		        product.setProTranCode("0"); // 기본값: 판매중
		    }

		    if ("1".equals(product.getProTranCode())) {
		        product.setProTranCode("4"); // 재고없음
		    }
		    
		    product.setProTranCode(product.getProTranCode().trim());
		}
	    // ==========================		
		
		Page resultPage = new Page(
				search.getCurrentPage(),
				((Integer) map.get("totalCount")).intValue(),
				pageUnit,
				pageSize
		);
		
		System.out.println("listProduct ::" + resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	// === 상품 수정 화면 ===
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		System.out.println("/updateProductView.do");
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	// === 상품 수정 처리 ===
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product, HttpSession session) throws Exception {
		System.out.println("/updateProduct.do");
		
		int prodNo = product.getProdNo();
		productService.updateProduct(product);
		
		// 세션 상품 정보 업데이트
		Product sessionProduct = (Product) session.getAttribute("product");
		if (sessionProduct != null && sessionProduct.getProdNo() == prodNo) {
			session.setAttribute("product", product);
		}
		
		return "redirect:/getProduct.do?prodNo=" + prodNo;
	}
}
