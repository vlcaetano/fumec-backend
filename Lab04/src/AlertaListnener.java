

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AlertaListnener implements HttpSessionListener, ServletContextListener {
	
    public AlertaListnener() {
    }

    public void sessionCreated(HttpSessionEvent se)  { 
    	HttpSession session = se.getSession();
		session.setAttribute("requisicoes", 0);
		
		ServletContext context = se.getSession().getServletContext();
		int numConexoes = (Integer) context.getAttribute("numConexoes");
		context.setAttribute("numConexoes", ++numConexoes);
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    	ServletContext context = se.getSession().getServletContext();
		
		int numConexoes = (Integer) context.getAttribute("numConexoes");
		context.setAttribute("numConexoes", --numConexoes);
    }

    public void contextDestroyed(ServletContextEvent sce)  {
    }

    public void contextInitialized(ServletContextEvent sce)  { 
         ServletContext context = sce.getServletContext();
         context.setAttribute("numConexoes", 0);
    }
	
}
