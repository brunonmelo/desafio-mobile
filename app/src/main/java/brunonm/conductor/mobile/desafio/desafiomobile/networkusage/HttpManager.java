package brunonm.conductor.mobile.desafio.desafiomobile.networkusage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

abstract class HttpManager {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static String getData(RequestPackage p) {
        String uri = p.getUri();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(7000, TimeUnit.MILLISECONDS)
                .build();
        Request request = null;
        try {
            switch (p.getRequestType()) {
                case GET:
                    HttpUrl.Builder builder = HttpUrl.parse(uri).newBuilder();

                    if (p.getParams().size() > 0)
                        for (Map.Entry<String, String> entry : p.getParams().entrySet()) {
                            builder.addQueryParameter(entry.getKey(), entry.getValue());
                        }

                    HttpUrl url = builder.build();

                    request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();
                    break;
                case POST:
                    RequestBody body = p.getFormBody() == null ?
                            RequestBody.create(JSON, p.getJsonParams()) :
                            p.getFormBody();

                    request = new Request.Builder()
                            .url(uri)
                            .post(body)
                            .build();
                    break;
            }

            Response response = client.newCall(request).execute();
            int code = response.code();

            if (code == 200) {
                ResponseBody body = response.body();
                return body.string();
            } else {
                throw new IOException();
            }
        } catch (IOException | NullPointerException e) {
            client.connectionPool().evictAll();
            client.dispatcher().executorService().shutdown();
            e.printStackTrace();
            return "ERROR";
        }
    }
}
