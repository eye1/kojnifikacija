package si.greg.coin;

import java.util.Random;

public class Utils
{
	private static final String characters = "abcdef0123456789";
	private static final Random random = new Random();

	public static String generatePassword()
	{
		char[] text = new char[6];

		for (int i = 0; i < 6; i++)
		{
			text[i] = characters.charAt(random.nextInt(characters.length()));
		}

		String inviteCode = new String(text);

		return inviteCode;
	}
}
