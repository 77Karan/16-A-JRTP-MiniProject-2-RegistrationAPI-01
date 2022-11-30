package in.ashokit.utils;

import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class EmailUtils 
{
	@Autowired
	private JavaMailSender mailSender;
	
	
	public boolean sendEmail(String subject, String body, String to) 
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try 
		{
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(body);
			mailSender.send(mimeMessageHelper.getMimeMessage());
			return true;
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}

}
