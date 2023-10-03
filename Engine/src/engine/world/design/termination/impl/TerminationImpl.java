package engine.world.design.termination.impl;

import DTOManager.impl.TerminationDTO;
import engine.world.design.termination.api.Termination;
import engine.world.design.termination.byuser.ByUser;
import engine.world.design.termination.second.Second;
import engine.world.design.termination.tick.api.Tick;

import java.time.Duration;
import java.time.Instant;

public class TerminationImpl implements Termination {

    private Tick ticks = null;
    private Second secondsToPast = null;
    private ByUser byUser;
    private Instant startTime;
    private long currSecond = 0;
    private Integer currTick;
    //private Object terminateReason = null;
    @Override
    public ByUser getByUser() {
        return byUser;
    }

    public TerminationImpl() {
        currTick = 0;
        startTime = Instant.ofEpochSecond(0);
        byUser = new ByUser();
    }
    @Override
    public void setCurrTick(Integer currTick) {
        this.currTick = currTick;
    }
    @Override
    public Integer getCurrTick() {
        return currTick;
    }

    @Override
    public void setTicks(Tick ticks) {
        this.ticks = ticks;
    }

    public Tick getTicks() {
        return ticks;
    }

    public Second getSecondsToPast() {
        return secondsToPast;
    }

    public long getCurrSecond() {
        return currSecond;
    }

    @Override
    public void setSecondsToPast(Second secondsToPast) {
        this.secondsToPast = secondsToPast;
    }
    @Override
    public TerminationDTO createTerminationDTO(){
        boolean isTicksTerminate = false;
        boolean isSecondsTerminate = false;
        Integer numOfTicks = null;
        Integer numOfSeconds = null;
        if (ticks != null) {
            isTicksTerminate = ticks.isTerminateReason();
            numOfTicks = ticks.getTicks();
        }
        if(secondsToPast != null) {
            isSecondsTerminate = secondsToPast.isTerminateReason();
            numOfSeconds = secondsToPast.getSeconds();
        }
        if (!byUser.isTerminationReason() && !byUser.isPaused()){
            currSecond = Instant.now().getEpochSecond() - startTime.getEpochSecond();
        }
        return new TerminationDTO(numOfTicks, numOfSeconds, isTicksTerminate, isSecondsTerminate,currTick,currSecond);
    }
    @Override
    public Boolean isTerminated(boolean isUserStop) {
        boolean isTerminate = false;
        if(secondsToPast != null){
            Instant currentTime = Instant.now();
            Duration elapsed = Duration.between(startTime, currentTime);
            if (elapsed.getSeconds() >= secondsToPast.getSeconds()) {
                isTerminate = true;
                secondsToPast.setTerminateReason(true);
                //terminateReason = secondsToPast;
            }
        }
        if (ticks != null) {
            if(currTick >= ticks.getTicks()) {
                isTerminate = true;
                ticks.setTerminateReason(true);
                //terminateReason = ticks;
            }
        }
        if (isUserStop){
            isTerminate = true;
            byUser.setTerminationReason(true);
        }
        return  isTerminate;
    }

//    public Object getTerminateReason() {
//        return terminateReason;
//    }

    public Instant getStartTime() {
        return startTime;
    }

    @Override
    public void reduceWaitTime(Duration waitTime) {
        this.startTime = startTime.plus(waitTime);
    }

    @Override
    public void startTerminationClock() {
        if (ticks != null) {
            ticks.setTerminateReason(false);
        }
        if (secondsToPast != null){
            secondsToPast.setTerminateReason(false);
        }
        startTime = Instant.now();
    }
}
