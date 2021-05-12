
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MontyListener implements ServletContextListener, HttpSessionListener {

	public MontyListener() {
	}

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();

		session.setAttribute("escolhaUsuario", 0);
		session.setAttribute("portaCerta", 0);
		session.setAttribute("portaAberta", 0);
		session.setAttribute("pontuacao", 0);
		session.setAttribute("rodada", 1);
	}

	public void sessionDestroyed(HttpSessionEvent se) {

	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
        context.setAttribute("pontuacaoMaxima", 0);
	}

}
