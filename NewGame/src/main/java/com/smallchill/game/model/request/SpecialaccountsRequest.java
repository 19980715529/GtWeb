package com.smallchill.game.model.request;

public class SpecialaccountsRequest  {
	private String OldID ;
	private String NewID;
	public SpecialaccountsRequest() {
	}

	public String getOldID() {
		return OldID;
	}

	public void setOldID(String oldID) {
		OldID = oldID;
	}

	public String getNewID() {
		return NewID;
	}

	public void setNewID(String newID) {
		NewID = newID;
	}

	@Override
	public String toString() {
		return "SpecialaccountsRequest{" +
				"OldID='" + OldID + '\'' +
				", NewID='" + NewID + '\'' +
				'}';
	}
}