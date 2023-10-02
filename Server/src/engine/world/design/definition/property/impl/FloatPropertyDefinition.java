package engine.world.design.definition.property.impl;

import engine.world.design.definition.property.api.AbstractPropertyDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.value.generator.api.ValueGenerator;

public class FloatPropertyDefinition extends AbstractPropertyDefinition<Float> {


    public FloatPropertyDefinition(String name,ValueGenerator<Float> valueGenerator) {
        super(name, PropertyType.FLOAT,valueGenerator);
    }
}
