package si.greg.coin;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class BalanceService
{
	public static Entity getRates()
	{
		DatastoreService store = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Balance").addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = store.prepare(q);
		List<Entity> results = pq.asList(FetchOptions.Builder.withLimit(1));

		if (results != null && results.size() > 0)
		{
			return results.get(0);
		}

		return null;
	}

	public static void saveBalance()
	{

	}
}
