package brunonm.conductor.mobile.desafio.desafiomobile.networkusage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import brunonm.conductor.mobile.desafio.desafiomobile.enums.RequestType;
import okhttp3.RequestBody;

class RequestPackage {

    private String uri;
    private RequestType method = RequestType.GET;
    private Map<String, String> params = new HashMap<>();
    private RequestBody formBody;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(String key, String value) {
        params.put(key, value);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(RequestType method) {
        this.method = method;
    }

    public void setFormBody(RequestBody formBody) {
        this.formBody = formBody;
    }

    public RequestBody getFormBody() {
        return formBody;
    }

    RequestType getRequestType() {
        return method;
    }

    String getJsonParams() {
        JSONObject json = new JSONObject();

        for (String key : params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                json.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

}
