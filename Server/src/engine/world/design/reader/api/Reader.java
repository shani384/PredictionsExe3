package engine.world.design.reader.api;

import engine.world.design.world.api.World;
import schema.generated.*;

import java.util.Map;

public interface Reader {

    void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME);
    World getWorld();

    void readEnvironmentPropertiesFromUser(Map<String, Object> propertyNameToValueAsString);
}
