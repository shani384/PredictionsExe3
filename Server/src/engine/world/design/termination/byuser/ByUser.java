package engine.world.design.termination.byuser;

public class ByUser {
    boolean isTerminationReason = false;

    boolean isPaused = false;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void setTerminationReason(boolean terminationReason) {
        isTerminationReason = terminationReason;
    }

    public boolean isTerminationReason() {
        return isTerminationReason;
    }
}
