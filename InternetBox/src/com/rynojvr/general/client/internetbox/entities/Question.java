package com.rynojvr.general.client.internetbox.entities;
import com.google.gson.annotations.SerializedName;
import com.rynojvr.general.client.internetbox.entities.enums.Pony;
import com.rynojvr.general.client.internetbox.entities.enums.Cast;


public class Question {
	private int id;
	@SerializedName("created_by")
	private String createdBy;
	@SerializedName("to_whom")
	private Cast toWhom;
	private String content;
	private int rating;
	@SerializedName("best_pony")
	private Pony bestPony;
//	private String bestPony;
	@SerializedName("is_answered")
	private boolean isAnswered;
	
	public Question() {
		this.bestPony = Pony.RainbowDash;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCreatedBy() {	
		return createdBy;
	}
	
	public Cast getToWhom() {
		return toWhom;
	}
	
	public String getContent() {
		return content;
	}
	
	public int getRating() {
		return rating;
	}
	
	public Pony getBestPony() {
		return bestPony;
	}
	
	public boolean isAnswered() {
		return isAnswered;
	}
	
	@Override
	public String toString() {
		return "Question[" + id + "]";
	}
}
