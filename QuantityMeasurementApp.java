public class QuantityMeasurementApp {

    // 🔹 ENUM with conversion factors (base = feet)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double value) {
            return value / toFeet;
        }
    }

    // 🔹 Generic Quantity Class
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

        // 🔹 Convert to another unit (UC5)
        public Quantity convertTo(LengthUnit targetUnit) {
            double valueInFeet = unit.toFeet(value);
            double convertedValue = targetUnit.fromFeet(valueInFeet);
            return new Quantity(convertedValue, targetUnit);
        }

        // 🔹 Static conversion API (UC5)
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (source == null || target == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }

            double valueInFeet = source.toFeet(value);
            return target.fromFeet(valueInFeet);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double thisFeet = unit.toFeet(value);
            double otherFeet = other.unit.toFeet(other.value);

            return Double.compare(thisFeet, otherFeet) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 MAIN METHOD (UC5 DEMO)
    public static void main(String[] args) {

        // UC3/UC4 equality
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("Equal: " + q1.equals(q2));

        // UC5 conversions
        System.out.println("1 foot to inches: " +
                Quantity.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));

        System.out.println("3 yards to feet: " +
                Quantity.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("36 inches to yards: " +
                Quantity.convert(36.0, LengthUnit.INCH, LengthUnit.YARDS));

        System.out.println("1 cm to inches: " +
                Quantity.convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH));

        // Instance conversion
        Quantity length = new Quantity(2.0, LengthUnit.YARDS);
        System.out.println("2 yards to feet: " + length.convertTo(LengthUnit.FEET));
    }
}