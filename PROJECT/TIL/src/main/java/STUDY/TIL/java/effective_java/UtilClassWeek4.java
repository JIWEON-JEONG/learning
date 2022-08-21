package STUDY.TIL.java.effective_java;

/**
 * 인스턴스를 생성하지 않아도 되는 클래스 일 경우 (ex)Util)
 *
 * 방법 1.
 * class 를 abstract 클래스로 생성한다.
 * 단점 : 만약 abstract 클래스를 상속받는 클래스가 있을경우, 그 클래스를 생성하게 되면 결국 UtilClass 생성됨.
 * (근데 이건 너무 억지 아닌가..) 어짜피 쓰레기 인스턴스라...
 * 굳이 만들거같지 않고 개발자 실수로 만들어질순 있을거같기도 하고 굳이 상황을 만들자면.
 *
 * 2. private 생성자를 만들자.
 * 의도치 않게 생성자를 호출 한 경우 (실수 등 따위로) 에러를 발생 시킬 수 있고,
 * private 생성자 이기 때문에 상속도 막을 수 있다.
 * 생성자를 제공하지만 쓸수 없기 때문에 직관에 어긋나는점은 주석으로 보완 하자!
 *
 * 결국 2가지의 차이점은 인스턴스 생성을 막을거냐 안막을거냐. 인데 현실적으로 전자를 택한다.
 * StringUtils 를 참고 해도 좋을듯. - abstract
 * Util Java 클래스는 private 으로 구현되어져 있음.
 *
 * AnnotatedElementUtils -> 오픈소스 기여 할 수 있는 기회 는 이미 날아감...
 */

public class UtilClassWeek4 {
    private static String APP_VERSION = "1.0.0";

    public static String getVersion() {
        return APP_VERSION;
    }

    public static Integer stringToInteger(String param) {
        return Integer.parseInt(param);
    }

    //유틸 클래스가 인스턴스를 만들지 못하게 막았습니다.
    private UtilClassWeek4() throws Exception {
        throw new Exception("인스턴스를 생성하지 않는 클래스 입니다.");
    }

}

class Main {
    public static void main(String[] args) {
//        UtilClassWeek4 util = new UtilClassWeek4();
//        ???
//        util.stringToInteger("param");
        UtilClassWeek4.getVersion();
        UtilClassWeek4.stringToInteger("param");
    }
}
