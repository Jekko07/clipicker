package com.clipicker.me.domain;

public class ChangeAnnouncement {
	private Announcement originalAnnouncement;
	private Announcement newAnnouncement;

	public ChangeAnnouncement(Announcement originalAnnouncement,
		Announcement newAnnouncement) {
		this.originalAnnouncement = originalAnnouncement;
		this.newAnnouncement = newAnnouncement;
	}

	public Announcement getOriginalAnnouncement() {
		return originalAnnouncement;
	}

	public void setOriginalAnnouncement(Announcement originalAnnouncement) {
		this.originalAnnouncement = originalAnnouncement;
	}

	public Announcement getNewAnnouncement() {
		return newAnnouncement;
	}

	public void setNewAnnouncement(Announcement newAnnouncement) {
		this.newAnnouncement = newAnnouncement;
	}
}
