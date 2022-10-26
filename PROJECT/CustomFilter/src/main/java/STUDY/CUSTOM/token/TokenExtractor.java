package STUDY.CUSTOM.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class TokenExtractor {
    public static final String AUTHORIZATION = "Authorization";

    public String extract(HttpServletRequest request) throws Exception {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        /**
         * hasMoreElements : 읽어올 요소가 있는지 확인.
         */
        if (headers.hasMoreElements()) {
            return headers.nextElement();
        }
        throw new Exception("토큰이 존재하지 않습니다.");
    }
}
