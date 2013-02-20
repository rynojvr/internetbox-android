package com.rynojvr.android.internetbox.models.old;

import com.rynojvr.android.internetbox.models.enums.Pony;
import com.rynojvr.android.internetbox.models.enums.StaffMembers;

@Deprecated
public class Question {

	private String name;
	private String email;
	private StaffMembers toWhom;
	private String question;
	private Pony bestPony;
}
