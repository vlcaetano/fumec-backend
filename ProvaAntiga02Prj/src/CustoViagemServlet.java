
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustoViagemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private double precoGasolina;
	private double precoAlcool;
	private double precoDiesel;

	public CustoViagemServlet() {
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			precoGasolina = Double.parseDouble(getInitParameter("gasolina"));
			precoAlcool = Double.parseDouble(getInitParameter("alcool"));
			precoDiesel = Double.parseDouble(getInitParameter("diesel"));
		} catch (NumberFormatException e) {
			System.out.println("Erro recuperando os parâmetros de inicialização");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Resposta");
		out.println("</title></head><body>");
		out.println("<h1>Resposta</h1>");
		out.println("<h2>Tempo de viagem</h2>");
		out.println(calcularTempoViagem(request));
		if (request.getParameter("calcular") != null) {
			out.println("<h2>Custo</h2>");
			out.println(calcularConsumo(request));
		}
		out.println("<p/><a href='viagem.html'>Voltar</a>");
		out.println("</body></html>");

		out.close();
	}

	private String calcularConsumo(HttpServletRequest req) {
		double consumoMedio = 0;
		double dist = 0;

		try {
			dist = Double.parseDouble(req.getParameter("distancia"));
			consumoMedio = Double.parseDouble(req.getParameter("consumo"));
		} catch (NumberFormatException e) {
			return "Inserir números válidos nos campos";
		}
		
		if (dist <= 0 || consumoMedio <= 0) {
			return "Os valores devem ser maior que zero";
		}

		String combustivel = req.getParameter("combustivel");
		double preco;

		if ("Gasolina".equals(combustivel)) {
			preco = dist / consumoMedio * precoGasolina;
		} else if ("Álcool".equals(combustivel)) {
			preco = dist / consumoMedio * precoAlcool;
		} else {
			preco = dist / consumoMedio * precoDiesel;
		}

		return String.format("Custo combustível: R$%.2f", preco);
	}

	private String calcularTempoViagem(HttpServletRequest req) {
		double dist = 0;
		double vm = 0;

		try {
			dist = Double.parseDouble(req.getParameter("distancia"));
			vm = Double.parseDouble(req.getParameter("velocidade"));

			if (dist <= 0 || vm <= 0) {
				return "Os valores devem ser maior que zero";
			}

			return String.format("Tempo de viagem: %.1f hora(s)", (dist / vm));
		} catch (NumberFormatException e) {
			return "Inserir números válidos nos campos";
		}
	}
}
