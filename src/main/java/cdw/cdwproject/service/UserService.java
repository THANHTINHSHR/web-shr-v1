package cdw.cdwproject.service;

import cdw.cdwproject.model.User.AuthenticationProvider;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.model.User.UserForm;
import cdw.cdwproject.oauth.CustomOAuth2User;
import org.springframework.social.connect.Connection;

import java.util.List;

public interface UserService {
   User getUserByEmail(String email);
   void updateUserAccount(User user);
   void saveUser(User user);
    List<User> getAllUser();
//    User registerNewUserAccount(UserForm appUserForm, List<String> roleNames);
    void createOauth2UserAfterLoginSuccess(String email, String name, AuthenticationProvider provider, boolean isAdmin);
     void saveRegister(User user);
     boolean checkOTPInvalid(String email, String otp);
     void unLockAccount(String email);
}
