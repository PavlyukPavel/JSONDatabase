package server;

/*
// Stage 3
import java.util.ArrayList;
import java.util.List;

public class CellBase {

    protected int size;
    protected List<String> base;

    public CellBase(int size) throws CellBaseException {
        if (size <= 0) {
            throw new CellBaseException("Invalid cell size");
        } else {
            this.size = size;
            base = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                base.add("");
            }
        }
    }

    private void checkUserIndex(int index) throws CellBaseException {
        if (index < 1 ||index > size) {
            throw new CellBaseException("Invalid index");
        }
    }

    public void setCell(int index, String value) throws CellBaseException {
        checkUserIndex(index);
        base.set(index - 1, value);
    }

    public String getCell(int index) throws CellBaseException {
        checkUserIndex(index);
        String value = base.get(index - 1);
        if ("".equals(value)) {
            throw new CellBaseException("Cell is empty");
        }
        return value;
    }

    public void delCell(int index) throws CellBaseException {
        checkUserIndex(index);
        setCell(index, "");
    }
}
*/

/*
// Stage 4
import java.util.HashMap;

public class CellBase {

    protected HashMap<String, String> base;

    public CellBase() {
        base = new HashMap<>();
    }

    private void checkUserIndex(String index) throws CellBaseException {
        if (!base.containsKey(index)) {
            throw new CellBaseException("No such key");
        }
    }

    public void setCell(String index, String value) throws CellBaseException {
        base.put(index, value);
    }

    public String getCell(String index) throws CellBaseException {
        checkUserIndex(index);
        return base.get(index);
    }

    public void delCell(String index) throws CellBaseException {
        checkUserIndex(index);
        base.remove(index);
    }
}
*/

