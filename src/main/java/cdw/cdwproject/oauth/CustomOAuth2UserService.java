package cdw.cdwproject.oauth;

import cdw.cdwproject.model.User.AuthenticationProvider;
import cdw.cdwproject.model.User.User;
import cdw.cdwproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserServiceImp userServiceImp;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oauth2User =  super.loadUser(userRequest);
        OAuth2User oauth2User =  super.loadUser(userRequest);

        User user = userServiceImp.getUserByEmail(oauth2User.getAttribute("email"));
        if(user == null ){
           userServiceImp.createOauth2UserAfterLoginSuccess(oauth2User.getAttribute("email"), oauth2User.getAttribute("name"), AuthenticationProvider.GOOGLE, false);

            user = userServiceImp.getUserByEmail(oauth2User.getAttribute("email"));
        }

    return new CustomOAuth2User(oauth2User, user);

    }
}
