package engine.world.design.rule.activation.api;

public interface Activation {
    Boolean isActive(int tickNumber);
    void setTicks(Integer ticks);
    void setProbability(Double probability);

    int getTicks();
    double getProbability();
}
