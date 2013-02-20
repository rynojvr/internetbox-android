package com.rynojvr.general.client.internetbox.entities;

import com.google.gson.annotations.SerializedName;


public class ChatReply {

	private int id;
	@SerializedName("created_by")
	private String createdBy;
	private String content;
	@SerializedName("is_vip_reply")
	private boolean isVipReply;
	@SerializedName("is_top_reply")
	private boolean isTopReply;
	@SerializedName("isModReply")
	private boolean isModReply;
	@SerializedName("is_content_editable")
	private boolean isContentEditable;
	
	public int getId() {
		return id;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public String getContent() {
		return content;
	}
	
	public boolean isVipReply() {
		return isVipReply;
	}
	
	public boolean isTopReply() {
		return isTopReply;
	}
	
	public boolean isModReply() {
		return isModReply;
	}
	
	public boolean isContentEditable() {
		return isContentEditable;
	}
}
