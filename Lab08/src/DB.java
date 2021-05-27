import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB {

	public static Connection getConnection() throws NamingException, SQLException, MinhaException {
		InitialContext ctx = new InitialContext();

		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/xdb");
		if (ds == null)
			throw new MinhaException("Datasource não encontrado");

		Connection connection = ds.getConnection();
		if (connection == null)
			throw new MinhaException("Conexão nula");

		return connection;
	}
}
