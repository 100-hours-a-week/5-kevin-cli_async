# 7주차 async 과제 설명

# 깃허브:

[https://github.com/100-hours-a-week/5-kevin-cli_async](https://github.com/100-hours-a-week/5-kevin-cli_async)

# 1. 간단한 스레드 구현

<img src = "![weather](https://github.com/100-hours-a-week/5-kevin-cli_async/assets/63594534/1ca88077-636b-4341-98b7-edb648ae0e75)">

## Weather 클래스 추가

- 날씨 클래스를 추가하여, 노예의 계산 기술인 computeSkill에 영향이 가도록 설정하였습니다.
- `ScheduledExecutorService` 를 통해 초기 지연 10초, 이후부터 60초의 지연을 지니도록 설정하였습니다.
- Runnable 인터페이스를 통해 스레드로 구현하였습니다.
- Weather 클래스가 실행되면 확률에 따라 세 가지로 날씨가 변화합니다. 어떤 날씨느냐에 따라 computeSkill에 가해지는 변동이 달라집니다.
- 코드
    
    ```java
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
            } else if (0.4<= randomValue && randomValue < 0.9) {
                //쾌청하면 노예 능률 올라감
                System.out.println("################################################");
                System.out.println("Praise the sun! what a wonderful day it is!!");
                slave.increaseSkill();
            } else if (0.9 <= randomValue && randomValue <1) {
                //재앙 오면 노예 능력이 10으로 고정됨
                System.out.println("################################################");
                System.out.println("By the gods, forgive us.. this disaster is too awful..");
                System.out.println("Slave became Idiot");
                slave.setComputeSkill(10);
            }
            System.out.println(slave.getComputeSkill());
            System.out.println("################################################");
    
        }
    }
    
    ```
    

## SlaveWork 클래스 변경

- Weather 클래스 생성 이후, `ScheduledExecutorService` 를 추가하여 시간 지연을 커스텀하였습니다.

# 2. 스레드간 상호 작용할 수 있는 기능 구현

<img src="![misfortune](https://github.com/100-hours-a-week/5-kevin-cli_async/assets/63594534/a9419531-0c3a-48cb-a8c8-bdee5feba66a)">

## Misfortune 클래스 추가

- SlaveWork 클래스에서 노예에 대한 행동을 사용자의 입력을 통해 실행하는 파트가 끝나면 작동합니다.
- 사용자의 선택을 통해, Slave 클래스의 computeSkill에 영향을 줄 수 있습니다.
- 사용자가 -1을 입력하지 않으면 while문을 종료되고, 사용자가 숫자를 입력하지 않는다면 재입력하도록 유도합니다.
- 코드
    
    ```java
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
    
    ```
    

## SlaveWork 클래스 수정

- SlaveWork에서 실행되는 지점
    
    ```java
    System.out.println("Choose an action: (1) Whip (2) Drug (3) Praise (4) Beat (5)Exit");
                try {
                    String input = scanner.nextLine();
                    action = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("you should manage your slave properly");
                    continue;
                }
    
                switch (action) {
                    case 1:
                        slave.whipping();
                        break;
                    case 2:
                        slave.drug();
                        break;
                    case 3:
                        slave.praise();
                        break;
                    case 4:
                        slave.beating();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid action. No skill change.");
                        break;
                }
    
    //******************여기서 스레드 실행*******************
                misfortune_event.run();
    //*****************************************************
    ```
    
- SlaveWork 클래스에서 사용자가 노예에게 가할 행동을 선택한 이후, Misfortune 클래스를 실행합니다.

# 정리

- Weather 스레드와 Misfortune 스레드 그리고 main 스레드 모두 Slave 클래스의 computeSkill 변수에 관여합니다.
- Weather 스레드는 main 스레드 작동과 동시에 10초의 지연을 두고 실행됩니다. 이후 60초의 간격을 두고 자동으로 실행됩니다.
- Misfortune 스레드는 SlaveWork 클래스의 사용자 입력이 끝난 후, 작동합니다. 이때 선택을 통해 computeSkill에 영향이 가해집니다.
- 세 스레드 모두 computeSkill 변수를 조작하므로 race condition이 발생할 수 있습니다. 다만 Slave의 메소드를 활용해서 computeSkill을 조작하므로, Slave의 메소드 중 computeSkill에 관여하는 메소드들에 synchronized를 달아서 race condition을 방지하였습니다.

# 주요 클래스 및 메소드 설명

- Weathr 클래스
    - 코드
        
        ```java
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
                } else if (0.4<= randomValue && randomValue < 0.9) {
                    //쾌청하면 노예 능률 올라감
                    System.out.println("################################################");
                    System.out.println("Praise the sun! what a wonderful day it is!!");
                    slave.increaseSkill();
                } else if (0.9 <= randomValue && randomValue <1) {
                    //재앙 오면 노예 능력이 10으로 고정됨
                    System.out.println("################################################");
                    System.out.println("By the gods, forgive us.. this disaster is too awful..");
                    System.out.println("Slave became Idiot");
                    slave.setComputeSkill(10);
                }
                System.out.println(slave.getComputeSkill());
                System.out.println("################################################");
        
            }
        }
        
        ```
        
    - 주요 개념: Thread, Runnable
    - 역할 및 동작방식
        - Weatehr 클래스는 Ruannble 인터페이스를 구현하여, 실행 흐름과 별개로, 정기적인 시간마다 자신의 run()을 실행시키는 클래스입니다.
        - randomValue 변수를 통해 생성되는 무작위값으로 각각의 확률에 맞는 3개의 이벤트중 하나가 실행됩니다. 그리고 이를 통해 Slave 클래스의 increaseSkill() 혹은 decreaseSkill()이 작동하여 Slave의 computeSkill 변수를 수정합니다.
- Misfortune 클래스
    - 코드
        
        ```java
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
        
        ```
        
    - 주요 개념: Thread, Runnable
    - 역할 및 동작방식
        - Misfortune 클래스는 Runnable 클래스를 구현한 것입니다.
        - run()이 실행되면, 사용자의 입력을 받아 Slave의 computeSkill에 영향을 가합니다.
- SlaveWork 클래스
    - 코드
        
        ```java
        import java.util.Scanner;
        import java.util.concurrent.Executors;
        import java.util.concurrent.ScheduledExecutorService;
        import java.util.concurrent.TimeUnit;
        
        import static java.lang.Thread.sleep;
        
        public class SlaveWork {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
        
                Slave slave = new Slave(50); // Initial skill level set to 50
                Weather weather = new Weather(slave);
                Misfortune misfortune = new Misfortune(slave);
                Thread weather_event = new Thread(weather);
                Thread misfortune_event = new Thread(misfortune);
        
                int problemsSolved = 0;
                int totalProblems = 10;
        
                ScheduledExecutorService schedular = Executors.newScheduledThreadPool(2);
        
                //10초 마다 날씨 변경
                schedular.scheduleAtFixedRate(weather_event, 10, 60, TimeUnit.SECONDS);
        
                for (int i = 0; i < totalProblems; i++) {
                    int action = -1;
        
                    System.out.println("Choose an action: (1) Whip (2) Drug (3) Praise (4) Beat (5)Exit");
                    try {
                        String input = scanner.nextLine();
                        action = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("you should manage your slave properly");
                        continue;
                    }
        
                    switch (action) {
                        case 1:
                            slave.whipping();
                            break;
                        case 2:
                            slave.drug();
                            break;
                        case 3:
                            slave.praise();
                            break;
                        case 4:
                            slave.beating();
                            break;
                        case 5:
                            System.exit(0);
                        default:
                            System.out.println("Invalid action. No skill change.");
                            break;
                    }
        
                    misfortune_event.run();
        
                    if (slave.tryToSolveProblem()) {
                        System.out.println("slave worked quite good");
                        problemsSolved++;
                    }else{
                        System.out.println("it was his best. hahaha");
                    }
                    slave.decreaseSkill(); // Decrease skill after each attempt
                }
        
                System.out.println("Problems solved: " + problemsSolved + " out of " + totalProblems);
                if(problemsSolved > 7){
                    System.out.println("you are quite good master");
                }else{
                    System.out.println("next time, you will become even more greater master");
                }
            }
        
        }
        
        ```
        
    - 주요 개념: Thread, Runnable
    - 역할 및 동작방식
        - main 클래스입니다.
        - 스레드들의 실행 지점 위치를 지정하고, 사용자의 입력을 받아 Slave 클래스의 메소드들을 선택할 수 있는 입력창 기능이 있습니다.
        - 이후 10번의 선택 이후, 사용자가 얼마나 문제를 풀었는지 판가름합니다.
- Slave 클래스
    - 코드
        
        ```java
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
        
        ```
        
    - 주요 개념: synchronized
    - 역할 및 동작방식
        - Slave의 역할을 구현한 클래스입니다.
        - computeSkill을 통해 문제를 풀 수 있는 확률이 구현됩니다.
        - 모든 스레드들은 computeSkill을 조작하여 확률을 조정합니다.
        - 모든 스레드들은 Slave 클래스의 메소드들을 활용하여 computeSkill을 조정합니다.
        - decreaseSkill()
            - computeSkill을 10만큼 감소시킵니다. 10 초과면 감소시키지 않습니다.
        - increaseSkill()
            - computeSkill을 10만큼 증가시킵니다. 90초과면 증가시키지 않습니다.
        - whipping()
            - computeSkill을 10만큼 증가시킵니다. 90초과면 5만큼 감소시킵니다.
        - drug()
            - computeSkill을 20만큼 증가시킵니다. 80초과면 40만큼 감소시킵니다.
        - praise()
            - computeSkill을 5만큼 증가시킵니다. 95초과면 증가시키지 않습니다.
        - beating()
            - computeSkill을 10만큼 증가시킵니다. 40초과면 computeSkill을 0으로 설정합니다.
        - tryToSolveProblem()
            - computeSkill과 랜덤수를 비교하여 성공했는지 판가름하는 메소드입니다.
            - 성공할 경우, ShapeCalculator를 작동시켜서 정확한 계산 결과를 볼 수 있습니다. 이후 true를 반환합니다.
            - 실패하면 계산 결과를 보지 못하고 false를 리턴시킵니다.
    

**shape 디렉토리 관련 클래스들은 이전 cli-sync와 동일하므로 하단 링크 참조 부탁드립니다.**

[https://www.notion.so/goorm/383934dd4405400fa63834813505cc3d?pvs=4](https://www.notion.so/383934dd4405400fa63834813505cc3d?pvs=21)
