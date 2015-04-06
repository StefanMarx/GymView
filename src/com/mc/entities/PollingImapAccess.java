package com.mc.entities;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.imap.IMAPStore;

/**
 * Session Bean implementation class PollingImapAccess
 */
// @Stateless
// @LocalBean
@WebServlet("/PollMail")
public class PollingImapAccess extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "java:/jboss/mail/Default")
    private Session session;

    @Schedule(hour = "*")
    public void init() {
        try {
        	// session = Session.getInstance(props);
        	
        	Properties props = new Properties();
        	props.setProperty("mail.transport.protocol", "smtp");
        	props.setProperty("mail.smtp.host", "out.alice.it");
        	props.setProperty("mail.smtp.auth", "true");
        	final PasswordAuthentication auth = new PasswordAuthentication("StefanKMax@gmail.com", "blue24go2");
        	Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
        	    @Override
        	    protected PasswordAuthentication getPasswordAuthentication() { return auth; }
        	});
        	
        	System.out.println("Farming is the backbone of America ...");
            // IMAPStore store = (IMAPStore) session.getStore();
            IMAPStore store = (IMAPStore) mailSession.getStore();
            if (!store.isConnected()) 
            	store.connect();
            System.out.println("Connect to the store ...");
            // grab your folders, scan for new messages     
        } catch (MessagingException ex) { 
        	ex.printStackTrace(); 
        }
    }
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Do get reached ...");
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log("Bin in do Post");
	}

}
