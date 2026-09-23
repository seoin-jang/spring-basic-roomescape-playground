package roomescape.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.member.Member;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    public AdminInterceptor(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String token = extractTokenFromCookie(request.getCookies());

        if (token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        try {
            Member member = loginService.findMemberByToken(token);

            if (!member.getRole().equals("ADMIN")) {
                response.setStatus(401);
                return false;
            }

            return true;

        } catch (LoginAuthenticationException e) {
            response.setStatus(401);
            return false;
        }
    }

    private String extractTokenFromCookie(Cookie[] cookies) {
        if (cookies == null) {
            return "";
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                return cookie.getValue();
            }
        }

        return "";
    }
}
