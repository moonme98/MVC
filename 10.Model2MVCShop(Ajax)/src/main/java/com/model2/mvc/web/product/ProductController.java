package com.model2.mvc.web.product;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
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
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception {
		System.out.println("/product/addProduct : GET");
		
		return "forward:/product/addProductView.jsp";
	}
	
	// === 상품 등록 처리 ===
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product,
								@RequestParam("uploadFiles") List<MultipartFile> uploadFiles,
								@RequestParam(value="deleteNewFiles", required=false) String deleteNewFiles,
								HttpServletRequest request) throws Exception {
		System.out.println("/product/addProduct : POST");
		
		String uploadPath = request.getServletContext().getRealPath("/images/uploadFiles/");
	    File dir = new File(uploadPath);
	    if (!dir.exists()) dir.mkdirs();

	    // 삭제 예정 새 파일 처리
	    Set<String> deleteSet = new HashSet<>();
	    if(deleteNewFiles != null && !deleteNewFiles.isEmpty()) {
	        for(String fname : deleteNewFiles.split(",")) deleteSet.add(fname);
	    }

	    // 새로 업로드된 파일 처리
	    StringBuilder newFileNames = new StringBuilder();
	    for (MultipartFile file : uploadFiles) {
	        if (!file.isEmpty() && !deleteSet.contains(file.getOriginalFilename())) {
	            if (file.getSize() > 1024 * 1024) {
	                throw new RuntimeException("업로드 가능한 최대 크기(1MB)를 초과: " + file.getOriginalFilename());
	            }
	            String originalFileName = file.getOriginalFilename();
	            File saveFile = new File(uploadPath, originalFileName);
	            file.transferTo(saveFile);
	            newFileNames.append(originalFileName).append(",");
	        }
	    }

	    // DB에 저장
	    if(newFileNames.length() > 0)
	        product.setFileName(newFileNames.substring(0, newFileNames.length()-1));

	    productService.addProduct(product);
		
		return "redirect:/product/listProduct?menu=manage";
	}
	
	// === 상품 상세 조회 ===
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model,
								HttpServletRequest request,
								HttpServletResponse response, HttpSession session) throws Exception {
		System.out.println("/product/getProduct : GET");
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		// 세션에 menu 저장
	    String menu = request.getParameter("menu");
	    if(menu != null) {
	        session.setAttribute("menu", menu);
	    }
		
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
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search,
														Model model,
														HttpServletRequest request, HttpSession session) throws Exception {
		System.out.println("/product/listProduct : GET / POST");
		
		String menuParam = request.getParameter("menu");
		if(menuParam != null) {
	        session.setAttribute("menu", menuParam);
	    } else if(session.getAttribute("menu") == null) {
	        session.setAttribute("menu", "user");
	    }
		
		String menu = (String) session.getAttribute("menu");
	    System.out.println("Current session menu: " + menu);
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		List<Product> productList = (List<Product>) map.get("list");
		
		// ===== 재고없음 처리 =====
		for (Product product : productList) {
		    String code = product.getProTranCode();

		    if (code == null || code.trim().isEmpty()) {
		        product.setProTranCode("0"); // 기본값: 판매중
		    } else {
		        product.setProTranCode(code.trim());
		    }

		    // 회원용 리스트만 재고없음 처리
		    if (!"manage".equals(menu) && "1".equals(product.getProTranCode())) {
		        product.setProTranCode("4"); // 재고없음
		    }
		    
		    // 대표 이미지 세팅
		    String prodImage = product.getFileName();
		    if (prodImage != null && !prodImage.isEmpty()) {
		        if (prodImage.contains(",")) {
		            product.setFileName(prodImage.split(",")[0].trim());
		        }
		    }
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
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
		System.out.println("/product/updateProductView : GET");
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	// === 상품 수정 처리 ===
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product,
									@RequestParam("uploadFiles") List<MultipartFile> uploadFiles,
									@RequestParam(value="deleteFiles", required=false) String deleteFiles,
									@RequestParam(value="deleteNewFiles", required=false) String deleteNewFiles,
									HttpServletRequest request,
									HttpSession session) throws Exception {
		System.out.println("/product/updateProduct : POST");
		
		String uploadPath = request.getServletContext().getRealPath("/images/uploadFiles/");
	    File dir = new File(uploadPath);
	    if (!dir.exists()) dir.mkdirs();

	    // 삭제된 새 파일 목록 처리
	    Set<String> deleteSet = new HashSet<>();
	    if(deleteNewFiles != null && !deleteNewFiles.isEmpty()) {
	        for(String fname : deleteNewFiles.split(",")) {
	            deleteSet.add(fname);
	        }
	    }

	    // 새로 업로드된 파일 처리
	    StringBuilder newFileNames = new StringBuilder();
	    for (MultipartFile file : uploadFiles) {
	        if (!file.isEmpty() && !deleteSet.contains(file.getOriginalFilename())) {
	            if (file.getSize() > 1024 * 1024) {
	                throw new RuntimeException("업로드 가능한 최대 크기(1MB)를 초과: " + file.getOriginalFilename());
	            }
	            String originalFileName = file.getOriginalFilename();
	            File saveFile = new File(uploadPath, originalFileName);
	            file.transferTo(saveFile);
	            newFileNames.append(originalFileName).append(",");
	        }
	    }

	    // 기존 DB 파일
	    Product dbProduct = productService.getProduct(product.getProdNo());
	    String existingFiles = (dbProduct.getFileName() != null) ? dbProduct.getFileName() : "";

	    // 삭제된 기존 파일 처리 (deleteFiles는 기존 이미지 삭제 시 서버/DB에서 이미 제거됨)
	    if(deleteFiles != null && !deleteFiles.isEmpty()) {
	        List<String> deleteList = Arrays.asList(deleteFiles.split(","));
	        existingFiles = Arrays.stream(existingFiles.split(","))
	                              .filter(f -> !deleteList.contains(f))
	                              .collect(Collectors.joining(","));
	    }

	    // 최종 파일명
	    String finalFileNames = existingFiles;
	    if (newFileNames.length() > 0) {
	        if (!finalFileNames.isEmpty()) finalFileNames += ",";
	        finalFileNames += newFileNames.substring(0, newFileNames.length() - 1);
	    }
	    product.setFileName(finalFileNames.isEmpty() ? null : finalFileNames);
	    
	    
		int prodNo = product.getProdNo();
		productService.updateProduct(product);
		
		// 세션 상품 정보 업데이트
		Product sessionProduct = (Product) session.getAttribute("product");
		if (sessionProduct != null && sessionProduct.getProdNo() == prodNo) {
			session.setAttribute("product", product);
		}
		
		return "redirect:/product/getProduct?prodNo=" + prodNo;
	}
	
	// === 상품 이미지 삭제 처리 ===
	@RequestMapping(value="deleteProductImage", method=RequestMethod.POST)
	@ResponseBody
	public String deleteProductImage(@RequestParam("prodNo") int prodNo,
	                                 @RequestParam("fileName") String fileName,
	                                 HttpServletRequest request) throws Exception {
	    System.out.println("/product/deleteProductImage : POST");

	    // 서버에서 실제 파일 삭제
	    String uploadPath = request.getServletContext().getRealPath("/images/uploadFiles/");
	    File targetFile = new File(uploadPath, fileName);
	    if (targetFile.exists()) targetFile.delete();

	    // DB에서 해당 파일명 삭제
	    int result = productService.deleteImage(prodNo, fileName);

	    return result > 0 ? "SUCCESS" : "FAIL";
	}

}
