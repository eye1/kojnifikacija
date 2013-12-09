package si.greg.coin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExchangeServlet extends HttpServlet
{
	private static final Logger log = Logger.getLogger(BalanceServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// ExchangeService.saveBalance();
	}
}