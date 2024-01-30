package shop.mtcoding.blog.user;

/*
    요청 DTO = Data Transfer Object
 */

import lombok.Data;

public class UserRequest {


    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
