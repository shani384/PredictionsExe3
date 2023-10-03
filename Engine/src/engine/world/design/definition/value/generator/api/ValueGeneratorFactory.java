package engine.world.design.definition.value.generator.api;

import engine.world.design.definition.value.generator.fixed.FixedValueGenerator;
import engine.world.design.definition.value.generator.random.impl.bool.RandomBooleanValueGenerator;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomFloatGenerator;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import engine.world.design.definition.value.generator.random.impl.string.RandomStringGenerator;

public interface ValueGeneratorFactory {

    static <T> ValueGenerator<T> createFixed(T value) {
        return new FixedValueGenerator<>(value);
    }

    static ValueGenerator<Boolean> createRandomBoolean() {
        return new RandomBooleanValueGenerator();
    }

    static ValueGenerator<Integer> createRandomInteger(Integer from, Integer to) {
        return new RandomIntegerGenerator(from, to);
    }

    static ValueGenerator<String> createRandomString(){
        return new RandomStringGenerator();
    }

    static ValueGenerator<Float> createRandomFloat(Float from, Float to) {
        return new RandomFloatGenerator(from, to);
    }

}
