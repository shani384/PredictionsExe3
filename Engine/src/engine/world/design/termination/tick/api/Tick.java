package engine.world.design.termination.tick.api;

public interface Tick {

    void setTicks(Integer ticks);
    Integer getTicks();
    boolean isTerminateReason();
    void setTerminateReason(boolean terminateReason);
}