/*
// Stage 5
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CellBase {

    protected String filePath;
    protected String fileName;
    protected HashMap<String, String> base;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public CellBase(String filePath, String fileName) throws CellBaseException {
        this.filePath = filePath;
        this.fileName = fileName;
        // Создадим файл БД, если его еще нет
        if (!Files.exists(Path.of(filePath + fileName))) {
            File file = new File(filePath + fileName);
            try (FileWriter writer = new FileWriter(file)) {

            } catch (IOException e) {
                throw new CellBaseException(e.getMessage());
            }
        }
        base = readAllDB();
    }

    private HashMap<String, String> readAllDB() throws CellBaseException {
        HashMap<String, String> base = new HashMap<>();
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath + fileName), Charset.defaultCharset());
            for (String line : lines) {
                DataJSON dataJSON = new Gson().fromJson(line, DataJSON.class);
                base.put(dataJSON.getKey(), dataJSON.getValue());
            }
        } catch (Exception e) {
            throw new CellBaseException(e.getMessage());
        } finally {
            readLock.unlock();
        }
        return base;
    }

    //Запись БД в файл на диске
    private synchronized void flushDB() throws CellBaseException {
        File file = new File(filePath + fileName);
        try (FileWriter writer = new FileWriter(file)) {
            for (var entry : base.entrySet()) {
                DataJSON dataJSON = new DataJSON(entry.getKey(), entry.getValue());
                writer.write(new Gson().toJson(dataJSON) + "\n");
            }
        } catch (IOException e) {
            throw new CellBaseException(e.getMessage());
        }
    }

    private void checkUserIndex(String index) throws CellBaseException {
        if (!base.containsKey(index)) {
            throw new CellBaseException("No such key");
        }
    }

    public String getCell(String index) throws CellBaseException {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            checkUserIndex(index);
            return base.get(index);
        } catch (Exception e) {
            throw new CellBaseException(e.getMessage());
        } finally {
            readLock.unlock();
        }
    }

    public void setCell(String index, String value) throws CellBaseException {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            base.put(index, value);
            flushDB();
        } finally {
            writeLock.unlock();
        }
    }

    public void delCell(String index) throws CellBaseException {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            checkUserIndex(index);
            base.remove(index);
            flushDB();
        } catch (Exception e) {
            throw new CellBaseException(e.getMessage());
        } finally {
            writeLock.unlock();
        }
    }
}*/

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CellBase {

    protected String filePath;
    protected String fileName;
    protected HashMap<String, String> base;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public CellBase(String filePath, String fileName) throws CellBaseException {
        this.filePath = filePath;
        this.fileName = fileName;
        // Создадим файл БД, если его еще нет
        if (!Files.exists(Path.of(filePath + fileName))) {
            File file = new File(filePath + fileName);
            try (FileWriter writer = new FileWriter(file)) {

            } catch (IOException e) {
                throw new CellBaseException(e.getMessage());
            }
        }
        base = readAllDB();
    }

    private HashMap<String, String> readAllDB() throws CellBaseException {
        HashMap<String, String> base = new HashMap<>();
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath + fileName), Charset.defaultCharset());
            for (String line : lines) {
                DataJSON dataJSON = new Gson().fromJson(line, DataJSON.class);
                base.put(dataJSON.getKey(), dataJSON.getValue());
            }
        } catch (Exception e) {
            throw new CellBaseException(e.getMessage());
        } finally {
            readLock.unlock();
        }
        return base;
    }

    //Запись БД в файл на диске
    private synchronized void flushDB() throws CellBaseException {
        File file = new File(filePath + fileName);
        try (FileWriter writer = new FileWriter(file)) {
            for (var entry : base.entrySet()) {
                DataJSON dataJSON = new DataJSON(entry.getKey(), entry.getValue());
                writer.write(new Gson().toJson(dataJSON) + "\n");
            }
        } catch (IOException e) {
            throw new CellBaseException(e.getMessage());
        }
    }

    private void checkUserIndex(String masterIndex) throws CellBaseException {
        if (!base.containsKey(masterIndex)) {
            throw new CellBaseException("No such key");
        }
    }

    public String getCell(String index) throws CellBaseException {
        JsonElement jsonElement = JsonParser.parseString(index);
        boolean isArray = jsonElement.isJsonArray();
        if (!isArray) {
            Lock readLock = lock.readLock();
            readLock.lock();
            try {
                checkUserIndex(index);
                return base.get(index);
            } catch (Exception e) {
                throw new CellBaseException(e.getMessage());
            } finally {
                readLock.unlock();
            }
        } else {
            boolean bError = false;
            String sResult = null;
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() > 0) {
                sResult = getCell(jsonArray.get(0).toString()); //возьмем сохраненное значение value у корневого элемента массива JSON
                try {
                    JsonElement jsonBaseElement = JsonParser.parseString(sResult);
                    JsonObject jsonBaseObject = jsonBaseElement.getAsJsonObject();
                    for (int i = 1; i < jsonArray.size(); i++) {
                        JsonElement jsonArrInner = jsonArray.get(i);
                        jsonBaseElement = jsonBaseObject.get(jsonArrInner.getAsString());
                        if (jsonBaseElement != null) {
                            if (jsonBaseElement.isJsonObject()) {
                                jsonBaseObject = jsonBaseElement.getAsJsonObject();
                                sResult = jsonBaseObject.toString();
                            } else {
                                sResult = jsonBaseElement.getAsString();
                            }
                        } else {
                            bError = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    bError = true;
                }
            } else {
                bError = true;
            }
            if (bError) {
                throw new CellBaseException("No such key");
            } else {
                return sResult;
            }
        }
    }

    public void setCell(String index, String value) throws CellBaseException {
        JsonElement jsonElement = JsonParser.parseString(index);
        boolean isArray = jsonElement.isJsonArray();
        if (!isArray) {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            try {
                base.put(index, value);
                flushDB();
            } finally {
                writeLock.unlock();
            }
        } else {
            boolean bError = false;
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() > 0) {
                String sBaseValue = getCell(jsonArray.get(0).toString()); //возьмем сохраненное значение value у корневого элемента массива JSON
                try {
                    JsonElement jsonBaseElement = JsonParser.parseString(sBaseValue);
                    JsonObject jsonBaseObject = jsonBaseElement.getAsJsonObject();
                    JsonElement jsonInnerElement = jsonBaseElement;
                    JsonObject jsonInnerObject = jsonBaseObject;
                    for (int i = 1; i < jsonArray.size()-1; i++) {
                        JsonElement jsonArrInner = jsonArray.get(i);
                        jsonInnerElement = jsonInnerObject.get(jsonArrInner.getAsString());
                        if (jsonInnerElement == null) {
                            jsonInnerObject.addProperty(jsonArrInner.getAsString(), "");
                        }
                    }
                    jsonInnerObject = jsonInnerElement.getAsJsonObject();
                    jsonInnerObject.add(jsonArray.get(jsonArray.size() - 1).getAsString(), JsonParser.parseString(value));
                    setCell(jsonArray.get(0).toString(), jsonBaseElement.toString());
                } catch (Exception e) {
                    bError = true;
                }
            } else {
                bError = true;
            }
            if (bError) {
                throw new CellBaseException("No such key");
            }
        }
    }

    public void delCell(String index) throws CellBaseException {
        JsonElement jsonElement = JsonParser.parseString(index);
        boolean isArray = jsonElement.isJsonArray();
        if (!isArray) {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            try {
                checkUserIndex(index);
                base.remove(index);
                flushDB();
            } catch (Exception e) {
                throw new CellBaseException(e.getMessage());
            } finally {
                writeLock.unlock();
            }
        } else {
            boolean bError = false;
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (jsonArray.size() > 0) {
                String sBaseValue = getCell(jsonArray.get(0).toString()); //возьмем сохраненное значение value у корневого элемента массива JSON
                try {
                    JsonElement jsonBaseElement = JsonParser.parseString(sBaseValue);
                    JsonObject jsonBaseObject = jsonBaseElement.getAsJsonObject();
                    JsonElement jsonInnerElement = jsonBaseElement;
                    JsonObject jsonInnerObject = jsonBaseObject;
                    for (int i = 1; i < jsonArray.size()-1; i++) {
                        JsonElement jsonArrInner = jsonArray.get(i);
                        jsonInnerElement = jsonInnerObject.get(jsonArrInner.getAsString());
                    }
                    jsonInnerObject = jsonInnerElement.getAsJsonObject();
                    if (jsonInnerObject.has(jsonArray.get(jsonArray.size()-1).getAsString())) {
                        jsonInnerObject.remove(jsonArray.get(jsonArray.size() - 1).getAsString());
                        setCell(jsonArray.get(0).toString(), jsonBaseElement.toString());
                    } else {
                        bError = true;
                    }
                } catch (Exception e) {
                    bError = true;
                }
            } else {
                bError = true;
            }
            if (bError) {
                throw new CellBaseException("No such key");
            }
        }
    }
}