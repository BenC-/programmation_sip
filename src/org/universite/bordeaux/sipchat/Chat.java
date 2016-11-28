package org.universite.bordeaux.sipchat;

import java.net.InetAddress;

public class Chat
{
	
	private Chat(){}
	
    public static void main(String[] args)
    {
        if(args.length != 2) {
            printUsage();
            System.exit(-1);            
        }
        
		try
        {
			// Extract parameters
		    String username = args[0];
		    int port = Integer.parseInt(args[1]);
		    String ip = InetAddress.getLocalHost().getHostAddress();

		    // Instantiate sip layer and GUI
			SipLayer sipLayer = new SipLayer(username, ip, port);
			Interface gui = new Interface(sipLayer);
			MessageHandler messageHandler = new MessageHandler(gui);
			sipLayer.setMessageHandler(messageHandler);
			
			// Display GUI
		    gui.display();
        } catch (Exception e)
        {
            System.out.println("Problem initializing the SIP stack : " + e);
            System.exit(-1);
        }
    }

    private static void printUsage()
    {
        System.out.println("Syntax:");
        System.out.println("  java -jar textclient.jar <username> <port>");
        System.out.println("where <username> is the nickname of this user");
        System.out.println("and <port> is the port number to use. Usually 5060 if not used by another process.");
        System.out.println("Example:");
        System.out.println("  java -jar textclient.jar snoopy71 5061");
    }

    
}
