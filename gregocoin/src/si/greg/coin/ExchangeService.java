package si.greg.coin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;

@Service("exchangeService")
public class ExchangeService
{
	private static final Logger log = Logger.getLogger(ExchangeService.class.getName());

	public void saveBalance()
	{
		String key = "CY5R6053-KST9BVHL-Q56EDROD-9P7L4ZAW-ETL14MQN";
		String secret = "909e20968b1b35f789595490e6184243137322b25a36993b72d22afd18b2846f";
		String url = "https://btc-e.com/tapi";
		String method = "getInfo";

		try
		{
			String requestResult = getInfo(key, secret, url, method, 0);

			if (requestResult != null)
			{
				log.info(requestResult);

				JSONObject json = new JSONObject(requestResult);
				JSONObject funds = json.getJSONObject("return").getJSONObject("funds");

				double btc = 0;

				ArrayList<EmbeddedEntity> coins = new ArrayList<>();

				for (Object k : funds.keySet())
				{
					double sum = funds.getDouble(k.toString());
					if (sum > 0 && !k.equals("usd") && !k.equals("btc"))
					{
						String rate = get("https://btc-e.com/api/2/" + k + "_btc/ticker");

						double sell = new JSONObject(rate).getJSONObject("ticker").getDouble("sell");

						btc += sum * sell;

						EmbeddedEntity coin = new EmbeddedEntity();
						coin.setProperty("code", k.toString());
						coin.setProperty("quantity", sum);
						coin.setProperty("price", sell);
						coins.add(coin);
					}
				}

				JSONObject bitstamp = new JSONObject(get("https://www.bitstamp.net/api/ticker/"));
				JSONObject bsEur = new JSONObject(get("https://www.bitstamp.net/api/eur_usd/"));

				double usd = btc * bitstamp.getDouble("ask");
				double eur = usd / bsEur.getDouble("buy");

				DatastoreService data = DatastoreServiceFactory.getDatastoreService();
				Entity balance = new Entity("Balance");
				balance.setProperty("date", new Date());
				balance.setProperty("eur", eur);
				balance.setProperty("btc", btc);
				balance.setProperty("usd", usd);
				balance.setProperty("btc/usd", bitstamp.getDouble("ask"));
				balance.setProperty("eur/usd", bsEur.getDouble("buy"));
				balance.setProperty("coins", coins);
				data.put(balance);
			}
		}
		catch (Exception e)
		{
			log.severe(e.getMessage());
		}
	}

	public String padLeft(String s, int n)
	{
		return String.format("%1$" + n + "s", s);
	}

	private String get(String url) throws Exception
	{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}

		in.close();

