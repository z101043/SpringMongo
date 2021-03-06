package initializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import config.MongoConfig;
import config.MvcConfig;
import config.RootConfig;

/**
 * WebApplicationInitializer 를 상속하면, 서블릿 컨테이너가 실행될 때 onStartup() 메소드가 자동으로 호출된다.
 * 이 클래스는 web.xml 의 역할을 대신하거나 보충한다.
 * @author mj
 *
 */
public class AppInitializer implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException
    {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(RootConfig.class);
        rootContext.register(MongoConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        
        this.addDispatcherServlet(servletContext);
        this.addUtf8CharacterEncodingFilter(servletContext);
    }
    
    /**
     * Dispatcher Servlet 을 추가한다.
     * CORS 를 가능하게 하기 위해서 dispatchOptionsRequest 설정을 true 로 한다.
     * @param servletContext
     */
    private void addDispatcherServlet(ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(MvcConfig.class);
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
       // dispatcher.setInitParameter("dispatchOptionsRequest", "true"); // CORS 를 위해서 option request 도 받아들인다.
    }
    
    /**
     * UTF-8 캐릭터 인코딩 필터를 추가한다.
     * @param servletContext
     */
    private void addUtf8CharacterEncodingFilter(ServletContext servletContext)
    {
        FilterRegistration.Dynamic filter = servletContext.addFilter("CHARACTER_ENCODING_FILTER", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding", "UTF-8");
        filter.setInitParameter("forceEncoding", "true");
        filter.addMappingForUrlPatterns(null, false, "/*");
    }
}
