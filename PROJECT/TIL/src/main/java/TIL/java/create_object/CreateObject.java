package TIL.java.create_object;


public class CreateObject {
    public static void main(String[] args) {
        System.out.println("=================");
        new Child();
        System.out.println("=================");
    }

}

class Parent{
    static {
        System.out.println("Parent static block");
    }

    public Parent() {
        System.out.println("Parent Constructor");
    }

    public void print(){
        System.out.println("Parent Method");
    }

    {
        System.out.println("Parent instance block");
    }
}

class Child extends Parent{

    static {
        System.out.println("Child static block");
    }

    public Child() {
        System.out.println("Child Constructor");
    }

    public void print(){
        System.out.println("Child Method");
    }

    {
        System.out.println("Child instance block");
    }
    
}
