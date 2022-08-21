package STUDY.TIL.java.servlet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 *
 * 1. 톰캣과 같은 Web Application Server(글자 수가 기니까, WAS로 줄이도록 하겠습니다)에는
 * 「웹 어플리케이션을 만들려면 이러이런 규격대로 만들어라」라는 개발 스펙이 있습니다.
 * 그 규격에는 《이런 HTTP 요청이 들어오면 이런 코드를 실행한다》같은 부분도 포함되어 있지요(저도 지식이 짧아서 확신해서 이야기드리지 못하겠지만, 특정 웹 요청과 코드를 연결시켜주는 것을 라우팅이라고 하는 것 같습니다).
 *
 * 저 스펙대로 만들어서 톰캣과 같은 WAS에 Deploy 시켜주면,
 * HTTP 요청은 톰캣이 받아서 분석하고 그 분석된 내용을 토대로 이미 연결된 코드가 있는지 확인합니다.
 * 그리고 요청에 연결된 코드가 있다면 그 부분을 실행하고 그 결과를 클라이언트에게 되돌려주는거죠.
 *
 * 정적인 파일이라도 결국은 정적인 파일의 HTTP 요청이므로,
 * 이 요청에 해당하는 정적 파일을 반환해주도록 작성하고 톰캣에 Deploy하면 됩니다
 * (제 경우 정적인 파일은 별다른 설정이나 개발 없이도 연결되었습니다만, 일단 쉽게 설명하기 위해서 직접 개발한다고 적었습니다).
 *
 * 2. 이 부분은 제가 지식이 얕아서 어떻게 설명드리면 좋을지 잘 모르겠는데요,
 * WAS는 웹서버와 DB 서버 사이에서 비즈니스 로직을 처리하기 위한 서버로 보시는 것이 가장 정확하지 않을까 생각합니다.
 * 그 비즈니스 로직을 처리하고 결과를 반환하는 프로토콜로써 HTTP를 사용하는 것이지요.
 *
 * 일반적으로 아파치와 같은 웹서버는 HTML이나 CSS, JS 파일 같은 정적인 파일들을 클라이언트에게 전달하기 위해 사용하고,
 * WAS는 그 외에 동적인 데이터를 처리하기 위해(=프로그램적인 처리가 필요한 경우에) 사용됩니다.
 * WAS가 정적인 파일들을 처리하는 것 또한 결국은 서버의 리소스를 사용해서 처리하는 것이므로,
 * WAS는 온전히 로직 처리만 할 수 있도록 하고 정적 파일만을 처리할 수 있도록 웹서버를 그 앞에 더 두게 됩니다.
 *
 */
@Controller
@RequiredArgsConstructor
public class ServletDemo implements Servlet {

    private final GenericServlet genericServlet;
    private final HttpServlet httpServlet;
    //
    private final HttpServletBean httpServletBean;
    private final FrameworkServlet frameworkServlet;
    private final DispatcherServlet dispatcherServlet;


    /**
     * Called by the servlet container to indicate to a servlet that the servlet
     * is being placed into service.
     *
     * The servlet container calls the <code>init</code> method exactly once
     * after instantiating the servlet. The <code>init</code> method must
     * complete successfully before the servlet can receive any requests.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    /**
     * Returns a {@link ServletConfig} object, which contains initialization and
     * startup parameters for this servlet. The <code>ServletConfig</code>
     * object returned is the one passed to the <code>init</code> method.
     *
     * <p>
     * Implementations of this interface are responsible for storing the
     * <code>ServletConfig</code> object so that this method can return it. The
     * {@link GenericServlet} class, which implements this interface, already
     * does this.
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * Called by the servlet container to allow the servlet to respond to a
     * request.
     *
     * <p>
     * This method is only called after the servlet's <code>init()</code> method
     * has completed successfully.
     *
     * <p>
     * Servlets typically run inside multithreaded servlet containers that can
     * handle multiple requests concurrently. Developers must be aware to
     * synchronize access to any shared resources such as files, network
     * connections, and as well as the servlet's class and instance variables.
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    }

    /**
     * Returns information about the servlet, such as author, version, and
     * copyright.
     *
     * <p>
     * The string that this method returns should be plain text and not markup
     * of any kind (such as HTML, XML, etc.).
     *
     * @return a <code>String</code> containing servlet information
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * Called by the servlet container to indicate to a servlet that the servlet
     * is being taken out of service. This method is only called once all
     * threads within the servlet's <code>service</code> method have exited or
     * after a timeout period has passed. After the servlet container calls this
     * method, it will not call the <code>service</code> method again on this
     * servlet.
     *
     * <p>
     * This method gives the servlet an opportunity to clean up any resources
     * that are being held (for example, memory, file handles, threads) and make
     * sure that any persistent state is synchronized with the servlet's current
     * state in memory.
     */
    @Override
    public void destroy() {

    }
}
