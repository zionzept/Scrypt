package ss;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import parsing.ParsingException;
import statements.StatementBlock;

public class Bot {
	private static final String acc = "sscriptbot@gmail.com";
	private static final String pass = "8jS\"ia2!n.13s9:S1_k~2is8ss7dKK1zuv8";
	private static final int msg_limit = 5000;

	public static void receiveEmail(String host, String storeType, String user, String password) {
		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			emailSession.setDebug(true);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("Inbox");
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	private static Session openSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(acc, pass);
			}
		});
	}

	private static void openMail(final Session session, Store store) throws MessagingException, IOException {
		// create the POP3 store object and connect with the pop server

		// create the folder object and open it
		Folder emailFolder = store.getFolder("Inbox");
		emailFolder.open(Folder.READ_ONLY);

		// retrieve the messages from the folder in an array and print it
		Message[] messages = emailFolder.getMessages();
		for (final Message m : messages) {
			final Address sender = m.getFrom()[0];
			chatbox.append("Script received from " + sender.toString() + ".\n");
			m.setFlag(Flags.Flag.SEEN , true);
			final Thread main_thread = Thread.currentThread();
			final Thread script_thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						try {
							String res = processMessage(m);
							chatbox.append("Script parsed from " + sender.toString() + ".\n");
							sendMail(sender.toString(), "script result", res, session);

						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParsingException e) {
							String res = e.getMessage();
							chatbox.append("Failed script from " + sender.toString() + ".\n");
							sendMail(sender.toString(), "script failed", res, session);
						}
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					main_thread.interrupt();
				}
			});
			script_thread.start();
			try {
				Thread.sleep(5000);
				script_thread.interrupt();
			} catch (InterruptedException e) {
			}
		}
		// close the store and folder objects
		emailFolder.close(false);

	}

	private static void sendMail(String to, String subject, String text, Session session) {
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sscriptbot@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

			chatbox.append("Message sent to " + to + ".\n");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String processMessage(Message m) throws MessagingException, IOException, ParsingException {
		Scrypt scrypt = new Scrypt(getTextFromMessage(m));

		final Charset charset = StandardCharsets.UTF_8;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		scrypt.out = ps;

		StatementBlock sb = scrypt.parse();
		sb.interpret();

		String result = new String(os.toByteArray(), charset);
		if (result.length() > msg_limit) {
			result = result.substring(0, msg_limit) + "\n...result length limit reached";
		}
		os.close();
		ps.close();

		return result;
	}

	private static JTextArea chatbox;
	private static boolean running = true;

	public static void main(String[] args) throws MessagingException, IOException {
		// String host = "mail.google.com";// change accordingly
		// String mailStoreType = "pop3";
		// String username = "sscriptbot@gmail.com";
		// String password = "8jS\"ia2!n.13s9:S1_k~2is8ss7dKK1zuv8";// change
		// accordingly

		// receiveEmail(host, mailStoreType, username, password);
		JFrame frame = new JFrame("ss bot");
		chatbox = new JTextArea();
		chatbox.setBackground(new Color(255, 255, 60));
		frame.add(new JScrollPane(chatbox));
		chatbox.setEditable(false);
		frame.setSize(400, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				running = false;
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});

		Session session = openSession();

		Store store = session.getStore("pop3s");
		store.connect("smtp.gmail.com", acc, pass);

		while (running) {
			openMail(session, store);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}

		store.close();
	}
}
