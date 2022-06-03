package server;

public class DataJSON {

    private String key;
    private String value;

    public DataJSON(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}