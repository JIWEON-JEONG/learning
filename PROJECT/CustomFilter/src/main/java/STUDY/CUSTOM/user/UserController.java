package STUDY.CUSTOM.user;

import STUDY.CUSTOM.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserDto.UserRequestDto requestDto) throws Exception {
        log.info("join controller");
        try {
            User user = User.builder()
                    .email(requestDto.getEmail())
                    .password(requestDto.getPassword())
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("회원가입 Exception");
        }

        return new ResponseEntity<String>("JOIN",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto.UserRequestDto requestDto) throws Exception {
        log.info("login controller");
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
        user.setRole(Role.USER);
        userRepository.save(user);
        String token = tokenService.encode(user);
        return new ResponseEntity<String>(token,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> authUser() {
        log.info("user controller");
        return new ResponseEntity<String>("USER PAGE", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> authAdmin() {
        log.info("admin controller");
        return new ResponseEntity<String>("ADMIN PAGE", HttpStatus.OK);
    }
}
