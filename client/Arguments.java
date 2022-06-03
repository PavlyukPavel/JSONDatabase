package client;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Arguments {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-t", description = "type of the request")
    private String requestType;

    @Parameter(names = "-k", description = "index of the cell")
    private String indexCell;

    @Parameter(names = "-v", description = "value to save in the database:")
    private String value;

    @Parameter(names = "-in", description = "name of the file with JSON line")
    private String filename;

    public List<String> getParameters() {
        return parameters;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getIndexCell() {
        return indexCell;
    }

    public String getValue() {
        return value;
    }

    public String getFilename() {
        return filename;
    }
}