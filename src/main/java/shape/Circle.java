package shape;

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super("shape.Circle");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double calculateCircumference() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String getDescription() {
        return "This is a circle with radius " + radius + ".";
    }

    public double calculateDiameter() {
        return 2 * radius;
    }
}
