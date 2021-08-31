package cdw.cdwproject.oauth;

import cdw.cdwproject.model.User.MyUserDetails;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    @Autowired
    private OAuth2User oAuth2User;
//    @Autowired
//    private UserServiceImp userServiceImp;
User user;


    public CustomOAuth2User(OAuth2User oAuth2User, User user) {
        this.oAuth2User = oAuth2User;
        this.user = user;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        MyUserDetails myUserDetails = new MyUserDetails(user);
        return myUserDetails.getAuthorities();
    }

    @Override
    public String getName() {
        // name user account
//        return oAuth2User.getName();
        return oAuth2User.getAttribute("name");

    }
    public String getFullName(){
//        return oAuth2User.getName();


        return oAuth2User.getAttribute("name");
    }

public String getEmail(){
    System.out.println("get email :" + oAuth2User.getAttribute("email"));

        return oAuth2User.getAttribute("email");
}

}
