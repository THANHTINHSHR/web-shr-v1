package cdw.cdwproject.handler;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class CustomAuthenticationFailure extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        setDefaultFailureUrl("/login?error=true");
//        setDefaultFailureUrl("/login-fail");

//        super.onAuthenticationFailure(request, response, exception);

//        String errorMessage = "Invalid email or passwordcdc ";
        setDefaultFailureUrl("/login-fail?error=1");
        if(exception.getClass().isAssignableFrom(DisabledException.class)) {
//            errorMessage = "User disabled về";
            setDefaultFailureUrl("/login-fail?error=2");
        } if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
//            errorMessage = "Account expired";
            setDefaultFailureUrl("/login-fail?error=3");
        }
        if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
//            errorMessage = " User account is locked csscss";
            setDefaultFailureUrl("/login-fail?error=4");
        }


//        HttpSession session = request.getSession();

//        session.setAttribute("loginFailMess", errorMessage);
//        System.out.println("----- " + errorMessage);
//        HttpServletRequest req= (HttpServletRequest)  request;

//        session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
//        req.setAttribute("avs","sssssssssacsssss");
        super.onAuthenticationFailure(request, response, exception);
    }
}
