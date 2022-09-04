package STUDY.TIL.spring.core;

public class DI {

    Application game;

    public DI(Application game) {
        this.game = game;
    }

    static class Computer{
        public void play(Application game) {
            System.out.println("play" + " : " + game);
        }
    }

    public static void main(String[] args) {
        Computer computer = new Computer();
        LOL lol = new LOL();
        FIFA fifa = new FIFA();
        computer.play(lol);
        computer.play(fifa);
    }
}

interface Application{

}

class LOL implements Application{
}

class FIFA implements Application{}