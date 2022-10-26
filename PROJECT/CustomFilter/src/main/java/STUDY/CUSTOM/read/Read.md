# Filter 와 Interceptor
>대부분 많은 웹 서비스는 로그인을 해야 서비스를 이용할 수 있다.
> 
>로그인을 하지 않은 사용자는 접근할 수 있는 페이지가 제한적이며 로그인이 필요한 페이지 접근이 허용되서는 안된다. 하지만, 그렇다고 로그인이 필요한 모든 컨트롤러 로직에 로그인 여부를 확인하는 코드를 작성하는 것은 너무 비효율적이다. 수정에도 취약하다.
> 
> 웹과 관련된 공통 관심사를 처리할 때는 HTTP의 헤더나 URL 정보가 필요한데 서블릿 필터나, 스프링 인터셉터는 HttpServletRequest를 제공하기 때문에 해당 기능을 사용하여 처리해보자.

## Filter
>필터(Filter)는 J2EE 표준 스펙 기능으로 디스패처 서블릿(Dispatcher Servlet)에 요청이 전달되기 전/후에 url 패턴에 맞는 모든 요청에 대해 부가작업을 처리할 수 있는 기능을 제공한다.
> 
> 다음 필터로 호출해주는 chain.doFilter() 전/후에  처리 코드를 넣어줌으로써 각각 전처리 후처리 모두가 가능.
> 
> 즉, 스프링 컨테이너가 아닌 톰캣과 같은 웹 컨테이너에 의해 관리가 되는 것이고(스프링 빈으로 등록은 된다), 디스패처 서블릿 전/후에 처리하는 것이다. 이러한 과정을 그림으로 표현하면 다음과 같다.

>init, destroy 메서드는 default 메서드 이기에 따로 구현하지 않아도 된다.

<img src = "/Users/BestFriend/Desktop/PROJECT/CustomFilter/src/main/java/STUDY/CUSTOM/read/images/filter.png"></img>

## Interceptor
> Spring이 제공하는 기술로써, 디스패처 서블릿(Dispatcher Servlet)이 컨트롤러를 호출하기 전과 후에 요청과 응답을 참조하거나 가공할 수 있는 기능을 제공
> 
> 즉, 웹 컨테이너에서 동작하는 필터와 달리 인터셉터는 스프링 컨텍스트에서 동작을 한다.

<img src = "/Users/BestFriend/Desktop/PROJECT/CustomFilter/src/main/java/STUDY/CUSTOM/read/images/interceptor.png"></img>

>디스패처 서블릿은 핸들러 매핑을 통해 적절한 컨트롤러를 찾도록 요청하는데, 그 결과로 실행 체인(HandlerExecutionChain)을 돌려준다. 
> 
>그래서 이 실행 체인은 1개 이상의 인터셉터가 등록되어 있다면 순차적으로 인터셉터들을 거쳐 컨트롤러가 실행되도록 하고, 인터셉터가 없다면 바로 컨트롤러를 실행한다.

<img src = "/Users/BestFriend/Desktop/PROJECT/CustomFilter/src/main/java/STUDY/CUSTOM/read/images/dispatcher.png"></img>

<img src = "/Users/BestFriend/Desktop/PROJECT/CustomFilter/src/main/java/STUDY/CUSTOM/read/images/interceptor_exception.png"></img>
>예외가 발생하면 postHandler()같은 경우 호출되지 않기 때문에 예외 처리가 필요하다면 afterCompletion()을 사용해야 한다.
> 다행히 afterCompletion은 Exception ex를 매개변수로 받고 있으며 Nullable하기에 notNull인 경우 해당 처리를 수행하면 된다.



### 구현
>인터셉터를 추가하기 위해서는 org.springframework.web.servlet의 HandlerInterceptor 인터페이스를 구현(implements)해야한다.
- preHandle 메소드
>preHandle 메소드는 컨트롤러가 호출되기 전에 실행된다. 
> 
>그렇기 때문에 컨트롤러 이전에 처리해야 하는 전처리 작업이나 요청 정보를 가공하거나 추가하는 경우에 사용할 수 있다.
>
>preHandle의 3번째 파라미터인 handler 파라미터는 핸들러 매핑이 찾아준 컨트롤러 빈에 매핑되는 HandlerMethod라는 새로운 타입의 객체로써, @RequestMapping이 붙은 메소드의 정보를 추상화한 객체이다.
>또한 preHandle의 반환 타입은 boolean인데 반환값이 true이면 다음 단계로 진행이 되지만, false라면 작업을 중단하여 이후의 작업(다음 인터셉터 또는 컨트롤러)은 진행되지 않는다.

