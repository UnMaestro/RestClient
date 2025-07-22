package com.odfin.domain;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public class Message implements Serializable{

	private User owner;
	private byte[] payload;
	private long created;
	
	public Message(User owner, String rawPayload, long created) {
		this.owner = owner;
		this.payload = rawPayload.getBytes();
		this.created = created;
	}
	
	public Message(User owner, byte[] payload, long created) {
		this.owner = owner;
		this.payload = payload;
		this.created = created;
	}
	
	public byte[] getRawPayload() {
		return payload;
	}
	public String getPayloadAsString() {
		return SerializationUtils.deserialize(payload);
	}
	public User getOwner() {
		return owner;
	}
	public long getCreated() {
		return created;
	}
}
