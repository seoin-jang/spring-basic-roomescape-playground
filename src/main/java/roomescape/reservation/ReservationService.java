package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.login.LoginMember;
import roomescape.member.Member;
import roomescape.member.MemberRepository;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;
import roomescape.time.Time;
import roomescape.time.TimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ThemeRepository themeRepository;
    private final TimeRepository timeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            MemberRepository memberRepository,
            ThemeRepository themeRepository,
            TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.themeRepository = themeRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse save(ReservationRequest reservationRequest, LoginMember loginMember) {
        Member member;

        if (reservationRequest.getName() != null) {
            member = memberRepository
                    .findByName(reservationRequest.getName())
                    .orElseThrow(NoSuchElementException::new);
        }
        else {
            member = memberRepository
                    .findById(loginMember.getId())
                    .orElseThrow(NoSuchElementException::new);
        }

        Theme theme = themeRepository
                .findById(reservationRequest.getTheme())
                .orElseThrow(NoSuchElementException::new);

        Time time = timeRepository
                .findById(reservationRequest.getTime())
                .orElseThrow(NoSuchElementException::new);

        Reservation reservation = new Reservation(member.getName(), reservationRequest.getDate(), time, theme);
        Reservation savedReservation = reservationRepository.save(reservation);

        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getName(),
                savedReservation.getTheme().getName(),
                savedReservation.getDate(),
                savedReservation.getTime().getValue()
        );
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                                    .stream()
                                    .map(reservation -> new ReservationResponse(
                                            reservation.getId(),
                                            reservation.getName(),
                                            reservation.getTheme().getName(),
                                            reservation.getDate(),
                                            reservation.getTime().getValue()
                                    ))
                                    .toList();
    }
}
