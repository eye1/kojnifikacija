package si.greg.coin;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Controller
public class MainController
{
	private static final Logger log = Logger.getLogger(MainController.class.getName());

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String getDashboard(ModelMap model)
	{
		return Pages.DASHBOARD;
	}

	@RequestMapping(value = "exchanges", method = RequestMethod.GET)
	public String getExchanges(ModelMap model)
	{
		return Pages.EXCHANGES;
	}

	@RequestMapping(value = "wallets", method = RequestMethod.GET)
	public String getWallets(ModelMap model)
	{
		return Pages.WALLETS;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String postLogin(ModelMap model)
	{
		return Pages.REDIRECT_DASHBOARD;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String getLogin(ModelMap model)
	{
		return Pages.LOGIN;
	}

	@RequestMapping(value = "create-account", method = RequestMethod.GET)
	public String getCreateAccount(ModelMap model)
	{
		return Pages.CREATE_ACCOUNT;
	}

	@RequestMapping(value = "create-account", method = RequestMethod.POST)
	public String postCreateAccount(@RequestParam String name, @RequestParam String email, ModelMap model)
	{
		if (name != null && email != null)
		{
			name = name.trim();
			email = email.trim();

			DatastoreService data = DatastoreServiceFactory.getDatastoreService();

			Query q = new Query("User").setFilter(new FilterPredicate("email", FilterOperator.EQUAL, email));
			PreparedQuery pq = data.prepare(q);
			int count = pq.countEntities(FetchOptions.Builder.withLimit(1));

			if (count > 0)
			{
				model.put("name", name);
				model.put("email", email);
				model.put("msg", "email-exists");

				return Pages.REDIRECT_CREATE_ACCOUNT;
			}

			String password = Utils.generatePassword();

			Entity user = new Entity("User");
			user.setProperty("name", name);
			user.setProperty("email", email);
			user.setProperty("password", password);
			user.setProperty("date", new Date());

			data.put(user);

			mailService.sendPassword(email, name, password);

			model.put("msg", "password-sent");

			return Pages.REDIRECT_LOGIN;
		}

		return Pages.REDIRECT_CREATE_ACCOUNT;
	}
}
