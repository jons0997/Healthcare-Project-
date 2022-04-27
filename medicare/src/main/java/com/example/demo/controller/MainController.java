package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.dao.NewUserDAO;
import com.example.demo.entity.Product;
import com.example.demo.form.CustomerForm;
import com.example.demo.form.NewUserForm;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.model.NewUserInfo;
import com.example.demo.pagination.PaginationResult;
import com.example.demo.utils.Utils;
import com.example.demo.validator.CustomerFormValidator;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class MainController {

   @Autowired
   private OrderDAO orderDAO;

   @Autowired
   private ProductDAO productDAO;
   
   @Autowired
   private NewUserDAO newUserDAO;

   @Autowired
   private CustomerFormValidator customerFormValidator;

   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);

      // Case update quantity in cart
      // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
      if (target.getClass() == CartInfo.class) {

      }

      // Case save customer information.
      // (@ModelAttribute @Validated CustomerInfo customerForm)
      else if (target.getClass() == CustomerForm.class) {
         dataBinder.setValidator(customerFormValidator);
      }

   }

   @RequestMapping("about")
   public String about() {
      return "about";
   } 
   
   @RequestMapping("contact")
   public String contact() {
	  return "contact";
	   }
   
   @RequestMapping("products")
   public String products() {
      return "products";
		   } 
   
   @RequestMapping("login")
   public String login() {
	   return "login";
			   }
   
   @RequestMapping("signup")
   public String signup() {
      return "signup";
   }
   
   @RequestMapping("logout")
   public String logout() {
      return "logout";
   }
   
   @RequestMapping("/403")
   public String accessDenied() {
      return "/403";
   }

   @RequestMapping("/")
   public String home() {
      return "index";
   }
   
   // GET: Show Sign up Page
   @RequestMapping(value= { "/signup" }, method = RequestMethod.GET)
   		public String signup(Model model) {
	    System.out.println("@REQUESTMAPPING SIGNUP called");
	    model.addAttribute("newUserForm", new NewUserForm());
	    System.out.println("NEWUSERFORM model attribute added");
	   	return "signup";
   }
   
   @WebServlet("/signupServlet")
   public class SignupServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
		   String username = request.getParameter("username");
		   String password = request.getParameter("password");
		   NewUserForm newuser = new NewUserForm();
		   newuser.setUsername(username);
		   newuser.setPassword(password);
		   NewUserDAO newuserdao = new NewUserDAO();
		   newuserdao.saveUser(newuser);
		   System.out.println("signupServlet Called");
	   }
   }

   // Product List
   @RequestMapping({ "/productList" })
   public String listProductHandler(Model model, //
         @RequestParam(value = "name", defaultValue = "") String likeName,
         @RequestParam(value = "page", defaultValue = "1") int page) {
      final int maxResult = 5;
      final int maxNavigationPage = 10;

      PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
            maxResult, maxNavigationPage, likeName);

      model.addAttribute("paginationProducts", result);
      return "productList";
   }
   
   //3 request mappings below this are for sorting by category
   
   @RequestMapping ({"/productListAnalgesic"})
   public String listAnalgesicProductHandler(Model model, //
		   @RequestParam(value="category", defaultValue="Analgesic") String category,
		   @RequestParam(value="page", defaultValue="1") int page) {
	   final int maxResult = 5;
	   final int maxNavigationPage = 10;
	   
	   PaginationResult<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, category);
	   model.addAttribute("paginationProducts", result);
	   //return "productListAnalgesic";
	   return "productList";
	   }
   
   @RequestMapping ({"/productListAntipyretic"})
   public String listAntipyreticProductHandler(Model model, //
		   @RequestParam(value="category", defaultValue="Antipyretic") String category,
		   @RequestParam(value="page", defaultValue="1") int page) {
	   final int maxResult = 5;
	   final int maxNavigationPage = 10;
	   
	   //PaginationResult<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, category);
	   PaginationResult<ProductInfo> result2 = productDAO.queryAntipyretics(page, maxResult, maxNavigationPage);
	   model.addAttribute("paginationProducts", result2);
	   //return "productListAntipyretic";
	   return "productList";
   }
   
   @RequestMapping ({"/productListAntibiotic"})
   public String listAntibioticProductHandler(Model model, //
		   @RequestParam(value="category", defaultValue="Antibiotic") String category,
		   @RequestParam(value="page", defaultValue="1") int page) {
	   final int maxResult = 5;
	   final int maxNavigationPage = 10;
	   
	   PaginationResult<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, category);
	   model.addAttribute("paginationProducts", result);
	   //return "productListAntibiotic";
	   return "productList";
   }

   @RequestMapping({ "/buyProduct" })
   public String listProductHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {

      Product product = null;
      if (code != null && code.length() > 0) {
         product = productDAO.findProduct(code);
      }
      if (product != null) {

         //
         CartInfo cartInfo = Utils.getCartInSession(request);

         ProductInfo productInfo = new ProductInfo(product);

         cartInfo.addProduct(productInfo, 1);
      }

      return "redirect:/shoppingCart";
   }

   @RequestMapping({ "/shoppingCartRemoveProduct" })
   public String removeProductHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {
      Product product = null;
      if (code != null && code.length() > 0) {
         product = productDAO.findProduct(code);
      }
      if (product != null) {

         CartInfo cartInfo = Utils.getCartInSession(request);

         ProductInfo productInfo = new ProductInfo(product);

         cartInfo.removeProduct(productInfo);

      }

      return "redirect:/shoppingCart";
   }

   // POST: Update quantity for product in cart
   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
   public String shoppingCartUpdateQty(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("cartForm") CartInfo cartForm) {

      CartInfo cartInfo = Utils.getCartInSession(request);
      cartInfo.updateQuantity(cartForm);

      return "redirect:/shoppingCart";
   }

   // GET: Show cart.
   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
   public String shoppingCartHandler(HttpServletRequest request, Model model) {
      CartInfo myCart = Utils.getCartInSession(request);

      model.addAttribute("cartForm", myCart);
      return "shoppingCart";
   }

   // GET: Enter customer information.
   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
   public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

      CartInfo cartInfo = Utils.getCartInSession(request);

      if (cartInfo.isEmpty()) {

         return "redirect:/shoppingCart";
      }
      CustomerInfo customerInfo = cartInfo.getCustomerInfo();

      CustomerForm customerForm = new CustomerForm(customerInfo);

      model.addAttribute("customerForm", customerForm);

      return "shoppingCartCustomer";
   }

   // POST: Save new user (STILL WORK IN PROGRESS)
   @RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
   public String newUserSave(HttpServletRequest request, //
		   Model model, //
		   @ModelAttribute("newUserForm") @Validated NewUserForm newUserForm, //
		   BindingResult result, //
		   final RedirectAttributes redirectAttributes) {
	   //model.addAttribute(newUserForm);
	   
	   if (result.hasErrors()) {
		   newUserForm.setValid(false);
		   // Forward to reenter new user info
		   return "signup";
	   }
	   try {
		   newUserDAO.saveUser(newUserForm);
	   }
	   catch(Exception e) {
		   Throwable rootCause = ExceptionUtils.getRootCause(e);
	       String message = rootCause.getMessage();
	       model.addAttribute("errorMessage", message);
	       // Show signup form.
	       return "signup";
	   }
	   //newUserForm.setValid(true);
	   
	   //NEED TO FIGURE OUT SAVING USER BELOW
	   //NewUserInfo userInfo = Utils.getUserInfo(request);
	   System.out.println("SIGNUP CALLED");
	   System.out.println("newUserForm.getUsername() is "+newUserForm.getUsername());
	   //System.out.println(userInfo.getUsername() +"    "+ userInfo.getId());
	   //UserInfo userInfo = "";
	   return "redirect:/";
   }
   // POST: Save customer information.
   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
   public String shoppingCartCustomerSave(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {

      if (result.hasErrors()) {
         customerForm.setValid(false);
         // Forward to reenter customer info.
         return "shoppingCartCustomer";
      }

      customerForm.setValid(true);
      CartInfo cartInfo = Utils.getCartInSession(request);
      CustomerInfo customerInfo = new CustomerInfo(customerForm);
      cartInfo.setCustomerInfo(customerInfo);

      return "redirect:/shoppingCartConfirmation";
   }

   // GET: Show information to confirm.
   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
   public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);

      if (cartInfo == null || cartInfo.isEmpty()) {

         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {

         return "redirect:/shoppingCartCustomer";
      }
      model.addAttribute("myCart", cartInfo);

      try {
          orderDAO.saveOrder(cartInfo);
       } catch (Exception e) {

          return "shoppingCartConfirmation";
       }
      
      return "shoppingCartConfirmation";
   }

   // POST: Submit Cart (Save)
   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)

   public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);

      if (cartInfo.isEmpty()) {

         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {

         return "redirect:/shoppingCartCustomer";
      }
      try {
         orderDAO.saveOrder(cartInfo);
      } catch (Exception e) {

         return "shoppingCartConfirmation";
      }

      // Remove Cart from Session.
      Utils.removeCartInSession(request);

      // Store last cart.
      Utils.storeLastOrderedCartInSession(request, cartInfo);

      return "redirect:/shoppingCartFinalize";
   }

   @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
   public String shoppingCartFinalize(HttpServletRequest request, Model model) {

      CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

      if (lastOrderedCart == null) {
         return "redirect:/shoppingCart";
      }
      model.addAttribute("lastOrderedCart", lastOrderedCart);
      return "shoppingCartFinalize";
   }

   @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
   public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
         @RequestParam("code") String code) throws IOException {
      Product product = null;
      if (code != null) {
         product = this.productDAO.findProduct(code);
      }
      if (product != null && product.getImage() != null) {
         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
         response.getOutputStream().write(product.getImage());
      }
      response.getOutputStream().close();
   }

}