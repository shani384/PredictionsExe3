package DTOManager.impl;

import DTOManager.impl.actionDTO.ActionDTO;

import java.util.List;

public class RuleDTO {

    private final String name;
    private final Integer ticks;
    private final Double probability;
    private final List<ActionDTO> actions;

    public RuleDTO(String name, Integer ticks, Double probability, List<ActionDTO> actions) {
        this.name = name;
        this.ticks = ticks;
        this.probability = probability;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }
    public ActionDTO getActionByIndex(int index){
        for (int i=0; i<actions.size(); i++){
            if(i==index){
                return actions.get(i);
            }
        }
        return null;
    }
}
