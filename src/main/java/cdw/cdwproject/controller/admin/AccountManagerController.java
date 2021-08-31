package cdw.cdwproject.controller.admin;


import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.ProductServiceImp;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AccountManagerController {
    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/admin/account-list")
    public String showAccountList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }

        List<User> users = userServiceImp.getAllUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("users", users);
        return "admin/index";

    }

}
