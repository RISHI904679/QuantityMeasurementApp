public class QuantityMeasurementApp {

    // 🔹 ENUM with all units
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084); // 1 cm = 0.0328084 feet

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // 🔹 Generic Quantity Class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            return Double.compare(thisInFeet, otherInFeet) == 0;
        }
    }

    // 🔹 MAIN METHOD (UC4 TESTS ONLY)
    public static void main(String[] args) {

        // Yard to Feet
        Quantity q1 = new Quantity(1.0, LengthUnit.YARDS);
        Quantity q2 = new Quantity(3.0, LengthUnit.FEET);
        System.out.println("Yard to Feet: " + q1.equals(q2));

        // Yard to Inch
        Quantity q3 = new Quantity(1.0, LengthUnit.YARDS);
        Quantity q4 = new Quantity(36.0, LengthUnit.INCH);
        System.out.println("Yard to Inch: " + q3.equals(q4));

        // CM to Inch
        Quantity q5 = new Quantity(1.0, LengthUnit.CENTIMETERS);
        Quantity q6 = new Quantity(0.393701, LengthUnit.INCH);
        System.out.println("CM to Inch: " + q5.equals(q6));

        // Same unit check
        Quantity q7 = new Quantity(2.0, LengthUnit.YARDS);
        Quantity q8 = new Quantity(2.0, LengthUnit.YARDS);
        System.out.println("Yard to Yard: " + q7.equals(q8));
    }
}