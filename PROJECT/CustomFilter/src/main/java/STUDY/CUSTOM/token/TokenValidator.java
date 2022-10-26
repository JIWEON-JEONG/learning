package STUDY.CUSTOM.token;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final TokenService tokenService;

    public void validate(String token) throws Exception {
        tokenService.checkValidateToken(token);
        tokenService.checkExpToken(token);
    }
}
