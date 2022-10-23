package TIL.java.effective_java;

/**
 * Item 16 : public class 에서는 public 필드가 아닌 접근자 메서드를 이용하라.
 *
 * 이유
 * 1. 캡슐화의 이점을 가지지 못한다.
 * 2. 불변성 보장하지 못한다.
 * 필드들을 private 으로 두고 getter 메소드 를 통해 접근 하는 것을 권장한다.
 *
 * >> public 클래스가 필드를 공개하면 이를 사용하는 클라이언트가 생겨날 것이므로 내부 표현 방식을 마음대로 바꿀수 없게 된다. ????
 * >> API 를 변경하지 않고는 표현 방식을 바꿀 수 없다.
 *
 * 내가 원하는건 10이야. 도현이가 5로 바꿨어. 출력했는데 5가 나왔다.
 */
public class Week8 {

    public int num;
    private int privateNum = 6;

    public int getPrivateNum() {
        return this.privateNum;
    }

    public Week8(int num) {
        this.num = num;
    }
}

class exec {
    public static void main(String[] args) {
        Week8 week8 = new Week8(10);

        int privateNum = week8.getPrivateNum();
        int num = week8.num;
        num = 5;
        System.out.println(week8.num);
    }
}



