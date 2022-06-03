package server;

public class SetCommand implements Command {
    protected CellBase base;
    protected String index;
    protected String value;

    public SetCommand(CellBase base, String index, String value) {
        this.base = base;
        this.index = index;
        this.value = value;
    }

    @Override
    public ResponseJSON execute() {
        try {
            //System.out.println("SetCommand index = " + index);
            //System.out.println("SetCommand value = " + value);
            base.setCell(index, value);
            return new ResponseJSON("OK");
        } catch (CellBaseException e) {
            return new ResponseJSON("ERROR", null, e.getMessage());
        }
    }
}
