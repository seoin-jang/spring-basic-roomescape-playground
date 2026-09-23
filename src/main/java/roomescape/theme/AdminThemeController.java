package roomescape.theme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AdminThemeController {

    private final ThemeService themeService;

    public AdminThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/admin/themes")
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) {
        Theme newTheme = themeService.save(theme);

        return ResponseEntity
                .created(URI.create("/admin/themes/" + newTheme.getId()))
                .body(newTheme);
    }

    @DeleteMapping("/admin/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
