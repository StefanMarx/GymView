package com.mc.mail;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletException;
import javax.sql.DataSource;

@Stateless
/**
 * http://www.hascode.com/2012/06/task-scheduling-in-java-ee-6-on-glassfish-using-the-timer-service/#Programmatic_Timer_Creation
 * @author stefan marx
 *
 */
public class ProgrammaticTimeEBJ {
	@Resource(lookup = "java:/MySQLDS")
	private DataSource dataSource;
	
	private int userIdFromMail = 0;

	public int getUserIdFromMail() {
		return userIdFromMail;
	}

	public void setUserIdFromMail(int userIdFromMail) {
		this.userIdFromMail = userIdFromMail;
	}
	
    /**
     * Default constructor. 
     */
    public ProgrammaticTimeEBJ() {
        // TODO Auto-generated constructor stub
    }
	
	// @SuppressWarnings("unused")
	@Schedule(second="*/6000", minute="*", hour="8-23", dayOfWeek="Mon-Sun",
      dayOfMonth="*", month="*", year="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) throws ServletException {
        // System.out.println("@Schedule called at: " + new java.util.Date());
		System.out.println("Going to gmail ... " + new Date());

		ReadingMail readmail = new ReadingMail();
		int userIdFromMail = readmail.readMail(dataSource);
		this.setUserIdFromMail(userIdFromMail);
		MimeBodyPart part = readmail.getMailAttachment();
		if ( part != null){
			try {
				System.out.println("Found part in schedule " + part.getSize());
				readmail.saveAttachement(dataSource, part);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Part is empty");
		}
    }
}