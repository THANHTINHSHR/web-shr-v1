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

//        response.sendRedirect("/body");
//      request.getRequestDispatcher("/body").forward(request, response);
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }


//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//
//        HttpSession session = request.getSession();
//        try{
//            if(session != null){                            //Infinite
//                response.sendRedirect("/body");
//                return;
//            }
//        }catch(Exception e){
//            e.toString();
//        }
//    }
}
