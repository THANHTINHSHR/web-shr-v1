package cdw.cdwproject.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCookieInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // to know cookie is enable or not, save a default cookie and try read it

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie dfCookie = null;
            for (Cookie c : cookies
            ) {
                if (c.getName().equalsIgnoreCase("enable")) {
                    dfCookie = c;
                    break;
                }
            }
            if ((dfCookie == null)) {
                dfCookie = new Cookie("enable", "enable");
                dfCookie.setPath("/");
                dfCookie.setMaxAge(60*60*24*3);
                response.addCookie(dfCookie);
            }

        } else {
            Cookie dfCookie = new Cookie("enable", "enable");
            dfCookie.setPath("/");
            dfCookie.setMaxAge(60*60*24*3);
            response.addCookie(dfCookie);
        }

        return true;
    }


}
