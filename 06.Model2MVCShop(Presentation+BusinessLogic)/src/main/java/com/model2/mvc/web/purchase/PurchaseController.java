package com.model2.mvc.web.purchase;

import java.util.Map;

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
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
public class PurchaseController {
	
	/// Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit']}")
	private int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	private int pageSize;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	// === 구매 등록 화면 ===
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@RequestParam("prodNo") int prodNo, HttpSession session, Model model) throws Exception {
		System.out.println("/addPurchaseView.do");
		
		Product product = productService.getProduct(prodNo);
		
		User sessionUser = (User) session.getAttribute("user");
		User buyer = new User();
		if (sessionUser != null) {
			buyer.setUserId(sessionUser.getUserId());
		}
		
		Purchase purchase = new Purchase();
		purchase.setBuyer(buyer);
		
		model.addAttribute("product", product);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	// === 구매 등록 처리 ===
	@RequestMapping("/addPurchase.do")
	public String addPurchase(@ModelAttribute("purchase") Purchase purchase,
							  @RequestParam("prodNo") int prodNo,
							  HttpSession session,
							  Model model) throws Exception {
		System.out.println("/addPurchase.do");
		
		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser == null) {
			return "redirect:/user/loginView.jsp";
		}
		
		Product purchaseProd = new Product();
		purchaseProd.setProdNo(prodNo);
		
		User buyer = new User();
		buyer.setUserId(sessionUser.getUserId());
		buyer.setUserName(sessionUser.getUserName());
		
		purchase.setPurchaseProd(purchaseProd);
		purchase.setBuyer(buyer);
		purchase.setTranCode("1");  // 기본 구매완료
		
		purchaseService.addPurchase(purchase);
		
		model.addAttribute("product", purchase.getPurchaseProd());
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	// === 구매 상세 조회 ===
	@RequestMapping("/getPurchase.do")
	public String getPurchase(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
		System.out.println("/getPurchase.do");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", purchase);
		model.addAttribute("product", purchase.getPurchaseProd());
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	// === 구매 목록 조회 ===
	@RequestMapping("/listPurchase.do")
	public String listPurchase(@ModelAttribute("search") Search search,
							   HttpSession session,
							   Model model) throws Exception {
		System.out.println("/listPurchase.do");
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/loginView.jsp";
		}
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		search.setSearchCondition("buyerId");
		search.setSearchKeyword(user.getUserId());
		
		Map<String, Object> map = purchaseService.getPurchaseList(search);
		
		Page resultPage = new Page(
				search.getCurrentPage(),
				((Integer) map.get("totalCount")).intValue(),
				pageUnit,
				pageSize
		);
		
		System.out.println("listPurchase ::" + resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	// === 구매 수정 화면 ===
	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
	    System.out.println("/updatePurchaseView.do");
	    
	    Purchase purchase = purchaseService.getPurchase(tranNo);
	    model.addAttribute("purchase", purchase);
	    
	    return "forward:/purchase/updatePurchaseView.jsp";
	}

	// === 구매 수정 처리 ===
	@RequestMapping("/updatePurchase.do")
	public String updatePurchase(@ModelAttribute("purchase") Purchase purchase, Model model) throws Exception {
	    System.out.println("/updatePurchase.do");
	    
	    purchaseService.updatePurchase(purchase);
	    
	    // 갱신된 구매정보 조회
	    Purchase updatedPurchase = purchaseService.getPurchase(purchase.getTranNo());
	    
	    model.addAttribute("purchase", updatedPurchase);
	    model.addAttribute("product", updatedPurchase.getPurchaseProd());
	    
	    return "forward:/purchase/getPurchase.jsp";
	}

	// === 거래상태(TranCode) 변경 ===
	@RequestMapping("/updateTranCode.do")
	public String updateTranCode(@RequestParam("tranNo") int tranNo,
	                             @RequestParam(value="tranCode", required=false, defaultValue="0") String tranCode,
	                             @RequestParam(value="menu", required=false) String menu) throws Exception {
	    System.out.println("/updateTranCode.do");
	    
	    Purchase purchase = new Purchase();
	    purchase.setTranNo(tranNo);
	    purchase.setTranCode(tranCode.trim());
	    
	    purchaseService.updateTranCode(purchase);
	    
	    // 이동 분기
	    if ("manage".equals(menu)) {
	        return "redirect:/listProduct.do?menu=manage";
	    } else {
	        return "redirect:/listPurchase.do";
	    }
	}
}
