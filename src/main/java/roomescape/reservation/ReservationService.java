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

        Theme theme = themeRepository
                .findById(reservationRequest.getTheme())
                .orElseThrow(NoSuchElementException::new);

        Time time = timeRepository
                .findById(reservationRequest.getTime())
                .orElseThrow(NoSuchElementException::new);

        Reservation reservation;

        if (reservationRequest.getName() != null) {
            reservation = new Reservation(
                    null,
                    reservationRequest.getName(),
                    reservationRequest.getDate(),
                    time,
                    theme
            );
        }
        else {
            Member member = memberRepository
                    .findById(loginMember.getId())
                    .orElseThrow(NoSuchElementException::new);

            reservation = new Reservation(
                    member,
                    "",
                    reservationRequest.getDate(),
                    time,
                    theme
            );
        }

        Reservation savedReservation = reservationRepository.save(reservation);
        String reservationName;

        if (savedReservation.getMember() != null) {
            reservationName = savedReservation.getMember().getName();
        }
        else {
            reservationName = savedReservation.getName();
        }

        return new ReservationResponse(
                savedReservation.getId(),
                reservationName,
                savedReservation.getTheme().getName(),
                savedReservation.getDate(),
                savedReservation.getTime().getValue()
        );
    }

    public List<MyReservationResponse> findMyReservations(LoginMember loginMember) {
        return reservationRepository
                .findAllByMemberId(loginMember.getId())
                .stream()
                .map(reservation -> new MyReservationResponse(
                        reservation.getId(),
                        reservation.getTheme().getName(),
                        reservation.getDate(),
                        reservation.getTime().getValue(),
                        "예약"
                ))
                .toList();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                                    .stream()
                                    .map(reservation -> {
                                        String reservationName;

                                        if (reservation.getMember() != null) {
                                            reservationName = reservation.getMember().getName();
                                        }
                                        else {
                                            reservationName = reservation.getName();
                                        }

                                        return new ReservationResponse(
                                                reservation.getId(),
                                                reservationName,
                                                reservation.getTheme().getName(),
                                                reservation.getDate(),
                                                reservation.getTime().getValue()
                                        );
                                    })
                                    .toList();
    }
}
