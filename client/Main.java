package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.Socket;
/*import java.util.HashMap;
import java.util.Map;*/
import java.util.Scanner;

import com.beust.jcommander.*;
import com.google.gson.*;

public class Main {

    //private static final String FILE_PATH = "./JSON Database/task/src/client/data/"; -- Для тестирования локально
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "client" + File.separator +
            "data" + File.separator;

    public static void main(String[] args) {
        /* -- Stage 2
        String address = "127.0.0.1";
        int port = 23456;
        String message = "Give me a record # 12";

        System.out.println("Client started!");
        try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(message);
            System.out.println("Sent: " + message);
            String response = input.readUTF();
            System.out.println("Received: " + response);
        } catch (Exception e) {
            System.out.println("FAILED connect to server: " + e.getMessage());
        }*/

        /*
        // -- Stage 3
        String address = "127.0.0.1";
        int port = 23456;

        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        String requestType = arguments.getRequestType();
        if (requestType != null) {
            try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
                System.out.println("Client started!");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                int indexCell = arguments.getIndexCell();
                String value = arguments.getValue();
                String message = requestType;
                if (indexCell > 0) {
                    message = message + " " + arguments.getIndexCell();
                    if (value != null) {
                        message = message + " " + arguments.getValue();
                    }
                }
                message = message.trim();

                if (!"".equals(message)) {
                    output.writeUTF(message);
                    System.out.println("Sent: " + message);

                    String response = input.readUTF();
                    System.out.println("Received: " + response);
                }
            } catch (Exception e) {
                System.out.println("FAILED connect to server: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }*/

        /*
        // -- Stage 4
        String address = "127.0.0.1";
        int port = 23456;

        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        String requestType = arguments.getRequestType();
        if (requestType != null) {
            try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
                System.out.println("Client started!");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String indexCell = arguments.getIndexCell();
                String value = arguments.getValue();
                RequestJSON requestJSON = new RequestJSON(requestType, indexCell, value);
                String message = new Gson().toJson(requestJSON);

                if (!"".equals(message)) {
                    output.writeUTF(message);
                    System.out.println("Sent: " + message);

                    String response = input.readUTF();
                    System.out.println("Received: " + response);
                }
            } catch (Exception e) {
                System.out.println("FAILED connect to server: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }*/

        /*
        // -- Stage 5
        String address = "127.0.0.1";
        int port = 23456;

        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        String requestType;
        String indexCell;
        String value;
        String filename = arguments.getFilename();
        RequestJSON requestJSON = null;
        if (filename != null) {
            File file = new File(FILE_PATH + filename);
            try {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNext()) {
                    requestJSON = new Gson().fromJson(scanner.nextLine(), RequestJSON.class);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("ERROR while opening file: " + e.getMessage());
                System.out.println("Absolute path to file: " + file.getAbsolutePath());
            } catch (JsonSyntaxException e) {
                System.out.println("Bad format in file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            requestType = arguments.getRequestType();
            if (requestType != null) {
                indexCell = arguments.getIndexCell();
                value = arguments.getValue();
                requestJSON = new RequestJSON(requestType, indexCell, value);

            }
        }


        if (requestJSON != null) {
            try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
                System.out.println("Client started!");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String message = new Gson().toJson(requestJSON);

                if (!"".equals(message)) {
                    output.writeUTF(message);
                    System.out.println("Sent: " + message);

                    String response = input.readUTF();
                    System.out.println("Received: " + response);
                }
            } catch (Exception e) {
                System.out.println("FAILED connect to server: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("Bad input!");
        }
         */

        // -- Stage 6
        String address = "127.0.0.1";
        int port = 23456;

        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        String message = null;
        String filename = arguments.getFilename();
        if (filename != null) {
            File file = new File(FILE_PATH + filename);
            try {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNext()) {
                    message = scanner.nextLine();
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("ERROR while opening file: " + e.getMessage());
                System.out.println("Absolute path to file: " + file.getAbsolutePath());
            } catch (JsonSyntaxException e) {
                System.out.println("Bad format in file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            String requestType = arguments.getRequestType();
            if (requestType != null) {
                String indexCell = arguments.getIndexCell();
                String value = arguments.getValue();
                RequestJSON requestJSON = new RequestJSON(requestType, indexCell, value);
                message = new Gson().toJson(requestJSON);
            }
        }

        if (!"".equals(message)) {
            try (Socket socket = new Socket(InetAddress.getByName(address), port)) {
                System.out.println("Client started!");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                output.writeUTF(message);
                System.out.println("Sent: " + message);

                String response = input.readUTF();
                System.out.println("Received: " + response);
            } catch (Exception e) {
                System.out.println("FAILED connect to server: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        } else {
            System.out.println("Bad input!");
        }
    }
}