package shape;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("shape.Rectangle");
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculateCircumference() {
        return 2 * (width + height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String getDescription() {
        return "This is a rectangle with width " + width + " and height " + height + ".";
    }

    //정사각형인지 확인하는 메소드
    public boolean isSquare() {
        return width == height;
    }
}
