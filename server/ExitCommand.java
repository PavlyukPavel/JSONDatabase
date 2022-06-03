package server;

public class ExitCommand implements Command {
    @Override
    public ResponseJSON execute() {
        return new ResponseJSON("OK");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}