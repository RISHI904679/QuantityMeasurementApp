
// 🔹 LENGTH UNIT (same as UC8)
enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    public double convertToBase(double value) {
        return value * toFeet;
    }

    public double convertFromBase(double baseValue) {
        return baseValue / toFeet;
    }
}

// 🔥 NEW — WEIGHT UNIT
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKg;

    WeightUnit(double toKg) {
        this.toKg = toKg;
    }

    public double convertToBase(double value) {
        return value * toKg;
    }

    public double convertFromBase(double baseValue) {
        return baseValue / toKg;
    }
}

public class QuantityMeasurementApp {

    // 🔹 LENGTH CLASS
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.convertToBase(value);
            return new QuantityLength(target.convertFromBase(base), target);
        }

        public QuantityLength add(QuantityLength other, LengthUnit target) {
            double sum = unit.convertToBase(value) +
                    other.unit.convertToBase(other.value);

            return new QuantityLength(target.convertFromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(
                    unit.convertToBase(value),
                    other.unit.convertToBase(other.value)
            ) == 0;
        }

        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔥 NEW — WEIGHT CLASS
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.convertToBase(value);
            return new QuantityWeight(target.convertFromBase(base), target);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit target) {
            double sum = unit.convertToBase(value) +
                    other.unit.convertToBase(other.value);

            return new QuantityWeight(target.convertFromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityWeight)) return false;

            QuantityWeight other = (QuantityWeight) obj;

            return Double.compare(
                    unit.convertToBase(value),
                    other.unit.convertToBase(other.value)
            ) == 0;
        }

        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 MAIN (UC9 DEMO)
    public static void main(String[] args) {

        // 🔥 LENGTH CHECK
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println("Length Equal: " + l1.equals(l2));

        // 🔥 WEIGHT EQUALITY
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        System.out.println("Weight Equal: " + w1.equals(w2));

        // 🔥 WEIGHT CONVERSION
        System.out.println("1kg → pound: " +
                w1.convertTo(WeightUnit.POUND));

        // 🔥 WEIGHT ADDITION
        QuantityWeight w3 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w4 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Add weight: " +
                w3.add(w4, WeightUnit.KILOGRAM));
    }
}