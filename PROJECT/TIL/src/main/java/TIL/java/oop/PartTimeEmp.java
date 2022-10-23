package TIL.java.oop;

import java.util.List;

public class PartTimeEmp implements Person {

    private Baker baker;
    private Barista barista;
    private Pos pos;
    private int toPay = 0;

    @Override
    public void hiToCustomer() {
        System.out.println("안녕하세요 지원베이커리입니다. 최상의 서비스를 제공하겠습니다.");
    }

    public void askOrder(){
        System.out.println("안녕하세요! 무엇을 주문 하시겠어요?");
    }

    public void confirmOrder(List<String> breadList, List<String> drinkList){
        this.toPay = pos.calcToPay(breadList, drinkList);
        System.out.println("이런 이런 주문 맞으시죠 ?~~ 총 " + toPay + "원 입니다 ! ");
    }

    public boolean getpaid(int fromPay){
        if(toPay > fromPay){
            return true;
        }
        return false;
    }

    public void handleOrder(List<String> breadList, List<String> drinkList){
        baker.make();
        barista.make();
        System.out.println("진동벨 울리기");
    }

}
