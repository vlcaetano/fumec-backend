

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CalculoListener implements HttpSessionListener {

    public CalculoListener() {
    }
    
    public void sessionCreated(HttpSessionEvent se)  { 
         se.getSession().setAttribute("termosUsados", 0);
    }
    
    public void sessionDestroyed(HttpSessionEvent se)  {
    }
}
