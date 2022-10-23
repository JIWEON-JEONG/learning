package TIL.java.algo.stack;

import java.util.Stack;
import java.util.Vector;

public class StackEX {
    static StackEX stackEX = new StackEX();
    static Stack<Integer> stack = new Stack<>();
    static Vector<Integer> result;

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        result = new Vector<>();
//        for (int i = 0; i < n; i++) {
//            String input = scanner.nextLine();
//            stackEX.handleMethod(input);
//        }
//        for (Integer resultValue : result) {
//            System.out.println(resultValue);
//        }
//
//    }

    public void handleMethod(String input){
        String[] sliceInput = input.split(" ");
        switch (sliceInput[0]) {
            case "push":
                stackEX.push(Integer.parseInt(sliceInput[1]));
                break;
            case "pop":
                stackEX.pop();
                break;
            case "size":
                stackEX.size();
                break;
            case "empty":
                stackEX.empty();
                break;
            case "top":
                stackEX.top();
                break;
            default:
                break;
        }
    }

    public void push(int value){
        stack.push(value);
    }

    public int pop(){
        if(stack.isEmpty()){
            result.add(-1);
            return -1;
        }
        Integer pop = stack.pop();
        result.add(pop);
        return pop;
    }

    public void size(){
        int size = stack.size();
        result.add(size);
    }

    public void empty(){
        boolean empty = stack.isEmpty();
        if(empty == true){
            result.add(1);
        } else result.add(0);
    }

    public void top(){
        if(stack.empty()){
            result.add(-1);
            return;
        }
        int pop = stackEX.pop();
        stackEX.push(pop);
    }

}
