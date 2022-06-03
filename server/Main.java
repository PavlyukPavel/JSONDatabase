package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int THREAD_COUNT = 4;
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    //private static final String DB_PATH = "./JSON Database/task/src/server/data/"; -- Для тестирования локально
    private static final String DB_PATH = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "server" + File.separator +
            "data" + File.separator;

    private static final String DB_NAME = "db.json";

    public static void main(String[] args) {
        /* -- Stage 1 --

        CellBase base = new CellBase(100);
        try (CommandController cmdController = new CommandController(System.in, base)) {
            cmdController.work();
        } catch (Exception e) {
            System.out.printf("ERROR: %s", e.getMessage());
        } */

        /* -- Stage 2 --
        String address = "127.0.0.1";
        int port = 23456;
        String message = "A record # 12 was sent!";

        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            Socket socket = server.accept();
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String messFromClient = input.readUTF();
            System.out.println("Received: " + messFromClient);
            output.writeUTF(message);
            System.out.println("Sent: " + message);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }*/

        /*
        // -- Stage 3
        boolean bError = false;
        CellBase base = null;
        try {
            base = new CellBase(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            bError = true;
        }
        if (!bError) {
            String address = "127.0.0.1";
            int port = 23456;

            try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
                System.out.println("Server started!");

                while (true) {
                    Socket socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    String messFromClient = null;
                    try {
                        messFromClient = input.readUTF();
                    } catch (Exception e) {
                        bError = true;
                    }
                    if (!bError) {
                        Controller controller = new Controller();
                        Command cmd = Controller.makeCommand(base, messFromClient);
                        controller.setCommand(cmd);
                        String result = controller.executeCommand();
                        output.writeUTF(result);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        } */

        /*
        // -- Stage 4
        CellBase base = new CellBase();
        String address = "127.0.0.1";
        int port = 23456;
        boolean bExit = false;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");

            while (!bExit) {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                boolean bError = false;
                String messFromClient = null;
                try {
                    messFromClient = input.readUTF();
                } catch (Exception e) {
                    bError = true;
                }
                if (!bError) {
                    RequestJSON requestJSON = new Gson().fromJson(messFromClient, RequestJSON.class);
                    Controller controller = new Controller();
                    Command cmd = Controller.makeCommand(base, requestJSON);
                    controller.setCommand(cmd);
                    ResponseJSON responseJSON = controller.executeCommand();
                    output.writeUTF(new Gson().toJson(responseJSON));
                    bExit = cmd.isExit();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace(System.out);
        }*/

        // -- Stage 5
        CellBase base = null;
        try {
            base = new CellBase(DB_PATH, DB_NAME);
        } catch (CellBaseException e) {
            System.out.println("ERROR while opening database: " + e.getMessage());
            e.printStackTrace(System.out);
        }

        if (base != null) {
            try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
                System.out.println("Server started!");

                ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
                while (!server.isClosed()) {
                    boolean bErrorSocket = false;
                    Socket socket = null;
                    try {
                        socket = server.accept();
                    } catch (IOException e) {
                        bErrorSocket = true;
                    }
                    if (!bErrorSocket) {
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                        String messFromClient = null;
                        try {
                            messFromClient = input.readUTF();
                            executor.submit(new ClientSession(server, output, messFromClient, base));
                        } catch (IOException e) {
                            System.out.println("ERROR reading input data: " + e.getMessage());
                            e.printStackTrace(System.out);
                        }
                    }
                }
                executor.shutdown();
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
    }
}
