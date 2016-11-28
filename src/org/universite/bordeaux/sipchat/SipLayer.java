package org.universite.bordeaux.sipchat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TooManyListenersException;

import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.PeerUnavailableException;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipFactory;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.TransportNotSupportedException;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

public class SipLayer implements SipListener {

	private MessageHandler messageHandler;

	private String username;

	private SipStack sipStack;

	private SipFactory sipFactory;

	private AddressFactory addressFactory;

	private HeaderFactory headerFactory;

	private MessageFactory messageFactory;

	private SipProvider sipProvider;

	/** Here we initialize the SIP stack. */
	public SipLayer(String username, String ip, int port) throws PeerUnavailableException,
			TransportNotSupportedException, InvalidArgumentException, ObjectInUseException, TooManyListenersException {
		setUsername(username);
		sipFactory = SipFactory.getInstance();

		Properties properties = new Properties();
		properties.setProperty("javax.sip.STACK_NAME", "SIPChat");
		properties.setProperty("javax.sip.IP_ADDRESS", ip);
		properties.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "32");
		properties.setProperty("gov.nist.javax.sip.SERVER_LOG", "textclient.txt");
		properties.setProperty("gov.nist.javax.sip.DEBUG_LOG", "textclientdebug.log");

		sipStack = sipFactory.createSipStack(properties);

		headerFactory = sipFactory.createHeaderFactory();
		addressFactory = sipFactory.createAddressFactory();
		messageFactory = sipFactory.createMessageFactory();

		ListeningPoint tcp = sipStack.createListeningPoint(port, "tcp");
		ListeningPoint udp = sipStack.createListeningPoint(port, "udp");
		sipProvider = sipStack.createSipProvider(tcp);
		sipProvider.addSipListener(this);
		sipProvider = sipStack.createSipProvider(udp);
		sipProvider.addSipListener(this);
	}

	/**
	 * This method uses the SIP stack to send a message.
	 */
	public void sendMessage(String to, String message) throws ParseException, InvalidArgumentException, SipException {

		// NOT IMPLEMENTED YET
		
		// Objectif : 
		// Construire une requête SIP avec les factory
		// Puis l'envoyer à l'aide de la commande : sipProvider.sendRequest(request);
		
	}

	/** This method is called by the SIP stack when a response arrives. */
	public void processResponse(ResponseEvent evt) {
		
		// NOT IMPLEMENTED YET
		
		// Objectif : 
		// Récupérer la réponse SIP
		// Et afficher 'Message envoyé' ou 'Echec de l'envoi' dans l'interface
		// En fonction de la valeur du code de retour récupéré
	}

	/**
	 * This method is called by the SIP stack when a new request arrives.
	 */
	public void processRequest(RequestEvent evt) {
		
		// NOT IMPLEMENTED YET
		
		// Objectif : 
		// Récupérer la requête et le contenu du message reçu
		// Et afficher dans l'interface de l'application
		
	}

	/**
	 * This method is called by the SIP stack when there's no answer to a
	 * message. Note that this is treated differently from an error message.
	 */
	public void processTimeout(TimeoutEvent evt) {
		messageHandler.processError("Previous message not sent: " + "timeout");
	}

	/**
	 * This method is called by the SIP stack when there's an asynchronous
	 * message transmission error.
	 */
	public void processIOException(IOExceptionEvent evt) {
		messageHandler.processError("Previous message not sent: " + "I/O Exception");
	}

	/**
	 * This method is called by the SIP stack when a dialog (session) ends.
	 */
	public void processDialogTerminated(DialogTerminatedEvent evt) {
	}

	/**
	 * This method is called by the SIP stack when a transaction ends.
	 */
	public void processTransactionTerminated(TransactionTerminatedEvent evt) {
	}

	public String getHost() {
		return sipStack.getIPAddress();
	}

	public int getPort() {
		return sipProvider.getListeningPoints()[0].getPort();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String newUsername) {
		username = newUsername;
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

}
