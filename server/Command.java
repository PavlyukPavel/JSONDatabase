package server;

public interface Command {
    ResponseJSON execute();
    default boolean isExit() {
        return false;
    };
}