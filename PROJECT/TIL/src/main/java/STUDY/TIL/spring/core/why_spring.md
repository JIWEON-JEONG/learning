#Spring
##Why Spring
1. Spring 은 어디에서나 쓰이고 있다.
>Spring also has contributions from all the big names in tech, including Alibaba, Amazon, Google, Microsoft, and more.

2. Spring 은 flexible 하다.
 - **" At its core, Spring Framework’s Inversion of Control (IoC) and Dependency Injection (DI) features provide the foundation for a wide-ranging set of features and functionality."**
> IOC , DI 가 기능과 함수들을 유연하게 넓은 범위에서 제공 할 수 있게 하는 근간이다. 
> 

3. Spring 은 생산적이다.
 - **Spring Boot transforms how you approach Java programming tasks, radically streamlining your experience.**
> 자바 프로그래밍 에서 접근 하는 방식을 변환하여 우리가 편하게 쓸 수 있도록 간소화 시켜 놓았다.
> 
> Spring Boot 같은 경우, web server 를 내장 시켜 마이크로서비스 구현 가능해졌다. 

4. Spring 은 빠르다.
 - You can even start a new Spring project in seconds, with the Spring Initializr at start.spring.io.
>훨씬 적은 수고로 쉽고 간편하게 애플리케이션을 구축할 수 있도록 도와준다.
> 

5. Spring 은 안전하다.
>Spring Security를 사용하면 업계 표준 보안 체계와 쉽게 통합하고 기본적으로 안전한 신뢰할 수 있는 솔루션을 제공할 수 있다.

6. Spring is Supportive
>필요한 지원과 리소스를 찾을 수 있다.
> 

##What can spring do?
- MicroServices
> 작고, 독릭적이고, 바로 실행 할 수 있어야한다.
>
> Spring 에서 제공 하는 기능을 이용하면 대규모로 마이크로 서비스를 쉽게 구축하고 실행 할 수 있다.
- Reactive
>반응형 시스템에는 대기 시간이 짧고 처리량이 많은 워크로드에 이상적인 특정 특성이 있습니다.
> 
> 비동기, nonblocking 이용
- WebApps
>Spring을 사용하면 웹 애플리케이션을 빠르고 간편하게 구축할 수 있습니다.
> 
>By removing much of the boilerplate code and configuration associated with web development.

-Batch
> Automated task.

##IOC
> IoC란 Inversion of Control의 줄임말이며, 제어의 역전이라고 한다. 
> 
> 뭐가 역전 되었다는 것일까??
> 
##Bean
> Srping IoC 컨테이너에 의해서 관리되고 어플리케이션에서 중요한 역할을 하는 객체.

###Ioc 컨테이너 : Application Context
>스프링 애플리케이션에서는 오브젝트(빈)의 생성과 **의존 관계 설정**, 사용, 제거 등의 작업을 애플리케이션 코드 대신 스프링 컨테이너가 담당한다.
>
> 개발자가 해야될 것을 스프링 컨테이너가 대신 해주기 때문에 제어에 대한 역전이다 라고 부르고, 스프링 컨테이너를 Ioc 컨테이너라고도 부른다. 
>
> 개발자가 좀 더 비즈니스 로직에 집중 할 수 있게 해준다.

>간단하게 Bean은 애플리케이션 내에서 존재하느 수 많은 객체인데 Spring Container에서 관리되고 있다고 보면 된다. Spring 을 사용하면 Spring IoC 컨테이너에서 객체의 생성과 의존성 주입을 관리 할 수 있다.
##DI
>Code 로 설명.
>어떠한 객체를 사용할지에 대한 책임은 프레임워크에게 넘어갔고, 자신은 수동적으로 주입받는 객체를 사용하기 때문이다.

###DI와 IoC의 차이는?
>DI는 의존관계를 어떻게 가질 것인가에 대한 문제고, IoC는 누가 소프트웨어의 제어권을 갖고 있느냐의 문제다. IoC 컨테이너가 빈을 생성할 때 빈들간의 의존관계를 DI를 통해 해결한다.
> 
>DI는 IoC 사용을 필수로 요구하지 않는다는 점을 주의해야 한다.