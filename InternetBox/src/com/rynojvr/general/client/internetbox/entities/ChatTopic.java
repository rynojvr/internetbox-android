package com.rynojvr.general.client.internetbox.entities;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ChatTopic {
	private int id;
	public String title;
	private String content;
	@SerializedName("created_by")
	private String createdBy;
	@SerializedName("num_replies")
	private int numReplies;
	@SerializedName("is_nsfw")
	private boolean isNSFW;
	@SerializedName("is_locked")
	private boolean isLocked;
	@SerializedName("is_sticky")
	private boolean isSticky;
	@SerializedName("chat_replies")
	private List<ChatReply> replies;
	
	@Override
	public String toString() {
		return "The title is: " + title;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public int getNumReplies() {
		return numReplies;
	}
	
	public boolean isNSFW() {
		return isNSFW;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public boolean isSticky() {
		return isSticky;
	}
	
	public List<ChatReply> getReplies() {
		return replies;
	}
}
