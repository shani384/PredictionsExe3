package engine.world.design.rule.activation.impl;

import engine.world.design.definition.value.generator.random.impl.numeric.RandomFloatGenerator;
import engine.world.design.rule.activation.api.Activation;

public class ActivationImpl implements Activation {

    private Integer ticks = 1;
    private Double probability = 1d;
    private final RandomFloatGenerator randomFloatGenerator = new RandomFloatGenerator(0f, 1f);


    public ActivationImpl(){}
    public ActivationImpl(Integer ticks,Double probability) {
        if (ticks != null){
            this.ticks = ticks;
        }
        if (probability != null) {
            this.probability = probability;
        }
    }
    @Override
    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }
    @Override
    public void setProbability(Double probability) {
        this.probability = probability;
    }

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public double getProbability() {
        return probability;
    }

    @Override
    public Boolean isActive(int tickNumber) {
        return tickNumber % ticks == 0 && randomFloatGenerator.generateValue() < probability;
    }
}
