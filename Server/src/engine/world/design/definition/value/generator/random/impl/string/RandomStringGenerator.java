package engine.world.design.definition.value.generator.random.impl.string;

import engine.world.design.definition.value.generator.random.api.AbstractRandomValueGenerator;

public class RandomStringGenerator extends AbstractRandomValueGenerator <String> {
    @Override
    public String generateValue() {
        int len = random.nextInt(50 - 1 + 1) + 1;
        StringBuilder res = new StringBuilder(len);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,_-.() ";
        for (int i = 0; i < len; i++) {
            int ind = random.nextInt(chars.length());
            char randomChar = chars.charAt(ind);
            res.append(randomChar);
        }
        return res.toString();
    }
}
