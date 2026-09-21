package roomescape.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse createMember(MemberRequest memberRequest) {
        String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());

        Member member = new Member(
                memberRequest.getName(),
                memberRequest.getEmail(),
                encodedPassword,
                "USER"
        );

        Member savedMember = memberDao.save(member);

        return new MemberResponse(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getEmail()
        );
    }
}
