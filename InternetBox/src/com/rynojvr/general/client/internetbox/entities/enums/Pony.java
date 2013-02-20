package com.rynojvr.general.client.internetbox.entities.enums;
public enum Pony {
	Fluttershy("Fluttershy"),
	RainbowDash("Rainbow Dash"),
	PinkiePie("Pinkie Pie"),
	TwilightSparkle("Twilight Sparkle"),
	Rarity("Rarity"),
	Applejack("Applejack");
	
	private String displayName;
	
	private Pony(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}
