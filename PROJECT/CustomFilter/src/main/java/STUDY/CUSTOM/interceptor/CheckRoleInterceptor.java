package STUDY.CUSTOM.interceptor;

import STUDY.CUSTOM.token.TokenExtractor;
import STUDY.CUSTOM.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckRoleInterceptor implements HandlerInterceptor {

    private static final String[] whitelist = {"/", "/login", "/join"};

    private final TokenService tokenService;

    private final TokenExtractor tokenExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("CheckRoleInterceptor 동작");
        String requestURI = request.getRequestURI();
        if (!checkIsNotWhitelist(requestURI)) {
            return Boolean.TRUE;
        }
        String token = tokenExtractor.extract(request);
        Map<String, Object> payload = tokenService.parse(token);
        if (requestURI.startsWith("/admin")) {
            return tokenService.isAdmin(payload);
        }
        return tokenService.isUser(payload);
    }

    private boolean checkIsNotWhitelist(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
