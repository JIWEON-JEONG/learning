package STUDY.CUSTOM.user;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor
    static class UserRequestDto {
        @NotNull
        private String email;
        @NotNull
        private String password;
    }
}
