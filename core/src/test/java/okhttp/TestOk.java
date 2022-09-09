package okhttp;

import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestOk {

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Test
    public Object testClient(){
        Authenticator authenticator = okHttpClient.authenticator();
        Cache cache = okHttpClient.cache();
        ConnectionPool connectionPool = okHttpClient.connectionPool();

        return null;
    }



    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    //Post to a Server
    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Get a URL¶
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
