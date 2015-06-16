package com.mc.mail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ReadingMail Reading Kieser data from googlemail
 */
@WebServlet("/ReadingMail")
public class ReadingMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String saveDirectory = "/Users/stefan/work/techno/GymView";

	private MimeBodyPart mailAttachment;

	public MimeBodyPart getMailAttachment() {
		return mailAttachment;
	}

	public void setMailAttachment(MimeBodyPart mailAttachment) {
		this.mailAttachment = mailAttachment;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReadingMail() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// readMail();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// if run as a servlet
	}

	/**
	 * Read mail from gmail server and store attachment data in a file in the
	 * filesystem
	 * 
	 * @param dataSource
	 *            open database connection
	 * @return user key of generated user training records in database
	 */
	protected int readMail(DataSource dataSource) {
		int userId = -1;
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("imap.gmail.com", "stefankmarx@gmail.com", "blue24go");
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);
			Message[] arrayMessages = inbox.getMessages();
			for (int i = 0; i < arrayMessages.length; i++) {
				Message message = arrayMessages[i];
				Address[] fromAddress = message.getFrom();
				String from = fromAddress[0].toString();
				String subject = message.getSubject();
				String sentDate = message.getSentDate().toString();

				String contentType = message.getContentType();
				String messageContent = "";

				// store attachment file name, separated by comma
				String attachFiles = "";

				if (contentType.contains("multipart")) {
					// content may contain attachments
					Multipart multiPart = (Multipart) message.getContent();
					int numberOfParts = multiPart.getCount();
					System.out.println("Found " + numberOfParts
							+ " numbers of attachments.");
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart
								.getBodyPart(partCount);
						if (Part.ATTACHMENT.equalsIgnoreCase(part
								.getDisposition())) {
							// this part is attachment
							setMailAttachment(part);
							String fileName = part.getFileName();
							attachFiles += fileName + ", ";
							System.out.println("Lines in part "
									+ part.getSize());

							part.saveFile(saveDirectory + File.separator
									+ fileName);
						} else {
							// this part may be the message content
							messageContent = part.getContent().toString();
						}
					}

					if (attachFiles.length() > 1) {
						attachFiles = attachFiles.substring(0,
								attachFiles.length() - 2);
					}
				} else if (contentType.contains("text/plain")
						|| contentType.contains("text/html")) {
					Object content = message.getContent();
					if (content != null) {
						messageContent = content.toString();
					}
				}

				for (int j = 0; j < arrayMessages.length; j++) {
					Message imessage = arrayMessages[j];
					imessage.setFlag(Flags.Flag.DELETED, true);
				}
				// inbox.close(true);

				// print out details of each message
				System.out.println("Message #" + (i + 1) + ":");
				System.out.println("\t From: " + from);
				// compute user id from from and users table
				userId = usergetUserIdFormSender(from, dataSource);
				System.out.println("\t Subject: " + subject);
				System.out.println("\t Sent Date: " + sentDate);
				System.out.println("\t Message: " + messageContent);
				System.out.println("\t Attachments: " + attachFiles);
			}
		} catch (Exception mex) {
			mex.printStackTrace();
			System.out.println("Can't connect to mail store, no net access!");
		}
		return userId;
	}

	private int usergetUserIdFormSender(String from, DataSource dataSource) {
		int userUid = -1;
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {

			java.sql.PreparedStatement selectUI = sqlConn
					.prepareStatement("select user_id from users where mail = ?");
			final String mailadr = getMailAddress(from);
			selectUI.setString(1, mailadr);

			selectUI.execute();

			ResultSet rs = selectUI.executeQuery();
			if (rs.next()) {
				String buff;
				buff = rs.getString(1);
				System.out.println("Found  UID " + buff);
				userUid = Integer.parseInt(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userUid;
	}

	private String getMailAddress(String from) {
		// From: Stefan Marx <Stefan.Marx@marx-consulting.com>
		String buff = from.substring(from.indexOf("<"));
		return buff.substring(1, buff.length() - 1).toLowerCase();
	}

	public void saveAttachement(DataSource dataSource, MimeBodyPart part, int userId) {
		int generatedkey;
		System.out.println("Starting saveAttachments ..");
		System.out.println("Going to mysql ... " + new Date());
		try (java.sql.Connection sqlConn = dataSource.getConnection();) {
			java.sql.PreparedStatement insertStatement = sqlConn
					.prepareStatement(
							"insert into attachments (insertdate, user_id, org_attachment) values ( ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);

			long time = System.currentTimeMillis();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
			insertStatement.setTimestamp(1, timestamp);
			insertStatement.setInt(2, userId);

			if (part != null) {
				com.sun.mail.imap.IMAPInputStream theStream = (com.sun.mail.imap.IMAPInputStream) part
						.getContent();

				InputStream is = theStream;
				// If "is" is not already buffered, wrap a BufferedInputStream
				// around it.
				if (!(is instanceof BufferedInputStream)) {
					is = new BufferedInputStream(is);
				}

				int c;
				StringBuffer buff = new StringBuffer();
				while ((c = is.read()) != -1) {
					buff.append((char) (c));
				}
				insertStatement.setString(3, buff.toString());
			} else {
				insertStatement.setString(3, "ERROR: no attachments found!");
			}

			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				generatedkey = rs.getInt(1);
				System.out
						.println("Auto Generated Primary Key " + generatedkey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init(getServletConfig());
	}
}
