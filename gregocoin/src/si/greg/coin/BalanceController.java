package si.greg.coin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BalanceController
{
	private static final Logger log = Logger.getLogger(BalanceController.class.getName());

	@Autowired
	private ExchangeService exchangeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getBalance(@RequestParam(required = false) String key, @RequestParam(required = false) String secret, ModelMap model)
	{
		Map<String, Double> balance = null;

		if (key != null && key.trim().length() > 0 && secret != null && secret.trim().length() > 0)
		{
			balance = exchangeService.getBalance(key, secret);
		}

		if (balance != null && balance.size() > 0)
		{
			Map<String, Double> rates = exchangeService.getRates();
			Map<String, Double> fiatRates = exchangeService.getFiatRates();
			Map<String, Double> btc = new HashMap<String, Double>();

			double totalBtc = 0;

			for (String coin : balance.keySet())
			{
				double btcValue = balance.get(coin) * rates.get(coin);
				if (btcValue > 0.0001)
				{
					btc.put(coin, btcValue);
					totalBtc += btcValue;
				}
			}

			btc = sortMap(btc);

			model.addAttribute("rates", rates);
			model.addAttribute("balance", balance);
			model.addAttribute("btc", btc);
			model.addAttribute("coins", btc.keySet());

			model.addAttribute("totalBtc", totalBtc);
			model.addAttribute("totalUsdBtce", totalBtc * fiatRates.get("btce-usd"));
			model.addAttribute("totalEurBtce", totalBtc * fiatRates.get("btce-eur"));
			model.addAttribute("totalUsdBitstamp", totalBtc * fiatRates.get("bitstamp-usd"));
			model.addAttribute("totalEurBitstamp", totalBtc * fiatRates.get("bitstamp-eur"));
			// model.addAttribute("totalUsdMtgox", totalBtc *
			// fiatRates.get("mtgox-usd"));
		}
		else if (key != null && key.length() > 0)
		{
			model.addAttribute("problem", 1);
		}

		model.addAttribute("key", key);
		model.addAttribute("secret", secret);

		return "balance";
	}

	public static String twoDecimals(Object num)
	{
		if (num != null)
		{
			DecimalFormat format = new DecimalFormat("###,###.00");
			return format.format(new Double(num.toString()));
		}

		return "";
	}

	private LinkedHashMap<String, Double> sortMap(Map<String, Double> passedMap)
	{
		List<String> mapKeys = new ArrayList<>(passedMap.keySet());
		List<Double> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		Collections.reverse(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();

		Iterator<Double> valueIt = mapValues.iterator();
		while (valueIt.hasNext())
		{
			Double val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext())
			{
				String key = keyIt.next();
				Double comp1 = passedMap.get(key);
				Double comp2 = val;

				if (comp1.equals(comp2))
				{
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}
}