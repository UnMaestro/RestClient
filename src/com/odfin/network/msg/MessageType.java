package com.odfin.network.msg;

public enum MessageType{
	REQUEST_CHAT, //Zum Server
	SEND_CHAT, //Zum Client
	
	STATUS_CHANGED,
	UPDATE_USERLIST,
	
	LOGIN,
	LOGIN_SUCCESS,
	LOGIN_FAILURE,
	
	
	DIRECT,
	JOIN,
	LEAVE,
	BROADCAST
}
