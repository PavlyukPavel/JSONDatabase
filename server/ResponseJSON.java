package server;

import com.google.gson.annotations.Expose;

public class ResponseJSON {
    private String response;
    private String value;
    private String reason;

    public ResponseJSON(String response) {
        this.response = response;
    }

    public ResponseJSON(String response, String value, String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}