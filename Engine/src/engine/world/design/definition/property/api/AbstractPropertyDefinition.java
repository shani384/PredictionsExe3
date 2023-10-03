package engine.world.design.definition.property.api;

import DTOManager.impl.PropertyDefinitionDTO;
import engine.world.design.definition.value.generator.api.ValueGenerator;
import engine.world.design.definition.value.generator.fixed.FixedValueGenerator;
import engine.world.design.definition.value.generator.random.impl.numeric.AbstractNumericRandomGenerator;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomFloatGenerator;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomIntegerGenerator;

public abstract class AbstractPropertyDefinition<T>  implements PropertyDefinition {

    private final String name;
    private final PropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;

    public AbstractPropertyDefinition(String name, PropertyType propertyType,ValueGenerator<T> valueGenerator) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
    }
    @Override
    public PropertyDefinitionDTO createPropertyDefinitionDTO() {
        Boolean isRandomInitializer;
        Float from = null;
        Float to = null;
        Object value = null;

        if (valueGenerator instanceof FixedValueGenerator) {
            isRandomInitializer = Boolean.FALSE;
            value = valueGenerator.generateValue();
        } else {
            isRandomInitializer = Boolean.TRUE;
            if (valueGenerator instanceof RandomFloatGenerator) {
                from = ((RandomFloatGenerator) valueGenerator).getFrom();
                to = ((RandomFloatGenerator) valueGenerator).getTo();
            }else if (valueGenerator instanceof RandomIntegerGenerator) {
                from = (float)((RandomIntegerGenerator) valueGenerator).getFrom();
                to = (float)((RandomIntegerGenerator) valueGenerator).getTo();
            }

        }
        return new PropertyDefinitionDTO(name, propertyType.name(), isRandomInitializer, from, to,value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return propertyType;
    }

    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }

}
