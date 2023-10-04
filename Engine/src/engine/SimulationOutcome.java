package engine;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.grid.api.Grid;
import engine.world.design.termination.api.Termination;

import java.util.HashMap;
import java.util.Map;

public class SimulationOutcome {
    private final String runDate;
    private final int id;
    private final Termination termination;
    private final EntityInstanceManager entityInstanceManager;
    private final EntityInstanceManagerDTO startPosition;
    private final ActiveEnvironment activeEnvironment;
    private final Map<Integer, EntityInstanceManagerDTO> dataAroundTicks = new HashMap<>();
    private final Grid grid;
    private boolean isStop = false;
    private boolean isResume = false;
    private boolean isPause = false;

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public void setResume(boolean resume) {
        isResume = resume;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public boolean isStop() {
        return isStop;
    }

    public boolean isResume() {
        return isResume;
    }

    public boolean isPause() {
        return isPause;
    }

    public SimulationOutcome(String runDate, int id, Termination termination, EntityInstanceManager entityInstanceDTOMap, EntityInstanceManagerDTO startPosition, ActiveEnvironment activeEnvironment, Grid grid) {
        this.runDate = runDate;
        this.id = id;
        this.termination = termination;
        this.entityInstanceManager = entityInstanceDTOMap;
        this.startPosition = startPosition;
        this.activeEnvironment = activeEnvironment;
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public SimulationOutcomeDTO createSimulationOutcomeDTO(){
        return new SimulationOutcomeDTO(runDate,id,termination.createTerminationDTO(), entityInstanceManager.createDTO(), isPause, isStop, dataAroundTicks);
    }

    public String getRunDate() {
        return runDate;
    }

    public int getId() {
        return id;
    }

    public Termination getTermination() {
        return termination;
    }

    public EntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }

    public EntityInstanceManagerDTO getStartPosition() {
        return startPosition;
    }

    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }

    public void addSimulationForTickDTO(int currTick, EntityInstanceManagerDTO simulationOutcomeDTO) {
        dataAroundTicks.put(currTick, simulationOutcomeDTO);
    }

    public Map<Integer, EntityInstanceManagerDTO> getDataAroundTicks(){
        return dataAroundTicks;
    }
//     private TerminationReason terminationReason;


    //private List<Entity> EntityData; //To Do!! change to EntityData class

}
