package STUDY.CUSTOM.config;

import STUDY.CUSTOM.filter.AuthFilter;
import STUDY.CUSTOM.interceptor.CheckRoleInterceptor;
import STUDY.CUSTOM.token.TokenExtractor;
import STUDY.CUSTOM.token.TokenService;
import STUDY.CUSTOM.token.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthFilter authFilter;

    private final CheckRoleInterceptor checkRoleInterceptor;

    @Bean
    public FilterRegistrationBean tokenFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(authFilter);
        filterRegistrationBean.setOrder(1);
//        필터를 적용할 URL 패턴을 지정하며, 하나 이상의 패턴을 지정 할 수도 있다.
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkRoleInterceptor)
                .order(1)
                .addPathPatterns("/*")
                .excludePathPatterns("/login", "/", "/join");
    }
}
