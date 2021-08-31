package cdw.cdwproject.controller.admin;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.oauth.CustomOAuth2User;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationManagerController {
    @Autowired
    private UserServiceImp userServiceImp;


    @GetMapping("/admin/notification/person")
    public String showPersonNotification(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails,  @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
        model.addAttribute("userName", user.getName());
        return "admin/person-notification";

    }
    @GetMapping("/admin/notification/server")
    public String showServerNotification(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails,   @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        User user;
        try {
            user = userServiceImp.getUserByEmail(oAuth2User.getEmail());
        } catch (NullPointerException e) {


            user = myUserDetails.getUser();
        }
        model.addAttribute("userName", user.getName());
        return "admin/server-notification";

    }
}
