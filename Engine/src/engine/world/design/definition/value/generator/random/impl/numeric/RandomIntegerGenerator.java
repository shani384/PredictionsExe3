package engine.world.design.definition.value.generator.random.impl.numeric;

public class RandomIntegerGenerator extends AbstractNumericRandomGenerator<Integer> {

    public RandomIntegerGenerator(Integer from, Integer to) {
        super(from, to);
    }

    @Override
    public Integer generateValue() {
        return from + random.nextInt(to - from + 1);
    }
}