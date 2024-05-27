package shape;

public class Shape {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double calculateArea() {
        return 0.0;
    }

    public double calculateCircumference(){
        return 0.0;
    }
    public String getDescription() {
        return "This is a shape.";
    }
}
