import java.util.Scanner;

public class Misfortune implements Runnable{
    private Slave slave;
    public Misfortune(Slave slave) {
        this.slave = slave;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int choice  = -1;

        System.out.println("################################################");
        do {
            System.out.printf("Test you fortune: (1) Left (2) Right : ");
            String input = sc.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice){
                case 1 :
                    System.out.println("Such a shame.");
                    slave.decreaseSkill();
                    break;
                case 2 :
                    System.out.println("Fortune favors the infamous.");
                    slave.increaseSkill();
                    break;
                default:
                    System.out.println("nothing would happen.");
                    break;
            }
        }while(choice == -1);
        System.out.println("Slave computeSkill : "+slave.getComputeSkill());
        System.out.println("################################################");
    }
}
