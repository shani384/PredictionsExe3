package DTOManager.impl;

import java.util.Map;

public class SimulationOutcomeDTO {

    private final String runDate;
    private final int id;
    private final TerminationDTO terminationDTO;
    private final EntityInstanceManagerDTO entityInstanceDTOS;

    Map<Integer, EntityInstanceManagerDTO> dataAroundTicks;

    private  boolean isPause;
    private  boolean isStop;

    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO, EntityInstanceManagerDTO dto, boolean isPause, boolean isStop, Map<Integer, EntityInstanceManagerDTO> dataAroundTicks) {
        this.isStop = isStop;
        this.isPause = isPause;
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
        entityInstanceDTOS = dto;
        this.dataAroundTicks = dataAroundTicks;
    }

    public boolean isPause() {
        return isPause;
    }
    public boolean isStop() {
        return isStop;
    }
    public Map<Integer, EntityInstanceManagerDTO> getDataAroundTicks() {
        return dataAroundTicks;
    }

    public EntityInstanceManagerDTO getEntityInstanceDTOS() {
        return entityInstanceDTOS;
    }

    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO, EntityInstanceManagerDTO entityInstanceDTOS) {
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
        this.entityInstanceDTOS = entityInstanceDTOS;
    }

    public String getRunDate() {
        return runDate;
    }

    public int getId() {
        return id;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
