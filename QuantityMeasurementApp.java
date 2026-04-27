enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    // 🔹 Convert to base (feet)
    public double convertToBase(double value) {
        return value * toFeet;
    }

    // 🔹 Convert from base (feet)
    public double convertFromBase(double baseValue) {
        return baseValue / toFeet;
    }
}

public class QuantityMeasurementApp {

    // 🔹 Quantity class (SIMPLIFIED)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        // 🔹 Convert
        public Quantity convertTo(LengthUnit target) {
            double base = unit.convertToBase(value);
            double converted = target.convertFromBase(base);
            return new Quantity(converted, target);
        }

        // 🔹 UC7 Add (with target)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double thisBase = unit.convertToBase(value);
            double otherBase = other.unit.convertToBase(other.value);

            double sumBase = thisBase + otherBase;

            double result = targetUnit.convertFromBase(sumBase);

            return new Quantity(result, targetUnit);
        }

        // 🔹 equals
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(
                    unit.convertToBase(value),
                    other.unit.convertToBase(other.value)
            ) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 MAIN (UC8 DEMO)
    public static void main(String[] args) {

        // Conversion
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        System.out.println("1ft → inch: " + q1.convertTo(LengthUnit.INCH));

        // Addition
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("Add (feet): " + q1.add(q2, LengthUnit.FEET));

        System.out.println("Add (yards): " + q1.add(q2, LengthUnit.YARDS));

        // Equality
        Quantity q3 = new Quantity(36.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.YARDS);
        System.out.println("Equal: " + q3.equals(q4));
    }
}