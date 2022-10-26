package STUDY.CUSTOM.filter;

import STUDY.CUSTOM.token.TokenExtractor;
import STUDY.CUSTOM.token.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter  {

    private static final String[] whitelist = {"/", "/login", "/join"};

    private final TokenExtractor tokenExtractor;

    private final TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        try {
            logger.info("AuthFilter 시작");
            if (checkIsNotWhitelist(requestURI)) {
                logger.info("Token 유효성 검사");
                String token = tokenExtractor.extract(request);
                tokenValidator.validate(token);
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    //httpResponse.sendRedirect("/login?redirectURL=" + requestURI);

    /**
     * whiteList의 경우 인증 체크를 안하도록 한다.
     */
    private boolean checkIsNotWhitelist(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
