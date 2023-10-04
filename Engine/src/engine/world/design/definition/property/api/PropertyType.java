package engine.world.design.definition.property.api;

public enum PropertyType {
    DECIMAL {
        public Integer convert(Object value) {
         try{
             double res = Double.parseDouble(value.toString());
             return (int) res;
         }catch (NumberFormatException e){
             throw new IllegalArgumentException("Can't convert this value to int");
         }
        }

        @Override
        public String toString() {
            return "decimal";
        }
    }, BOOLEAN {
        @Override
        public Boolean convert(Object value) {
            try{
                boolean res = Boolean.parseBoolean(value.toString());
                return res;
            }catch (NumberFormatException e){
                throw new IllegalArgumentException("Can't convert this value to boolean");
            }
        }

        @Override
        public String toString() {
            return "boolean";
        }
    }, FLOAT {
        @Override
        public Float convert(Object value) {
            try{
                float res = Float.parseFloat(value.toString());
                return res;
            }catch (NumberFormatException e){
                throw new IllegalArgumentException("Can't convert this value to float");
            }
        }
        @Override
        public String toString() {
            return "float";
        }
    }, STRING {
        @Override
        public String convert(Object value) {
            String res = value.toString();
            return res;
        }
        @Override
        public String toString() {
            return "string";
        }
    };

    public abstract <T> T convert(Object value);
}