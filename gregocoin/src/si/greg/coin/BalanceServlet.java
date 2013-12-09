package si.greg.coin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class BalanceServlet extends HttpServlet
{
	private static final Logger log = Logger.getLogger(BalanceServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{

		resp.setContentType("text/plain");

		DatastoreService store = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = store.prepare(new Query("Balance").addSort("date", SortDirection.DESCENDING));
		List<Entity> results = pq.asList(FetchOptions.Builder.withLimit(1));
		if (results != null && results.size() > 0)
		{
			resp.getWriter().println(results.get(0).getProperty("eur"));
		}
	}
}