> Object handler 매개변수에는 핸들러 정보로 HandlerMethod가 넘어온다. 
- postHandle 메소드
>postHandle 메소드는 컨트롤러를 호출된 후에 실행된다. 
> 
>그렇기 때문에 컨트롤러 이후에 처리해야 하는 후처리 작업이 있을 때 사용할 수 있다. 이 메소드에는 컨트롤러가 반환하는 ModelAndView 타입의 정보가 제공되는데, 최근에는 Json 형태로 데이터를 제공하는 RestAPI 기반의 컨트롤러(@RestController)를 만들면서 자주 사용되지는 않는다.
- afterCompletion 메소드
>afterCompletion 메소드는 이름 그대로 모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후에 실행된다. 요청 처리 중에 사용한 리소스를 반환할 때 사용하기에 적합하다.

## Interceptor 와 AOP 
>인터셉터 대신에 컨트롤러들에 적용할 부가기능을 어드바이스로 만들어 AOP(Aspect Oriented Programming, 관점 지향 프로그래밍)를 적용할 수도 있다. 
> 
>하지만 컨트롤러들은 타입이 일정하지 않고 호출 패턴도 정해져 있지 않기 때문에 AOP를 적용하려면 번거로운 부가 작업들이 생기게 된다.
> 
> 즉, 컨트롤러의 호출 과정에 적용되는 부가기능들은 인터셉터를 사용하는 편이 낫다

## Filter 와 Interceptor 차이점
### Request/Response 객체 조작 가능 여부
>doFilter 메소드로 다음 필터에게 넘겨줄때,
>우리가 원하는 request, response 객체를 만들어 넣어 줄 수 있다. 
> 
~~~
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    // custom한 request와 response를 넣어줄 수 있음
    chain.doFilter(request, response);       
}
~~~

>꽤 자주 있는 요구 사항이다. 
> HttpServletRequest의 body(ServletInputStream의 내용)를 로깅하는 것을 예로 들 수 있을 것 같다. 
> HttpServletRequest는 body의 내용을 한 번만 읽을 수 있다. Rest API Application을 작성할 때, 흔히 json 형식으로 요청을 받는다. @Controller(Handler)에 요청이 들어오면서 body를 한 번 읽게 된다. 
> 때문에 Filter나 Interceptor에서는 body를 읽을 수 없다. IOException이 발생한다. 
> body를 로깅하기 위해서는 HttpServletRequest를 감싸서 여러 번 inputStream을 열 수 있도록 커스터마이징 된 ServletRequest를 쓸 수밖에 없다.



>하지만 인터셉터는 처리 과정이 필터와 다르다.
> 
>디스패처 서블릿이 여러 인터셉터 목록을 가지고 있고, for문으로 순차적으로 실행시킨다. 
> 그리고 true를 반환하면 다음 인터셉터가 실행되거나 컨트롤러로 요청이 전달되며, false가 반환되면 요청이 중단된다. 
>

### Filter 용도 및 예시
- 공통된 보안 및 인증/인가 관련 작업
> 인터셉터보다 앞단에서 동작하므로 전역적으로 해야하는 보안 검사(XSS 방어 등)를 하여 올바른 요청이 아닐 경우 차단.
>
> 스프링 컨테이너 까지 오기 전에 차단 해버려 안정성 이점.
- 모든 요청에 대한 로깅 또는 감사
- 이미지/데이터 압축 및 문자열 인코딩
> 웹 애플리케이션에 전반적으로 사용되는 기능을 구현하기에 적당
- Spring 과 분리되어야 하는 기능
>필터에서는 기본적으로 스프링과 무관하게 전역적으로 처리해야 하는 작업들을 처리.
> 
> 추가 : 예외 처리 부분 Interceptor 보다 어려울 듯.
> 
> 예시 : Filter는 Web Application에 등록한다(참고로, Interceptor는 Spring Context에 등록한다). 
> 예를들어 tomcat의 경우 /WEB-INF/web.xml에 사용할 Filter를 등록하여 사용한다.
> 
>따라서, Filter에서 예외가 발생하면 Web Application에서 처리해야한다. 
> tomcat을 사용한다면 <error-page>를 잘 선언하든가 아니면 Filter에서 예외를 잡아 request.getRequestDispatcher(String)으로 마치 핑퐁 하듯이 예외 처리를 미뤄야 한다.

