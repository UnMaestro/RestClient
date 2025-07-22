package com.odfin.domain;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public class Message {
	private String author;
	private String text;
	private long created;

	public Message() {}

	public Message(String author, String text) {
		this.author = author;
		this.text = text;
		this.created = System.currentTimeMillis();
	}

	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }

	public String getText() { return text; }
	public void setText(String text) { this.text = text; }

	public long getCreated() { return created; }
	public void setCreated(long created) { this.created = created; }
}