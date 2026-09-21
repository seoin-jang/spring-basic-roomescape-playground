package roomescape.login;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import roomescape.member.Member;
import roomescape.member.MemberDao;

@Service
public class LoginService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public LoginService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest loginRequest) {
        Member member;

        try {
            member = memberDao.findByEmail(loginRequest.getEmail());
        } catch (EmptyResultDataAccessException e) {
            throw new LoginAuthenticationException();
        }

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                member.getPassword()
        )) {
            throw new LoginAuthenticationException();
        }

        String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

        return Jwts.builder()
                   .setSubject(member.getId().toString())
                   .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                   .compact();
    }

    public Member findMemberByToken(String token) {
        try {
            Long memberId = Long.valueOf(
                    Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(
                                "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=".getBytes()
                        ))
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
            );

            return memberDao.findById(memberId);

        } catch (JwtException
                 | NumberFormatException
                 | EmptyResultDataAccessException e) {
            throw new LoginAuthenticationException();
        }
    }
}
