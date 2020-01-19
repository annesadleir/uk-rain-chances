package uk.co.littlestickyleaves.temp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Scratch {

    private static final String CLIENT_ID = "8b277490-5edf-4114-b3d5-529f71fbc70e";

    private static final String CLIENT_SECRET = "lY7kN3cF1yT0qW5vP7rT0mX0dU5lF4rJ0yN3rI1nX2fN8mF5vO";

    private static final double EXETER_LATITUDE = 50.7184;
    private static final double EXETER_LONGITUDE = -3.5339;

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api-metoffice.apiconnect.ibmcloud.com/metoffice/production/v0/forecasts/point/hourly" +
                        "?excludeParameterMetadata=false&includeLocationName=true" +
                        "&latitude=" + EXETER_LATITUDE + "&longitude=" + EXETER_LONGITUDE)
                .get()
                .addHeader("x-ibm-client-id", CLIENT_ID)
                .addHeader("x-ibm-client-secret", CLIENT_SECRET)
                .addHeader("accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}