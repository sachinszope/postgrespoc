package com.gooru.beans;

public class Attributes {
	private String displayName;
	private String fullName;
	private String profilePic;
	
	private int collectionsCreated;
	private int resourceAdded;
	private int assessmentAdded;
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public int getCollectionsCreated() {
		return collectionsCreated;
	}

	public void setCollectionsCreated(int collectionsCreated) {
		this.collectionsCreated = collectionsCreated;
	}

	public int getResourceAdded() {
		return resourceAdded;
	}

	public void setResourceAdded(int resourceAdded) {
		this.resourceAdded = resourceAdded;
	}

	public int getAssessmentAdded() {
		return assessmentAdded;
	}

	public void setAssessmentAdded(int assessmentAdded) {
		this.assessmentAdded = assessmentAdded;
	}

	@Override
	public String toString() {
		return "Attributes [displayName=" + displayName + ", fullName=" + fullName + ", profilePic=" + profilePic
				+ ", collectionsCreated=" + collectionsCreated + ", resourceAdded=" + resourceAdded
				+ ", assessmentAdded=" + assessmentAdded + "]";
	}
	
	

}
