package engine.world.design.definition.property.impl;

import engine.world.design.definition.property.api.AbstractPropertyDefinition;
import engine.world.design.definition.value.generator.api.ValueGenerator;
import engine.world.design.definition.property.api.PropertyType;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String> {

    public StringPropertyDefinition(String name,ValueGenerator<String> valueGenerator) {
        super(name, PropertyType.STRING,valueGenerator);
    }

}