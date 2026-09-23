package roomescape.theme;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public Theme save(Theme theme) {
        return themeDao.save(theme);
    }

    public void deleteById(Long id) {
        themeDao.deleteById(id);
    }
}
