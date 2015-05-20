package br.com.imobiliaria.modelo;

import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.imobiliaria.util.ContextUtil;
 
public class Email {
 
	public void enviaEmailApartamentosFavoritos(List<ApartamentosFavoritos> favoritosSelecionados) {
		
		final String username = "gustaveduardo1@gmail.com";
		final String password = "gustaveduardo23";
		
		
		
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		session.setDebug(true);
		try {
			Transport trans = session.getTransport();
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(username));
			
			message.setSubject("Seiko Imóveis: corretor "+FuncionarioLogado.getFuncionarioLogado().getNome()+" "+FuncionarioLogado.getFuncionarioLogado().getSobrenome() + " selecionou apartamentos que possam ser de seu interesse!");
			
			 MimeMultipart multipart = new MimeMultipart("related");
			 
			 //comeco do email
			 
//			 for (ApartamentosFavoritos a : listaApartamentos) {
		        // first part  (the html)
		        BodyPart messageBodyPart = new MimeBodyPart();
		        
		        String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
		        
		        
		        messageBodyPart.setContent(htmlText, "text/html");

		        multipart.addBodyPart(messageBodyPart);
		        // second part (the image)
//		        messageBodyPart = new MimeBodyPart();
//		        DataSource fds = new FileDataSource
//		          ("C:\\Temp\\seiko 2013.png");
//		        messageBodyPart.setDataHandler(new DataHandler(fds));
//		        messageBodyPart.setHeader("Content-ID","<image>");
		        // add it
//		        multipart.addBodyPart(messageBodyPart);
//			 }
		        //fim do email
//			 	BodyPart messageBodyPart = new MimeBodyPart();
//		        String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
//		        messageBodyPart.setContent(htmlText, "text/html");
		        // add it
//		        multipart.addBodyPart(messageBodyPart);
		        
		        // put everything together
		        message.setContent(multipart);
			
			// apache commons para htmlmail
			
			trans.connect();
	        trans.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        trans.close();

	        ContextUtil.getAnyMessage("E-mail enviado com sucesso!");
	        
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
