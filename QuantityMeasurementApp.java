public class QuantityMeasurementApp {

    // 🔹 ENUM (same as UC5)
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

        // 🔹 Convert
        public Quantity convertTo(LengthUnit target) {
            double feet = unit.toFeet(value);
            double converted = target.fromFeet(feet);
            return new Quantity(converted, target);
        }

        // 🔹 STATIC CONVERT
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            double feet = source.toFeet(value);
            return target.fromFeet(feet);
        }

        // 🔥 UC6 — ADD METHOD
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Null quantity");
            }

            double thisFeet = this.unit.toFeet(this.value);
            double otherFeet = other.unit.toFeet(other.value);

            double sumFeet = thisFeet + otherFeet;

            double result = this.unit.fromFeet(sumFeet);

            return new Quantity(result, this.unit);
        }

        // 🔹 equals
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

    // 🔹 MAIN (UC6 DEMO)
    public static void main(String[] args) {

        // Same unit
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(2.0, LengthUnit.FEET);
        System.out.println("1ft + 2ft = " + q1.add(q2));

        // Cross unit (feet + inch)
        Quantity q3 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q4 = new Quantity(12.0, LengthUnit.INCH);
        System.out.println("1ft + 12in = " + q3.add(q4));

        // Reverse (inch + feet)
        Quantity q5 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q6 = new Quantity(1.0, LengthUnit.FEET);
        System.out.println("12in + 1ft = " + q5.add(q6));

        // Yard + feet
        Quantity q7 = new Quantity(1.0, LengthUnit.YARDS);
        Quantity q8 = new Quantity(3.0, LengthUnit.FEET);
        System.out.println("1yd + 3ft = " + q7.add(q8));

        // CM + inch
        Quantity q9 = new Quantity(2.54, LengthUnit.CENTIMETERS);
        Quantity q10 = new Quantity(1.0, LengthUnit.INCH);
        System.out.println("2.54cm + 1in = " + q9.add(q10));

        // Zero case
        Quantity q11 = new Quantity(5.0, LengthUnit.FEET);
        Quantity q12 = new Quantity(0.0, LengthUnit.INCH);
        System.out.println("5ft + 0in = " + q11.add(q12));
    }
}