package engine.world.design.termination.second;

public class SecondImpl implements Second {

    private Integer seconds;
    private boolean isTerminateReason;
    public SecondImpl(Integer seconds) {
        this.seconds = seconds;
    }

    @Override
    public boolean isTerminateReason() {
        return isTerminateReason;
    }
    @Override
    public void setTerminateReason(boolean terminateReason) {
        isTerminateReason = terminateReason;
    }

    @Override
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    @Override
    public Integer getSeconds() {
        return seconds;
    }
}
