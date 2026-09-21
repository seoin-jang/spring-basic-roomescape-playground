package roomescape.theme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThemeController {

    private final ThemeDao themeDao;

    public ThemeController(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> list() {
        return ResponseEntity.ok(themeDao.findAll());
    }
}
