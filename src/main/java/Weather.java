public class Weather implements Runnable{
    private Slave slave;
    public Weather(Slave slave) {
        this.slave = slave;
    }

    @Override
    public void run() {
        //100의 백분률
        double randomValue;
        randomValue = Math.random();

        if(0 <= randomValue && randomValue < 0.4){
            //비 오면 노예 능력 떨어짐
            System.out.println("################################################");
            System.out.println("The rain is coming. it's so gloomy day it is..");
            slave.decreaseSkill();
            System.out.println("################################################");
        } else if (0.4<= randomValue && randomValue < 0.9) {
            //쾌청하면 노예 능률 올라감
            System.out.println("################################################");
            System.out.println("Praise the sun! what a wonderful day it is!!");
            slave.increaseSkill();
            System.out.println("################################################");
        } else if (0.9 <= randomValue && randomValue <1) {
            //재앙 오면 노예 능력이 10으로 고정됨
            System.out.println("################################################");
            System.out.println("By the gods, forgive us.. this disaster is too awful..");
            System.out.println("Slave became Idiot");
            System.out.println("################################################");

            slave.setComputeSkill(10);
        }
    }
}
