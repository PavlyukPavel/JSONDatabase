package client;

import java.util.ArrayList;
import java.util.List;

public class RequestJSON {
    private String type;
    private String key;
    private String value;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public RequestJSON(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public List<String> toList() {
        List<String> result = new ArrayList<>();
        if (type != null) {
            result.add(type);
        }
        if (key != null) {
            result.add(key);
        }
        if (value != null) {
            result.add(value);
        }
        return result;
    }
}