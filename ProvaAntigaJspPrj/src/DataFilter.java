
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DataFilter implements Filter {

	double limite = 0.99;

	public DataFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		double valorAleatorio = Math.random();

		if (valorAleatorio > limite && "POST".equals(((HttpServletRequest) request).getMethod())) {
			
			int novaDiferenca = (int) (Math.random() * 30);
			((HttpServletRequest) request).setAttribute("resposta", ("Diferença: " + novaDiferenca));

			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/formularioData.jsp");
			rd.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		limite = Double.parseDouble(fConfig.getInitParameter("limite"));
	}
}
