package shape;

public class Ellipse extends Circle {
    private double minorAxis;

    public Ellipse(double majorAxis, double minorAxis) {
        super(majorAxis);
        this.minorAxis = minorAxis;
        this.name = "shape.Ellipse";
    }

    @Override
    public double calculateArea() {
        return Math.PI * getRadius() * minorAxis;
    }

    public double getMinorAxis() {
        return minorAxis;
    }

    @Override
    public String getDescription() {
        return "This is an ellipse with major axis " + getRadius() + " and minor axis " + minorAxis + ".";
    }
}
