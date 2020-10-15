package no.maardal.fantapp.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String token;

    LoggedInUserView(String token) {
        this.token = token;
    }

    public String getToken() {
    }
}