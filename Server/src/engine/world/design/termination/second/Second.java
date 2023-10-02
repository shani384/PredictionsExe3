package engine.world.design.termination.second;

public interface Second {

    void setSeconds(Integer seconds);

    Integer getSeconds();
    boolean isTerminateReason();
    void setTerminateReason(boolean terminateReason);
}
