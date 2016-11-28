package org.universite.bordeaux.sipchat;

public class MessageHandler {

	private Interface sipGui;
	
	public MessageHandler(Interface sipGui) {
		this.sipGui = sipGui;
	}

	public void processMessage(String sender, String message) {
		this.sipGui.appendMessage("FROM " +
                sender + ": " + message + "\n");
	}

	public void processError(String errorMessage) {
		this.sipGui.appendMessage("ERROR: " +
                errorMessage + "\n");
	}

	public void processInfo(String infoMessage) {
		this.sipGui.appendMessage(infoMessage + "\n");
	}

}
