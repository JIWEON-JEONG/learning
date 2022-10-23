/**
* WebServer
* 클라이언트로 부터 HTTP 요청을 받을 수 있다.
* 정적 컨텐츠 요청시,
* html jpeg css 등과 같은 정적 컨텐츠를 제공 할 수 있고,
* 동적 컨텐츠 요청시,
* WAS 로 전달하여 WAS 가 처리한 결과를 클라이언트에게 전달 할 수 있다.
* 
* WAS
* 클라이언트로 부터 HTTP 요청을 받을 수 있다. (대부분 WebServer 내장)
* 요청에 맞는 정적 컨텐츠 제공.
* DB 조회나 다양한 로직 처리를 통해 동적 컨텐츠 제공.
* 
* 상황예시) SUWIKI EC2 로 WAS 띄워 놓았을때, WAS 앞단에 따로 WebServer 두는 이유?
* - 책임 분할을 통한 서버 부하 방지
* - 정적 컨텐츠는 WebServer , 동적 컨텐츠는 WAS 가 담당.
* - 로드 밸런싱 가능 여러대의 WAS
* - 리버스 프록시를 통해 실제 서버를 외부에 노출하지 않을수 있다.
* 
* EC2 한대에 DB 까지 두면 안좋은 이유 -> 확장성. WebServer 예시와 같은 맥락.
* 
* CGI 구현체 : 웹서버와 프로그램 사이 통신하기 위한 인터페이스
* 단점
* -
*  매 요청마다 새로운 프로세스 생성
* Servlet : 웹 어플리케이션을 만들때 필요한 인터페이스.
* 서블릿 컨테이너가 서블릿들을 관리하고 서버와 통신 한다.
* 서블릿들은 Servlet 인터페이스를 상속받아 구현한다.
* 
* Servlet 컨테이너 에서 Dispatcher 서블렛 -> 해당 url, method 에 맞는 컨테이너를 찾고 ,
* 해당 서블릿 설정파일에 어떤 handler (controller) 사용할지 저장.
* 
* Handler Adapter 가 해당 controller 호출, 결과 반환 (model)
* 
* View 에 model 심어서 view 로 반환
* 그다음에 servlet 컨테이너에 view 반환.
* 
* Spring Web MVC -> url 마다 servlet 만들어주고 xml 에 매핑 해야 했었는데, 지금은 DispatcherServlet 1개로 관리
* 원래는 view 로 보내주는거 까지 만들어줬어야한다.
* 
* 결과적으로 http response 형태 text 로 보내줘야 되는데 이걸 개발자가 손수 만들어서 보내준다? 굉장히 귀찮은 작업.
* 메서드를 통해 쉽게 만들어 response 해주기.
* 
* 
* 스프링부트에서 사용되는 DispatcherServlet 상속구조
* Object -> Servlet 인터페이스를 구현한 GenericServlet
* -> HttpServlet
* ---- tomcat
* ---- spring
* -> HttpServletBean
* -> FrameworkServlet -> DispatcherServlet
  */

Web Server가 필요한 이유?
클라이언트(웹 브라우저)에 이미지 파일(정적 컨텐츠)을 보내는 과정을 생각해보자.
이미지 파일과 같은 정적인 파일들은 웹 문서(HTML 문서)가 클라이언트로 보내질 때 함께 가는 것이 아니다.
클라이언트는 HTML 문서를 먼저 받고 그에 맞게 필요한 이미지 파일들을 다시 서버로 요청하면 그때서야 이미지 파일을 받아온다.
Web Server를 통해 정적인 파일들을 Application Server까지 가지 않고 앞단에서 빠르게 보내줄 수 있다.
따라서 Web Server에서는 정적 컨텐츠만 처리하도록 기능을 분배하여 서버의 부담을 줄일 수 있다.

웹 페이지는 정적 컨텐츠와 동적 컨텐츠가 모두 존재한다.
사용자의 요청에 맞게 적절한 동적 컨텐츠를 만들어서 제공해야 한다.
이때, Web Server만을 이용한다면 사용자가 원하는 요청에 대한 결과값을 모두 미리 만들어 놓고 서비스를 해야 한다.
하지만 이렇게 수행하기에는 자원이 절대적으로 부족하다.
따라서 WAS를 통해 요청에 맞는 데이터를 DB에서 가져와서 비즈니스 로직에 맞게 그때 그때 결과를 만들어서 제공함으로써 자원을 효율적으로 사용할 수 있다.


Web Server는 웹 브라우저 클라이언트로부터 HTTP 요청을 받는다.
Web Server는 클라이언트의 요청(Request)을 WAS에 보낸다.
WAS는 관련된 Servlet을 메모리에 올린다.
WAS는 web.xml을 참조하여 해당 Servlet에 대한 Thread를 생성한다. (Thread Pool 이용)
HttpServletRequest와 HttpServletResponse 객체를 생성하여 Servlet에 전달한다.
5-1. Thread는 Servlet의 service() 메서드를 호출한다.
5-2. service() 메서드는 요청에 맞게 doGet() 또는 doPost() 메서드를 호출한다.
protected doGet(HttpServletRequest request, HttpServletResponse response)
doGet() 또는 doPost() 메서드는 인자에 _맞게 생성된 적절한 동적 페이지를_ Response 객체에 담아 WAS에 전달한다.
WAS는 Response 객체를 HttpResponse 형태로 바꾸어 Web Server에 전달한다.
생성된 Thread를 종료하고, HttpServletRequest와 HttpServletResponse 객체를 제거한다.
