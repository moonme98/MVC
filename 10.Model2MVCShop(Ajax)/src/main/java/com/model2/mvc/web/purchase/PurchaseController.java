package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
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
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo, HttpSession session) throws Exception {
		System.out.println("/purchase/addPurchaseView : GET");
		
		Product product = productService.getProduct(prodNo);
		
		User sessionUser = (User) session.getAttribute("user");
		User buyer = new User();
		if (sessionUser != null) {
			buyer.setUserId(sessionUser.getUserId());
		}
		
		Purchase purchase = new Purchase();
		purchase.setBuyer(buyer);
		
		ModelAndView mav = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
		mav.addObject("product", product);
		mav.addObject("purchase", purchase);
		return mav;
	}
	
	// === 구매 등록 처리 ===
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase,
							  @RequestParam("prodNo") int prodNo,
							  HttpSession session) throws Exception {
		System.out.println("/purchase/addPurchase : POST");
		
		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser == null) {
			return new ModelAndView("redirect:/user/loginView.jsp");
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
		
		ModelAndView mav = new ModelAndView("forward:/purchase/addPurchase.jsp");
		mav.addObject("product", purchase.getPurchaseProd());
		mav.addObject("purchase", purchase);
		return mav;
	}
	
	@PostMapping("/purchase/kakaoReady")
	@ResponseBody
	public Map<String,String> kakaoPayReady(@RequestBody Map<String,String> data) {
	    // RestTemplate 등으로 카카오페이 "결제 준비 API" 호출
	    // ex) data.get("prodNo"), data.get("userId"), data.get("itemName"), data.get("totalAmount")
	    // tid, next_redirect_pc_url 받기
	    Map<String,String> result = new HashMap<>();
	    result.put("next_redirect_pc_url", "카카오페이에서 받은 redirect URL"); // 실제 URL로 대체
	    return result;
	}

	
	// === 구매 상세 조회 ===
	@RequestMapping(value="getPurchase", method=RequestMethod.GET)
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception {
		System.out.println("/purchase/getPurchase : GET");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView mav = new ModelAndView("forward:/purchase/getPurchase.jsp");
		mav.addObject("purchase", purchase);
		mav.addObject("product", purchase.getPurchaseProd());
		return mav;
	}
	
	// === 구매 목록 조회 ===
	@RequestMapping(value="listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search,
							   HttpSession session) throws Exception {
		System.out.println("/purchase/listPurchase : GET / POST");
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ModelAndView("redirect:/user/loginView.jsp");
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
		
		ModelAndView mav = new ModelAndView("forward:/purchase/listPurchase.jsp");
		mav.addObject("list", map.get("list"));
		mav.addObject("resultPage", resultPage);
		mav.addObject("search", search);
		return mav;
	}
	
	// === 구매 수정 화면 ===
	@RequestMapping(value="updatePurchase", method=RequestMethod.GET)
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception {
	    System.out.println("/purchase/updatePurchaseView : GET");
	    
	    Purchase purchase = purchaseService.getPurchase(tranNo);
	    
	    ModelAndView mav = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
	    mav.addObject("purchase", purchase);
	    return mav;
	}

	// === 구매 수정 처리 ===
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception {
	    System.out.println("/purchase/updatePurchase : POST");
	    
	    purchaseService.updatePurchase(purchase);
	    
	    // 갱신된 구매정보 조회
	    Purchase updatedPurchase = purchaseService.getPurchase(purchase.getTranNo());
	    
	    ModelAndView mav = new ModelAndView("forward:/purchase/getPurchase.jsp");
	    mav.addObject("purchase", updatedPurchase);
	    mav.addObject("product", updatedPurchase.getPurchaseProd());
	    return mav;
	}

	// === 거래상태(TranCode) 변경 ===
	@RequestMapping(value="updateTranCode", method=RequestMethod.POST)
	public ModelAndView updateTranCode(@RequestParam("tranNo") int tranNo,
	                             @RequestParam(value="tranCode", required=false, defaultValue="0") String tranCode,
	                             @RequestParam(value="menu", required=false) String menu) throws Exception {
	    System.out.println("/purchase/updateTranCode : POST");
	    
	    Purchase purchase = new Purchase();
	    purchase.setTranNo(tranNo);
	    purchase.setTranCode(tranCode.trim());
	    
	    purchaseService.updateTranCode(purchase);
	    
	    ModelAndView mav = new ModelAndView();
	    if ("manage".equals(menu)) {
	        mav.setViewName("redirect:/purchase/listProduct?menu=manage");
	    } else {
	        mav.setViewName("redirect:/purchase/listPurchase");
	    }
	    return mav;
	}
}
