package no.maardal.fantapp.data;

import android.renderscript.ScriptGroup;

import no.maardal.fantapp.FantService;
import no.maardal.fantapp.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(FantService.BASE_URL + "auth/login?uid=" + username + "&pwd=" + password);
            connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(true);
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String token = br.readLine();
                LoggedInUser fakeUser = new LoggedInUser(username, token);
                connection.getInputStream().close();
                return new Result.Success<>(fakeUser);
            }
            else {
                System.out.println("ERROR: response code " + connection.getResponseCode() + " " + connection.getResponseCode());
                System.out.println(url);
            }
             return new Result.Error(new IOException("Error logging in " + connection.getResponseMessage()));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}