### Interceptor 용도 및 예시
- 세부적인 보안 및 인증/인가 공통 작업
- API 호출에 대한 로깅 또는 감사
- Controller로 넘겨주는 정보(데이터)의 가공
> 인터셉터에서는 클라이언트의 요청과 관련되어 전역적으로 처리해야 하는 작업들을 처리.
> 
> HandlerInterceptor에서 Request에 대해 원하는 작업을 수행한 후 Request 객체를 Controller로 전달합니다.

# 서블릿 필터(Servlet Filter)가 스프링 빈으로 등록 가능한 이유
## DelegatingFilterProxy : 서블릿 컨테이너에서 관리되는 프록시용 필터
>우리가 만든 필터는 스프링 컨테이너에 빈으로 먼저 등록된 후에 DelegatingFilterProxy에 감싸져 서블릿 컨테이너로 등록이 된다. 
> 
>이러한 이유로 우리가 개발한 Filter도 스프링 빈으로 등록되며, 스프링 컨테이너에서 관리되기 때문에 빈 등록뿐만 아니라 빈의 주입까지 가능하다.

## Spring Boot
>위의 DelegatingFilterProxy를 등록하는 과정은 Spring이기 때문에 필요한 것이고, SpringBoot라면 DelegatingFilterProxy조차 필요가 없다. 
> 
>왜냐하면 SpringBoot가 내장 웹서버를 지원하면서 톰캣과 같은 서블릿 컨테이너까지 SpringBoot가 제어가능하기 때문.
> 
> SpringBoot가 서블릿 필터의 구현체 빈을 찾으면 DelegatingFilterProxy 없이 바로 필터 체인(Filter Chain)에 필터를 등록하여 사용.

___
# OncePerRequestFilter 란
>요청 당 한번의 실행을 보장.

## 예시
>예를들어, 어느 필터에서 헤더를 확인 한 후 특정 url로 포워딩 시킨다고 가정하자.
> 
>이때 예외가 발생하지 않았다면, url로 포워딩 시키는 것 자체가 서블릿 실행 중 요청이 온 것이다.
> 
> OncePerRequestFilter를 사용하지 않았다면 앞서 거친 필터들을 또 한번 거칠 것이고, 쓸데없는 자원만 낭비하는 셈이다.
>
>결국 동일한 request안에서 한번만 필터링을 할 수 있게 해주는 것이 OncePerRequestFilter의 역할이고 보통 인증 또는 인가와 같이 한번만 거쳐도 되는 로직에서 사용한다.
>
>인증 또는 인가를 거치고나서 특정 url로 포워딩하면, 요청이 들어왔으니 인증 및 인가필터를 다시 실행시켜야 하지만, OncePerRequestFilter를 사용함으로써 인증이나 인가를 한번만 거치고 다음 로직을 진행할 수 있도록 한다.


## GenericFilterBean
>GenericFilterBean은 기존 Filter에서 얻어올 수 없는 정보였던 Spring의 설정 정보를 가져올 수 있게 확장된 추상 클래스.
> 
> spring은 filter에서 spring config 설정 정보를 쉽게 처리하기 위한 GenericFilterBean을 제공한다.
Filter를 구현한 것과 동일하고 getFilterConfig()나 getEnvironment()를 제공해주는 정도이다.

___
# 인터셉터가 아닌 필터로 구현하는 이유는?

>토큰인증과 권한을 filter에서 모두 처리하면서 Error가 인증관련 문제로 나타나는 문제가 있었다. 
> 
>그리고 이 부분은 결과적으로 Filter에서는 인증을, Interceptor에서는 권한체크를 담당하도록 구조를 변경



<img src = "/Users/BestFriend/Desktop/PROJECT/CustomFilter/src/main/java/STUDY/CUSTOM/read/images/spring_request.png"></img>
