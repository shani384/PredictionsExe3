package DTOManager.impl;

public class TerminationDTO {
    private Integer ticksTermination = null;
    private Integer secondsTermination = null;
    private final boolean isTicksTerminate;
    private final boolean isSecondsTerminate;
    private final Integer currTick;
    private final long currSecond;

    public TerminationDTO(Integer ticks, Integer secondsToPast, boolean isTicksTerminate, boolean isSecondsTerminate,Integer currTick,long currSecond) {
        this.ticksTermination = ticks;
        this.secondsTermination = secondsToPast;
        this.isTicksTerminate = isTicksTerminate;
        this.isSecondsTerminate = isSecondsTerminate;
        this.currTick = currTick;
        this.currSecond = currSecond;
    }

    public Integer getTicksTermination() {
        return ticksTermination;
    }

    public Integer getSecondsTermination() {
        return secondsTermination;
    }

    public Integer getCurrTick() {
        return currTick;
    }

    public long getCurrSecond() {
        return currSecond;
    }
    public boolean isTicksTerminate() {
        return isTicksTerminate;
    }

    public boolean isSecondsTerminate() {
        return isSecondsTerminate;
    }
}
