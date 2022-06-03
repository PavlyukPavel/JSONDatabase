package server;

import client.RequestJSON;
import com.google.gson.Gson;
import com.google.gson.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class ClientSession extends Thread {

    private ServerSocket server;
    private final DataOutputStream output;
    private final String messFromClient;
    private final CellBase base;

    public ClientSession(ServerSocket server, DataOutputStream output, String messFromClient, CellBase base) {
        super();
        this.server = server;
        this.output = output;
        this.messFromClient = messFromClient;
        this.base = base;
    }

    @Override
    public void run() {
        JsonElement jsonElement = JsonParser.parseString(messFromClient);
        JsonObject rootObject = jsonElement.getAsJsonObject();

        RequestJSON requestJSON = new RequestJSON(
                (rootObject.has("type") ? rootObject.get("type").getAsString() : null),
                (rootObject.has("key") ? rootObject.get("key").toString().replace("\\","") : null),
                (rootObject.has("value") ? rootObject.get("value").toString().replace("\\","") : null)
        );

        System.out.println("key in session = " + requestJSON.getKey());
        System.out.println("value in session = " + requestJSON.getValue());

        Controller controller = new Controller();
        Command cmd = Controller.makeCommand(base, requestJSON);
        controller.setCommand(cmd);
        ResponseJSON responseJSON = controller.executeCommand();
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ResponseJSON.class, new ResponseJSONSerializer())
                    //.setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();

            output.writeUTF(gson.toJson(responseJSON));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cmd.isExit()) { // Закрываем соединение с сервером
            try {
                server.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
