package server;

public class GetCommand implements Command{
    protected CellBase base;
    protected String index;
    protected String result;

    public GetCommand(CellBase base, String index) {
        this.base = base;
        this.index = index;
    }

    @Override
    public ResponseJSON execute() {
        try {
            return new ResponseJSON("OK", base.getCell(index), null);
        } catch (CellBaseException e) {
            return new ResponseJSON("ERROR", null, e.getMessage());
        }
    }
}
