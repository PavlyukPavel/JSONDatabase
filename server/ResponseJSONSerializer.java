package server;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ResponseJSONSerializer implements JsonSerializer<ResponseJSON> {
    @Override
    public JsonElement serialize(ResponseJSON src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject responseJsonObj = new JsonObject();
        if (src.getResponse() != null) {
            responseJsonObj.addProperty("response", src.getResponse());
        }
        if (src.getValue() != null) {
            try {
                JsonElement valueElement = JsonParser.parseString(src.getValue());
                responseJsonObj.add("value", valueElement);
            } catch (JsonSyntaxException e) {
                responseJsonObj.addProperty("value", src.getValue());
            }
        }
        if (src.getReason() != null) {
            responseJsonObj.addProperty("reason", src.getReason());
        }
        return responseJsonObj;
    }
}
