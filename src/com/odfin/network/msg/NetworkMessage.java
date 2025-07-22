package com.odfin.network.msg;

import java.io.Serializable;

import com.odfin.domain.Message;
import com.odfin.domain.User;

public class NetworkMessage implements Serializable{

	private MessageType messageType;
	private User networkSender;
	private User networkRecipient;
	private Message message;
	
	public NetworkMessage(MessageType messageType, User sender, User recipient, Message message) {
		this.messageType = messageType;
        this.networkSender = sender;
        this.networkRecipient = recipient;
	}

	public MessageType getMessageType() {return messageType;}
    public User getSender() {return networkSender;}
    public User getRecipient() {return networkRecipient;}
    public Message getMessage() {return message;}
	
}
