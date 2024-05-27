import shape.ShapeCalculator;

public class Slave {
    private int computeSkill;

    public Slave(int input) {
        this.computeSkill = input;
    }

    public synchronized int  getComputeSkill() {
        return computeSkill;
    }

    public synchronized void setComputeSkill(int computeSkill) {
        this.computeSkill = computeSkill;
    }

    public synchronized void decreaseSkill() {
        if (computeSkill >= 10) {
            System.out.println("decreaseSkill");
            computeSkill= computeSkill - 10;
        }else{
            System.out.println("He already became idiot.");
        }
    }

    public synchronized void increaseSkill(){
        if(computeSkill <= 90){
            System.out.println("increaseSkill");
            computeSkill = computeSkill + 10;
        }else{
            System.out.println("it is his limit");
        }
    }

    public synchronized void whipping(){
        if(this.computeSkill <= 90){
            computeSkill +=10;
        }else{
            System.out.println("Slave is angry. it was his limit");
            computeSkill -= 5;
        }
    }

    public synchronized void drug(){
        if(this.computeSkill <= 80){
            computeSkill +=20;
        }else{
            System.out.println("Slave is slept. Drug was too powerful");
            computeSkill -= 40;
        }
    }

    public synchronized void praise(){
        if(this.computeSkill <= 95){
            System.out.println("Praise breeds willingness");
            computeSkill +=5;
        }
    }

    public synchronized void beating(){
        if(this.computeSkill <= 40){
            computeSkill +=10;
        }else{
            System.out.println("Slave is knocked out. terrible outcome it is hahahha");
            computeSkill = 0;
        }
    }


    public synchronized boolean tryToSolveProblem() {
        int successChance = (int) (Math.random() * 100);
        double slaveAns;

        ShapeCalculator calculator = new ShapeCalculator();

        if(successChance >= computeSkill){
            slaveAns = calculator.activateCalculator();
            return true;
        }else{
            System.out.println("Unnerved, unbalanced...");
            return false;
        }
    }
}
