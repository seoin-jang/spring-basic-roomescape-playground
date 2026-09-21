package roomescape.login;

public class LoginAuthenticationException extends RuntimeException {

    public LoginAuthenticationException() {
        super("인증에 실패했습니다.");
    }
}
