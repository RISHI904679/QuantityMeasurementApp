public class QuantityMeasurementApp {

    // 🔹 ENUM
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

    // 🔹 Quantity Class
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

        // 🔹 UC5 convert
        public Quantity convertTo(LengthUnit target) {
            double feet = unit.toFeet(value);
            double converted = target.fromFeet(feet);
            return new Quantity(converted, target);
        }

        // 🔹 UC6 add (default → first unit)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // 🔥 UC7 add with target unit
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double thisFeet = this.unit.toFeet(this.value);
            double otherFeet = other.unit.toFeet(other.value);

            double sumFeet = thisFeet + otherFeet;

            double result = targetUnit.fromFeet(sumFeet);

            return new Quantity(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(
                    this.unit.toFeet(this.value),
                    other.unit.toFeet(other.value)
            ) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 MAIN (UC7 DEMO)
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        // Different target units
        System.out.println("Feet: " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Inches: " + q1.add(q2, LengthUnit.INCH));
        System.out.println("Yards: " + q1.add(q2, LengthUnit.YARDS));

        // Yard + feet
        Quantity q3 = new Quantity(1.0, LengthUnit.YARDS);
        Quantity q4 = new Quantity(3.0, LengthUnit.FEET);
        System.out.println("Yard result: " + q3.add(q4, LengthUnit.YARDS));

        // CM + inch
        Quantity q5 = new Quantity(2.54, LengthUnit.CENTIMETERS);
        Quantity q6 = new Quantity(1.0, LengthUnit.INCH);
        System.out.println("CM result: " + q5.add(q6, LengthUnit.CENTIMETERS));
    }
}