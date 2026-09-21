package roomescape.time;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AdminTimeController {

    private final TimeService timeService;

    public AdminTimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping("/admin/times")
    public ResponseEntity<Time> create(@RequestBody Time time) {
        if (time.getValue() == null || time.getValue().isEmpty()) {
            throw new RuntimeException();
        }

        Time newTime = timeService.save(time);

        return ResponseEntity
                .created(URI.create("/admin/times/" + newTime.getId()))
                .body(newTime);
    }

    @DeleteMapping("/admin/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
