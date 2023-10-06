package engine.impl;

import DTOManager.impl.MyThreadInfo;
import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.world.design.world.api.World;
import engine.world.design.reader.api.Reader;
import engine.world.design.reader.impl.ReaderImpl;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

// TODO: 10/08/2023 After deleting old World Change to World

public class EngineImpl implements Engine {
    private static final String JAXB_XML_PACKAGE_NAME = "schema.generated";
    private static final int INITIAL_THREAD_POOL = 3;
    private boolean isLoadedWorld = false;
    private final SimpleStringProperty fileName = new SimpleStringProperty();
    private final Reader myReader;
    private Map<String,World> myWorlds = new HashMap<>();
    private World myWorld; // TODO: 19/08/2023 static?
    private Integer countId = 1;
    private final Map<Integer, SimulationOutcome> pastSimulations;

    private final Map<Integer, RunSimulation> simulationThreads = new HashMap<>();
    private Map<String, Object> propertyNameToValueAsString;
    private ExecutorService threadExecutor = Executors.newFixedThreadPool(INITIAL_THREAD_POOL);


    public Map<String, World> getWorlds() {
        return myWorlds;
    }

    public EngineImpl() {
        myReader = new ReaderImpl();
        pastSimulations = new HashMap<>();
    }


    @Override
    public SimulationOutcomeDTO runNewSimulation(Map<String, Object> propertyNameToValueAsString) {
        SimulationOutcome simulation = myWorld.runSimulation(countId);
        RunSimulation runSimulation = new RunSimulation(simulation, myWorld, propertyNameToValueAsString);
        pastSimulations.put(countId, simulation);
        simulationThreads.put(countId, runSimulation);
        countId++;
//        runSimulation.run();
        threadExecutor.execute(runSimulation);
        return simulation.createSimulationOutcomeDTO();
    }


    @Override
    public WorldDTO getWorldDTO() {
         return myWorld.createWorldDTO();
    }
    @Override
    public Map<String,WorldDTO> getWorldsDTO(){
        Map<String,WorldDTO> worldsDTO = new HashMap<>();
        for (Map.Entry<String, World> entry : myWorlds.entrySet()) {
            String name = entry.getKey();
            WorldDTO worldDTO = entry.getValue().createWorldDTO();
            worldsDTO.put(name, worldDTO);
        }
        return worldsDTO;
    }

    @Override
    public MyThreadInfo getThreadPoolInfo(){
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) threadExecutor;
        int threadPoolSize = threadPool.getMaximumPoolSize();
        int queueSize;
        int workingThreads;
        int finishedThread;
        workingThreads =  threadPool.getActiveCount();
        finishedThread = (int) threadPool.getCompletedTaskCount();
        queueSize = threadPool.getQueue().size();
        return new MyThreadInfo(queueSize, workingThreads, finishedThread, threadPoolSize);
    }

    @Override
    public SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber) {
        return pastSimulations.get(wantedSimulationNumber).createSimulationOutcomeDTO();
    }

    @Override
    public void readWorldFromXml() {
        myReader.readWorldFromXml(fileName.get(), JAXB_XML_PACKAGE_NAME);
        myWorld = myReader.getWorld();
        isLoadedWorld = true;
                threadExecutor = Executors.newFixedThreadPool(myWorld.getNumOfThreads());
        //threadExecutor = Executors.newFixedThreadPool(1);
    }

    @Override
    public void readWorldWithServer(String fileName, StringBuilder fileContent){
        myReader.readWorldFromStringBuilder(fileContent, JAXB_XML_PACKAGE_NAME);
        myWorlds.put(fileName, myReader.getWorld());
        isLoadedWorld = true;
    }

    @Override
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        myReader.readWorldFromXml(XML_PATH, JAXB_XML_PACKAGE_NAME);
        myWorld = myReader.getWorld();
        isLoadedWorld = true;
    }

    public boolean getIsLoadedWorld() {
        return isLoadedWorld;
    }
//    @Override
//    public Map<Integer,SimulationOutcomeDTO> getPastSimulationDTO(int wantedSimulationNumber) {
//        return pastSimulations.get(wantedSimulationNumber).createSimulationOutcomeDTO();
//    }

    @Override
    public Map<Integer, SimulationOutcomeDTO> getPastSimulationMapDTO() {
        Map<Integer,SimulationOutcomeDTO> res = new HashMap<>();
        pastSimulations.forEach((id,pastSimulationOutCome) -> res.put(id, pastSimulationOutCome.createSimulationOutcomeDTO()));
        return res;
    }
    @Override
    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }
    @Override
    public Integer getCountId() {
        return countId;
    }
    @Override
    public Map<Integer, SimulationOutcome> getPastSimulations() {
        return pastSimulations;
    }
    @Override
    public World getMyWorld() {
        return myWorld;
    }
    @Override
    public Map<String, Object> getPropertyNameToValueAsString() {
        return propertyNameToValueAsString;
    }
    @Override
    public void setPropertyNameToValueAsString(Map<String, Object> propertyNameToValueAsString) {
        this.propertyNameToValueAsString = propertyNameToValueAsString;
    }
    @Override
    public void setCountId(Integer countId) {
        this.countId = countId;
    }
    @Override
    public void stopSimulationByID(int id) {
        simulationThreads.get(id).StopThread();
    }

    @Override
    public void resumeSimulationByID(int id){
        simulationThreads.get(id).resumeThread();
    }
    @Override
    public void pauseSimulationByID(int id){
        simulationThreads.get(id).pauseThread();
    }

    @Override
    public void setThreadPoolSize(int newThreadPoolSize) {
        this.threadExecutor = Executors.newFixedThreadPool(newThreadPoolSize);
    }
}
