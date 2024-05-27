package shape;

public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        super("shape.Triangle");
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    public double getBase() {
        return base;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double calculateCircumference() {
        double hypotenuse = Math.sqrt((base * base) + (height * height));
        return base + height + hypotenuse;
    }

    @Override
    public String getDescription() {
        return "This is a triangle with base " + base + " and height " + height + ".";
    }

    //직각삼각형인지 확인하는 메소드
    public boolean isRightTriangle() {
        double hypotenuse = Math.sqrt((base * base) + (height * height));
        return Math.abs((hypotenuse * hypotenuse) - (base * base + height * height)) < 1e-9;
    }
}
