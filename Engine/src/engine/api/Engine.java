package engine.api;

import DTOManager.impl.MyThreadInfo;
import DTOManager.impl.SimulationOutcomeDTO;
import engine.SimulationOutcome;
import DTOManager.impl.WorldDTO;
import engine.world.design.world.api.World;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public interface Engine {


    SimulationOutcomeDTO runNewSimulation(Map<String, Object> propertyNameToValueAsString);
    void readWorldFromXml();

    void readWorldWithServer(StringBuilder fileContent);

    void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME);
    WorldDTO getWorldDTO();

    MyThreadInfo getThreadPoolInfo();

    SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber);

    Map<Integer, SimulationOutcomeDTO> getPastSimulationMapDTO();
    public boolean getIsLoadedWorld();
    public Integer getCountId();
    public Map<Integer, SimulationOutcome> getPastSimulations();
    public World getMyWorld();
    SimpleStringProperty fileNameProperty();
    public Map<String, Object> getPropertyNameToValueAsString();
    public void setPropertyNameToValueAsString(Map<String, Object> propertyNameToValueAsString);
    public void setCountId(Integer countId);
    public void stopSimulationByID(int id);
    public void resumeSimulationByID(int id);
    public void pauseSimulationByID(int id);
    // TODO: 10/08/2023 SimulationOutComeDTO getSimulationOutComeDTO();
}
