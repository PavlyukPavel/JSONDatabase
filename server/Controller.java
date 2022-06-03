package server;

import client.RequestJSON;

import java.util.List;
import java.util.Locale;

public class Controller {

    protected Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public ResponseJSON executeCommand() {
        if (command != null) {
            return command.execute();
        } else {
            return new ResponseJSON("ERROR", null,"No command");
        }
    }

    // Разбить строку на параметры (максимум 3 параметра)
    /*private static List<String> splitString(String sInput) {
        List<String> params = new ArrayList<>();
        String temp = sInput.trim();
        int iSpace = temp.indexOf(" ",0);
        int iCnt = 0;
        //System.out.println("iSpace = "+ iSpace);
        while (iSpace > 0) {
            //System.out.println("sub = " + temp.substring(0, iSpace).trim());
            params.add(temp.substring(0, iSpace).trim());
            iCnt++;
            temp = temp.substring(iSpace + 1).trim();
            iSpace = temp.indexOf(" ",0);
            if (iCnt == 2){
                break;
            }
        }
        if (!"".equals(temp)) {
            params.add(temp);
        }
        return params;
    }*/

    // Создать нужную команду на основе входных данных
    public static Command makeCommand(CellBase base, RequestJSON requestJSON) {
        Command result = null;
        List<String> params = requestJSON.toList();
        if (params.size() >= 1) {
            if (!"".equals(params.get(0))) {
                switch (params.get(0).toLowerCase(Locale.ROOT)) {
                    case "set":
                        if (params.size() == 3) {
                            try {
                                result = new SetCommand(base, params.get(1), params.get(2));
                            } catch (Exception e) {
                                System.out.println("ERROR: " + e.getMessage());
                            }
                        } else {
                            System.out.println("INVALID PARAMETERS NUMBER!");
                        }
                        break;
                    case "get":
                        if (params.size() == 2) {
                            try {
                                result = new GetCommand(base, params.get(1));
                            } catch (Exception e) {
                                System.out.println("ERROR: " + e.getMessage());
                            }
                        } else {
                            System.out.println("INVALID PARAMETERS NUMBER!");
                        }
                        break;
                    case "delete":
                        if (params.size() == 2) {
                            try {
                                result = new DeleteCommand(base, params.get(1));
                            } catch (Exception e) {
                                System.out.println("ERROR: " + e.getMessage());
                            }
                        } else {
                            System.out.println("INVALID PARAMETERS NUMBER!");
                        }
                        break;
                    case "exit":
                        result = new ExitCommand();
                        break;
                    default:
                        System.out.println("INVALID COMMAND NAME!");
                }
            }
        } else {
            System.out.println("INVALID COMMAND WITH PARAMS!");
        }
        return result;
    }
}
