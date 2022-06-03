package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandController implements Closeable {

    protected BufferedReader reader;
    protected CellBase base;

    public CommandController(InputStream inStream, CellBase base) {
        this.reader = new BufferedReader(new InputStreamReader(inStream));
        this.base = base;
    }

    // Разбить строку на параметры (максимум 3 параметра)
    /*public static List<String> splitString(String sInput) {
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
    }

    // Считыватель команд пользователя
    public void work() throws IOException {
        boolean bExit = false;
        while (!bExit) {
            String sInput = reader.readLine();
            List<String> params = splitString(sInput);

            if (params.size() >= 1) {
                if (!"".equals(params.get(0))) {
                    switch (params.get(0).toLowerCase(Locale.ROOT)) {
                        case "set":
                            if (params.size() == 3) {
                                try {
                                    int index = Integer.parseInt(params.get(1));
                                    base.setCell(index, params.get(2));
                                    System.out.println("OK");
                                } catch (NumberFormatException e) {
                                    System.out.println("CONVERT TO NUMBER ERROR!");
                                } catch (Exception e) {
                                    System.out.println("ERROR");
                                }
                            } else {
                                System.out.println("INVALID PARAMETERS NUMBER!");
                            }
                            break;
                        case "get":
                            if (params.size() == 2) {
                                try {
                                    int index = Integer.parseInt(params.get(1));
                                    System.out.println(base.getCell(index));
                                } catch (NumberFormatException e) {
                                    System.out.println("CONVERT TO NUMBER ERROR!");
                                } catch (Exception e) {
                                    System.out.println("ERROR");
                                }
                            } else {
                                System.out.println("INVALID PARAMETERS NUMBER!");
                            }
                            break;
                        case "delete":
                            if (params.size() == 2) {
                                try {
                                    int index = Integer.parseInt(params.get(1));
                                    base.delCell(index);
                                    System.out.println("OK");
                                } catch (NumberFormatException e) {
                                    System.out.println("CONVERT TO NUMBER ERROR!");
                                } catch (Exception e) {
                                    System.out.println("ERROR");
                                }
                            } else {
                                System.out.println("INVALID PARAMETERS NUMBER!");
                            }
                            break;
                        case "exit":
                            bExit = true;
                            break;
                        default:
                            System.out.println("INVALID COMMAND NAME!");
                    }
                }
            } else {
                System.out.println("INVALID COMMAND WITH PARAMS!");
            }
        }
    }*/

    @Override
    public void close() throws IOException {
        reader.close();
    }
}