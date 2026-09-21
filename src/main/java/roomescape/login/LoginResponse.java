package roomescape.login;

public class LoginResponse {

    private final String name;

    public LoginResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
