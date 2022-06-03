package server;

public class DeleteCommand implements Command{
    protected CellBase base;
    protected String index;

    public DeleteCommand(CellBase base, String index) {
        this.base = base;
        this.index = index;
    }

    @Override
    public ResponseJSON execute() {
        try {
            base.delCell(index);
            return new ResponseJSON("OK");
        } catch (CellBaseException e) {
            return new ResponseJSON("ERROR", null, e.getMessage());
        }
    }
}
