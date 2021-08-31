package cdw.cdwproject.controller;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.order.Order;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.security.SecuriryPasswordGenerator;
import cdw.cdwproject.service.OrderServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import cdw.cdwproject.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private OrderServiceImp orderServiceImp;
    @Autowired
    private OrderSpecification orderSpecification;
    @Autowired
    private SecuriryPasswordGenerator securiryPasswordGenerator;

    //    @GetMapping("/my-account")
//    public String myAccount(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
//        User user = userDetails.getUser();
//        // notification
//        //order
//        Pageable requestedPage = PageRequest.of(0, 5, Sort.by("createDate"));
//        Page<Order> orders = orderServiceImp.getOrdersByUserID(user.getId(), requestedPage);
////        Order order = new Order
//        // user Detail
//        model.addAttribute("orders", orders.getContent());
//        model.addAttribute("user", userDetails.getUser());
//        model.addAttribute("totalPages", orders.getTotalPages());
//
//        return "user/my-account";
//
//    }
//    @GetMapping("/my-account/account-orders")
//    public String myAccount(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
//        User user = userDetails.getUser();
//        // notification
//        //order
//        Pageable requestedPage = PageRequest.of(0, 5, Sort.by("CREATE_AT"));
//        Page<Order> orders = orderServiceImp.getOrdersByUserID(user.getId(), requestedPage);
////        Order order = new Order
//        // user Detail
//        model.addAttribute("orders", orders.getContent());
//        model.addAttribute("user", userDetails.getUser());
//        model.addAttribute("totalPages", orders.getTotalPages());
//
//        return "user/account-orders";
//
//    }
    @GetMapping("/my-account/account-details")
    public String showAccountDetails(Model model , @AuthenticationPrincipal CustomOAuth2User oAuth2User, @AuthenticationPrincipal MyUserDetails userDetails) {

        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        model.addAttribute("name", user.getName());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("shippingAddress", user.getShippingAddress());
        return "user/account-details";

    }

    @PostMapping("/update-account-details")
    public String updateAccountDetails(Model model , @AuthenticationPrincipal CustomOAuth2User oAuth2User, @AuthenticationPrincipal MyUserDetails userDetails, @RequestParam(value = "new-name") String newName, @RequestParam("new-phone") String newPhone, @RequestParam("new-shipping-address") String newShippingAddress) {

        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }

//        userServiceImp.updateUserAccountDetails(user.getId(), newName, newPhone, newShippingAddress);
        user.setName(newName);
        user.setPhone(newPhone);
        user.setShippingAddress(newShippingAddress);
        userServiceImp.updateUserAccount(user);
        model.addAttribute("name", user.getName());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("shippingAddress", user.getShippingAddress());
        model.addAttribute("dSuccess",
                "update success");
        return "user/account-details";

    }

    @PostMapping("/update-account-password")
    public String updateAccountPassword(Model model , @AuthenticationPrincipal CustomOAuth2User oAuth2User, @AuthenticationPrincipal MyUserDetails userDetails, @RequestParam("old-pass") String oldPass, @RequestParam("new-pass") String newPass) {


        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = userDetails.getUser();
        }
        if (securiryPasswordGenerator. isPasswordMatches(oldPass, user.getPassword())) {
            String ecodePass = securiryPasswordGenerator.encodePassword(newPass);
            user.setPassword(ecodePass);
            userServiceImp.updateUserAccount(user);
            model.addAttribute("pSuccess",
                    "update success");
        } else {
            model.addAttribute("pFail",
                    "password incorrect");
        }

        model.addAttribute("name", user.getName());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("shippingAddress", user.getShippingAddress());

        return "user/account-details";
    }

    @GetMapping({"/my-account/account-notification", "my-account"})
    public String showNotification() {
        return "user/account-notification";
    }
}

