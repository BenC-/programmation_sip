package org.universite.bordeaux.sipchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface extends JFrame
{
	private static final long serialVersionUID = 1L;

	private SipLayer sipLayer;
    
	private JTextField sendMessages = new JTextField();
	private JTextArea receivedMessages = new JTextArea();
	private JTextField toAddress = new JTextField();
	private JTextField fromAddress = new JTextField();
	
    public Interface(SipLayer sip)
    {
        super();
        sipLayer = sip;
        initWindow();
        String from = "sip:" + sip.getUsername() + "@" + sip.getHost() + ":" + sip.getPort();
        this.fromAddress.setText(from);
    }
    
    // Public methods
    
    public void display(){
    	this.setVisible(true);
    }
    
    public void appendMessage(String message){
    	this.receivedMessages.append(message);
    }
    
    // Private methods
    
    private void initWindow() {
        
        JLabel fromLbl = new JLabel();
        JLabel receivedLbl = new JLabel();
        JScrollPane receivedScrollPane = new JScrollPane(); 
        JButton sendBtn = new JButton();
        JLabel sendLbl = new JLabel();
        JLabel toLbl = new JLabel();
    	
        getContentPane().setLayout(null);

        setTitle("TextClient");
        addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        receivedLbl.setText("Received Messages:");
        receivedLbl.setAlignmentY(0.0F);
        receivedLbl.setPreferredSize(new java.awt.Dimension(25, 100));
        getContentPane().add(receivedLbl);
        receivedLbl.setBounds(5, 0, 136, 20);

        sendLbl.setText("Send Message:");
        getContentPane().add(sendLbl);
        sendLbl.setBounds(5, 150, 150, 20);

        getContentPane().add(sendMessages);
        sendMessages.setBounds(5, 170, 270, 20);

        receivedMessages.setAlignmentX(0.0F);
        receivedMessages.setEditable(false);
        receivedMessages.setLineWrap(true);
        receivedMessages.setWrapStyleWord(true);
        receivedScrollPane.setViewportView(receivedMessages);
        receivedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getContentPane().add(receivedScrollPane);
        receivedScrollPane.setBounds(5, 20, 270, 130);

        fromLbl.setText("From:");
        getContentPane().add(fromLbl);
        fromLbl.setBounds(5, 200, 35, 15);

        getContentPane().add(fromAddress);
        fromAddress.setBounds(40, 200, 235, 20);
        fromAddress.setEditable(false);

        toLbl.setText("To:");
        getContentPane().add(toLbl);
        toLbl.setBounds(5, 225, 35, 15);

        getContentPane().add(toAddress);
        toAddress.setBounds(40, 225, 235, 21);

        sendBtn.setText("Send");
        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        getContentPane().add(sendBtn);
        sendBtn.setBounds(200, 255, 75, 25);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-288)/2, (screenSize.height-310)/2, 288, 320);
    }

    private void sendBtnActionPerformed(ActionEvent evt) {

        try
        {
            String to = this.toAddress.getText();
            String message = this.sendMessages.getText();
            sipLayer.sendMessage(to, message);
        } catch (Exception e)
        {
            e.printStackTrace();
            this.receivedMessages.append("ERROR sending message: " + e + "\n");
        }
        			
    }

}
