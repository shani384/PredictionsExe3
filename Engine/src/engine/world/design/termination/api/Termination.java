package engine.world.design.termination.api;

import DTOManager.impl.TerminationDTO;
import engine.world.design.termination.byuser.ByUser;
import engine.world.design.termination.second.Second;
import engine.world.design.termination.tick.api.Tick;

import java.time.Duration;

public interface Termination {

    // start the clock of the Termination object
    void startTerminationClock();

    // checks if ticks passed the termination object ticks or
    // the time in seconds from the start has passed
    Boolean isTerminated(boolean isUserStop);
    TerminationDTO createTerminationDTO();
    public void reduceWaitTime(Duration waitTime);

    //private Object terminateReason = null;
    ByUser getByUser();

    public void setCurrTick(Integer currTick);
    public Integer getCurrTick();
    public Tick getTicks();
    public Second getSecondsToPast();
    public long getCurrSecond();
    //Object getTerminateReason();
    public void setTicks(Tick ticks);
    public void setSecondsToPast(Second secondsToPast);
}
