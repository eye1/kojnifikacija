package si.greg.coin;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService
{
	private static final Logger log = Logger.getLogger(ExchangeService.class.getName());

	public void sendPassword(String email, String name, String password)
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try
		{
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress("sumofcoins@gmail.com", "Sum of Coins"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			msg.setSubject("It's your Sum of Coins password!");
			msg.setText("A warm welcome to you, " + name + "!\n\nYour password for Sum of Coins is " + password + ".\n\nPlease, change is soon!\n\nBest regards,\nSum of Coins Team");

			Transport.send(msg);

		}
		catch (Exception e)
		{
			log.severe(e.getMessage());
		}
	}
}
