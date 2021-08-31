package cdw.cdwproject.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieRequireInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        return HandlerInterceptor.super.preHandle(request, response, handler);
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null) {
////            response.sendRedirect("/cookie-require");
//            request.getRequestDispatcher("cookie-require").forward(request, response);
//
//        }
//        return  true;
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
//            response.sendRedirect("/cookie-require");
            request.getRequestDispatcher("cookie-require").forward(request, response);
//            response.sendRedirect("cookie-require");

        }
        return ;

    }
}
