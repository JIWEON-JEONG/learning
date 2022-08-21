package STUDY.TIL.java.effective_java;

/**
 * 생성자 매개변수가 많은 경우에 빌더 사용을 고려해 볼 것.
 *
 * 1. 생성자 - 매개변수가 많을 경우 가독성이 떨어진다.
 * 무슨 변수에 어떤값이 들어가는지 파악하기 어렵다.
 * 2. 빈 생성자 - setter 를 통해 초기화
 * 여러번의 호출 , 자바빈이 안정적이지 않은 상태로 중간에 호출하여 사용될 가능성이 있다.
 * 불변 클래스를 만들지 못한다.
 * 3. 빌더 패턴 : 객체를 바로 만들지 않는다.
 * Builder 객체를 얻고 Setter 와 비슷한 빌더 객체가 제공하는 메소드를 사용하여
 * 부가적인 필드를 채워넣고 build 메소드를 호출하여 객체 생성.
 *
 * 빌더의 생성자나 메소드에서 유효성 확인을 할 수 있다. - 코드 참조
 *
 * @Lombok
 * @Builder.Default : 따로 매개변수 저장 안하면 , 0,null, false 같은걸로 초기화 되는데
 * 이 어노테이션을 활용하면 Default 값으로 초기화 할 수 있다.
 *
 * @Builder.Default int count = 0;
 *
 * @Singular : 컬렉션 타입 필드가 있을 때 add , clear 해주는 메서드 제공
 * ex) List<Places> places 있으면
 * .place("독서실")
 * .place("우리집")
 * .clearPlaces()
 * 등으로 활용 가능
 */
public class Week2 {
    //builder 예제 참조
}
