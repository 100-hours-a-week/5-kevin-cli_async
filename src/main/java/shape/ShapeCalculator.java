package shape;

import java.util.Scanner;

public class ShapeCalculator {
    private Scanner scanner;

    public ShapeCalculator() {
        scanner = new Scanner(System.in);
    }

    public double activateCalculator() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("Choose a shape to calculate the area (1: shape.Circle, 2: shape.Rectangle, 3: shape.Triangle, 4: shape.Ellipse, 0: Exit): ");

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Exiting Program");
                break;
            }

            Shape shape = null;
            switch (choice) {
                case 1:
                    shape = createCircle();
                    break;
                case 2:
                    shape = createRectangle();
                    break;
                case 3:
                    shape = createTriangle();
                    break;
                case 4:
                    shape = createEllipse();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (shape != null) {
                System.out.println("The area of the " + shape.getName() + " is: " + shape.calculateArea());
                System.out.println(shape.getDescription());
                return shape.calculateArea(); // Return the calculated area
            }
        }

        scanner.close();
        return -1;
    }

    private Circle createCircle() {
        double radius = inputMajor("circle");
        Circle circle = new Circle(radius);
        return circle;
    }

    private Rectangle createRectangle() {
        double width = inputMajor("rectangle");
        double height = inputMinor("rectangle");
        Rectangle rectangle = new Rectangle(width, height);
        return rectangle;
    }

    private Triangle createTriangle() {
        double base = inputMajor("triangle");
        double height = inputMinor("triangle");
        Triangle triangle = new Triangle(base, height);
        return triangle;
    }

    private Ellipse createEllipse() {
        double majorAxis = inputMajor("ellipse");
        double minorAxis = inputMinor("ellipse");
        Ellipse ellipse = new Ellipse(majorAxis, minorAxis);
        return ellipse;
    }

    private double inputMajor(String type) {
        switch (type) {
            case "circle":
                System.out.print("Enter the radius of the circle: ");
                break;
            case "rectangle":
                System.out.print("Enter the width of the rectangle: ");
                break;
            case "triangle":
                System.out.print("Enter the base of the triangle: ");
                break;
            case "ellipse":
                System.out.print("Enter the major axis of the ellipse: ");
                break;
            default:
                System.out.println("Missing type");
                return -1;
        }
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
        }
        return scanner.nextDouble();
    }

    private double inputMinor(String type) {
        switch (type) {
            case "rectangle":
                System.out.print("Enter the height of the rectangle: ");
                break;
            case "triangle":
                System.out.print("Enter the height of the triangle: ");
                break;
            case "ellipse":
                System.out.print("Enter the minor axis of the ellipse: ");
                break;
            default:
                System.out.println("Missing type");
                return -1;
        }
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
        }
        return scanner.nextDouble();
    }
}