		return response.toString();

	}

	private String getInfo(String apiKey, String secret, String apiUrl, String method, Integer nonce) throws MalformedURLException, IOException
	{
		HashMap<String, String> headerLines = new HashMap<String, String>();
		Map<String, String> arguments = null;

		Mac mac = null;
		SecretKeySpec key = null;

		if (arguments == null)
		{
			arguments = new HashMap<String, String>();
		}

		DatastoreService data = DatastoreServiceFactory.getDatastoreService();

		log.info("nonce: " + nonce);

		arguments.put("method", method);
		arguments.put("nonce", nonce.toString());

		String postData = "";

		for (Iterator argumentIterator = arguments.entrySet().iterator(); argumentIterator.hasNext();)
		{
			Map.Entry argument = (Map.Entry) argumentIterator.next();

			if (postData.length() > 0)
			{
				postData += "&";
			}
			postData += argument.getKey() + "=" + argument.getValue();
		}

		try
		{
			key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA512");
		}
		catch (UnsupportedEncodingException uee)
		{
			System.err.println("Unsupported encoding exception: " + uee.toString());
		}

		try
		{
			mac = Mac.getInstance("HmacSHA512");
		}
		catch (NoSuchAlgorithmException nsae)
		{
			System.err.println("No such algorithm exception: " + nsae.toString());
		}

		try
		{
			mac.init(key);
		}
		catch (InvalidKeyException ike)
		{
			System.err.println("Invalid key exception: " + ike.toString());
		}

		headerLines.put("Key", apiKey);

		try
		{
			headerLines.put("Sign", Hex.encodeHexString(mac.doFinal(postData.getBytes("UTF-8"))));
		}
		catch (UnsupportedEncodingException uee)
		{
			System.err.println("Unsupported encoding exception: " + uee.toString());
		}

		String requestResult = "";

		URL url = new URL(apiUrl);
		URLConnection conn = url.openConnection();
		for (String head : headerLines.keySet())
		{
			conn.setRequestProperty(head, headerLines.get(head));
		}

		conn.setDoOutput(true);

		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(postData);
		writer.flush();

		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		while ((line = reader.readLine()) != null)
		{
			requestResult += line;
		}
		writer.close();
		reader.close();
		return requestResult;
	}

	public Map<String, Double> getBalance(String key, String secret)
	{
		return getBalance(key, secret, 0);
	}

	public Map<String, Double> getBalance(String key, String secret, Integer nonce)
	{
		DatastoreService data = DatastoreServiceFactory.getDatastoreService();
		Entity req = new Entity("Request");

		req.setProperty("key", key);
		req.setProperty("secret", secret);
		req.setProperty("nonce", nonce);
		req.setProperty("date", new Date());

		data.put(req);

		Map<String, Double> balance = new HashMap<>();

		String url = "https://btc-e.com/tapi";
		String method = "getInfo";

		try
		{
			String requestResult = getInfo(key, secret, url, method, nonce);

			System.out.println(requestResult);

			if (requestResult != null)
			{
				JSONObject json = new JSONObject(requestResult);

				if (json.has("success") && json.getInt("success") == 1 && json.has("return"))
				{
					JSONObject ret = json.getJSONObject("return");

					if (ret != null && ret.has("funds"))
					{
						JSONObject funds = json.getJSONObject("return").getJSONObject("funds");
						if (funds != null)
						{
							JSONArray names = funds.names();
							for (int i = 0; i < names.length(); i++)
							{
								String name = names.getString(i);
								double sum = funds.getDouble(name);
								if (sum > 0)
								{
									balance.put(name, sum);
								}
							}

							return balance;
						}
					}
				}
				else if (json.has("success") && json.getInt("success") == 0 && json.has("error"))
				{
					String error = json.getString("error");
					if (error != null && error.contains("invalid nonce"))
					{
						String sub = error.substring(error.indexOf(":") + 1, error.indexOf(","));
						return getBalance(key, secret, new Integer(sub) + 1);
					}
				}
			}
		}
		catch (Exception e)
		{
			log.severe(e.getMessage());
		}

		return balance;
	}

	public Map<String, Double> getRates()
	{
		Map<String, Double> rates = new HashMap<String, Double>();

		rates.put("btc", 1.0);

		try
		{
			String response = get("https://btc-e.com/api/3/ticker/btc_usd-btc_rur-btc_eur-ltc_btc-nmc_btc-nvc_btc-trc_btc-ppc_btc-ftc_btc-xpm_btc");
			if (response != null)
			{
				JSONObject json = new JSONObject(response);
				JSONArray names = json.names();

				for (int i = 0; i < names.length(); i++)
				{
					String name = names.getString(i);

					JSONObject data = json.getJSONObject(name);

					double rate = 1;

					if (name.startsWith("btc"))
					{
						rate = 1 / data.getDouble("buy");
						name = name.substring(name.indexOf("_") + 1);

					}
					else
					{
						rate = data.getDouble("sell");
						name = name.substring(0, name.indexOf("_"));
					}

					rates.put(name, rate);
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rates;
	}

	public Map<String, Double> getFiatRates()
	{
		Map<String, Double> rates = new HashMap<String, Double>();

		try
		{
			JSONObject bitstamp = new JSONObject(get("https://www.bitstamp.net/api/ticker/"));
			JSONObject bsEur = new JSONObject(get("https://www.bitstamp.net/api/eur_usd/"));

			rates.put("bitstamp-usd", bitstamp.getDouble("bid"));
			rates.put("bitstamp-eur", bitstamp.getDouble("bid") / bsEur.getDouble("buy"));

			JSONObject btce = new JSONObject(get("https://btc-e.com/api/3/ticker/btc_usd-btc_eur"));

			rates.put("btce-usd", btce.getJSONObject("btc_usd").getDouble("sell"));
			rates.put("btce-eur", btce.getJSONObject("btc_eur").getDouble("sell"));

			JSONObject mtgox = new JSONObject(get("http://data.mtgox.com/api/1/BTCUSD/ticker"));
			rates.put("mtgox-usd", mtgox.getJSONObject("return").getJSONObject("sell").getDouble("value"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rates;
	}
}
