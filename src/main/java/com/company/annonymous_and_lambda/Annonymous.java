package com.company.annonymous_and_lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * 익명 클래스 : 이름이 없는 일회용 클래스. 정의와 생성을 동시에.
 * 익명 클래스는 단독으로 생성할 수 없고 클래스를 상속하거나 인터페이스를 구현해야만 생성할 수 있다.
 * Java 8에서 등장한 람다도 익명클래스의 일종.
 * UI 이벤트 처리, 스레드 객체 같은 일시적으로 한번만 사용되어야 하는 경우 사용.
 *
 * 익명 클래스 와 클래스 생성 시 어떤 차이가 있을까?
 * - 익명클래스 생성시 클래스 파일 2개 만들어짐.
 * - 그외 뭔가 잘 와닿지 않는다. -> 이부분 스터디에서 다루면 좋을듯.
 *
 * 결론 : 재사용 하지 않는 일회용 객체를 만들때 익명 클래스를 이용한다.
 */
public class Annonymous {

    public static void main(String[] args) throws Throwable {
        boolean logic = Boolean.TRUE;

        if (logic) {
//            ...
        }
        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public int getHttpStatusCode() {
                return 401;
            }

            @Override
            public String getErrorName() {
                return "UnAuthorizedError";
            }

            @Override
            public String getMessage() {
                return "sorry bye";
            }
        };

        throw (Throwable) errorResponse;

//        throw new UnAuthorizedError();
    }

}

class UnAuthorizedError extends Throwable implements ErrorResponse {

    @Override
    public int getHttpStatusCode() {
        return 401;
    }

    @Override
    public String getErrorName() {
        return "UnAuthorizedError";
    }

    @Override
    public String getMessage() {
        return "sorry bye";
    }
}

interface ErrorResponse {
    int getHttpStatusCode();
    String getErrorName();
    String getMessage();
}
