package DTOManager.impl.actionDTO;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ActionDTODeserializer implements JsonDeserializer<ActionDTO> {
    @Override
    public ActionDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            String actionType = jsonObject.get("actionType").getAsString();

            switch (actionType.toLowerCase()) {
                case "calculation":
                    return context.deserialize(json, ConditionActionDTO.class);
                case "condition":
                    return conditionConvert(jsonObject, json, context);
                case "decrease":
                    return context.deserialize(json, DecreaseDTO.class);
                case "increase":
                    return context.deserialize(json, IncreaseDTO.class);
                case "kill":
                    return context.deserialize(json, KillDTO.class);
                case "proximity":
                    return context.deserialize(json, ProximityDTO.class);
                case "replace":
                    return context.deserialize(json, ReplaceDTO.class);
                case "set":
                    return context.deserialize(json, SetDTO.class);
                default:
            }
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private ActionDTO conditionConvert(JsonObject jsonObject, JsonElement json, JsonDeserializationContext context) {
        if(jsonObject.get("logic") != null) {
            return context.deserialize(json, MultipleConditionDTO.class);
        }
        return context.deserialize(json, SingleConditionDTO.class);
    }
}
