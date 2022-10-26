package STUDY.CUSTOM.token;

import STUDY.CUSTOM.user.Role;
import STUDY.CUSTOM.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * @Value("${spring.jwt.secret}") 이친구에 대해서 좀 알아보자.
 */
@Slf4j
@Service
@Transactional
public class TokenService {

    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Value("${spring.jwt.secret}")
    private String SECRET;
    private final int EXPIRE_SECONDS = 60 * 60;
    private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private SecretKeySpec SECRET_KEY;

    @PostConstruct
    public void init() {
        this.SECRET_KEY = new SecretKeySpec(SECRET.getBytes(), ALGORITHM.getJcaName());
    }
    /**
     * 유저 데이터를 JWT로 암호화 하는 함수.
     * @param user User 객체
     * @return String 생성된 JWT String
     */
    public String encode(User user) {
        Date now = new Date();
        return Jwts.builder()
                //따로 config 로 빼줘도 좋음 - JWT 라는 value 값
                .setHeaderParam("type", "JWT")
                .setIssuer("JIWEON-JEONG")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(EXPIRE_SECONDS).toMillis()))
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .signWith(ALGORITHM, SECRET_KEY)
                .compact();
    }

    /**
     * JWT String 을 Map 으로 파싱해주는 함수.
     * @param token JWT String
     * @return Map<String, Object> key, value 형태의 Map 데이터
     * @throws ParseException 파싱 실패 예외처리
     */
    public Map<String, Object> parse(String token) throws ParseException {
        String[] chunks = token.split("\\.");
        String jwtBodyString = new String(decoder.decode(chunks[1]));
        JSONParser parser = new JSONParser(jwtBodyString);
        return parser.parseObject();
    }

    public boolean isAdmin(Map<String, Object> payload) throws ParseException {
        String role = payload.get("role").toString();
        if (role.equals(Role.ADMIN.toString())) {
            return Boolean.TRUE;
        }
        throw new RuntimeException("권한 ADMIN 아닙니다.");
    }

    public boolean isUser(Map<String, Object> payload) throws ParseException {
        String role = payload.get("role").toString();
        if (role.equals(Role.USER.toString())) {
            return Boolean.TRUE;
        }
        throw new RuntimeException("권한 USER 아닙니다.");
    }

    public void checkExpToken(String token) throws RuntimeException {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        Date current = new Date();

        if (expiration.before(current)){
            throw new RuntimeException("Token 갱신 해주세요.");
        }
    }


    public boolean checkValidateToken(String token) throws RuntimeException {
        String[] chunks = token.split("\\.");
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(ALGORITHM, SECRET_KEY);
        if (!validator.isValid(tokenWithoutSignature, signature)) {
            throw new RuntimeException("Could not verify JWT token integrity!");
        }
        return true;
    }
}
