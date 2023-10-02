package engine.world.design.termination.tick.impl;

import engine.world.design.termination.tick.api.Tick;

public class TickImpl implements Tick {

    private Integer ticks;
    private boolean isTerminateReason;
    public TickImpl(Integer ticks) {
        this.ticks = ticks;
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
    public Integer getTicks() {
        return ticks;
    }

    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }
}
