package engine.world.design.definition.property.impl;

import engine.world.design.definition.property.api.AbstractPropertyDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.value.generator.api.ValueGenerator;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {

    public BooleanPropertyDefinition(String name,ValueGenerator<Boolean> valueGenerator) {
        super(name, PropertyType.BOOLEAN,valueGenerator);
    }

}
