package engine.world.design.definition.property.impl;

import engine.world.design.definition.property.api.AbstractPropertyDefinition;
import engine.world.design.definition.value.generator.api.ValueGenerator;
import engine.world.design.definition.property.api.PropertyType;


public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {

        public IntegerPropertyDefinition(String name,ValueGenerator<Integer> valueGenerator) {
            super(name, PropertyType.DECIMAL,valueGenerator);
        }

}
