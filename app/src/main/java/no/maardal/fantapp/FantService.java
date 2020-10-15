package no.maardal.fantapp;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FantService {

    public static final String BASE_URL = "http://localhost:8080/resources/";
    static FantService SINGLETON;

    User user;

    String token;
    RequestQueue requestQueue;

    public static FantService initialize(Context context, String token) {
        SINGLETON = new FantService(context, token);
        return SINGLETON;
    }

    public FantService(Context context, String token) {
        this.token = token;
        this.requestQueue = Volley.newRequestQueue(context);
        loadUser();
    }

    private void loadUser() {
        requestQueue.add(new SecuredJsonObjectRequest(Request.Method.GET, BASE_URL + "auth/currentuser", null,
                response -> {
                    try {
                        user = new User(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, (Response.ErrorListener) this, token));
    }

    static class SecuredJsonObjectRequest extends JsonObjectRequest {
        String token;

        public SecuredJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest,
                                        Response.Listener<JSONObject> listener,
                                        @Nullable Response.ErrorListener errorListener,
                                        String token) {
            super(method, url, jsonRequest, listener, errorListener);
            this.token = token;
        }

        @Override
        public Map<String, String> getHeaders() {
            HashMap<String, String> result = new HashMap<>();
            result.put("Authorization", "Bearer" + token);
            return result;
        }
    }
}
