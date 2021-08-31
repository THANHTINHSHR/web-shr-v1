package cdw.cdwproject.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
        // all request, add cookie to define cookie enable or not
        registry.addInterceptor(new AddCookieInterceptor()).excludePathPatterns("/cookie-require").order(1);
        //
        registry.addInterceptor(new CookieRequireInterceptor()).excludePathPatterns("/cookie-require").order(2);
    }
}
