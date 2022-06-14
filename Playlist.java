package JukeBox_Project;

import java.util.Scanner;

public class Playlist {

	//Attributes
	String playList_Name;
	long songId;
	long podcastId;
	long userId;
	String mediaType;
	int position;
	
	

	//constructor
	public Playlist(String playList_Name, long songId, long podcastId, long userId, String mediaType,
			int position) {
	
		this.playList_Name = playList_Name;
		this.songId = songId;
		this.podcastId = podcastId;
		this.userId = userId;
		this.mediaType = mediaType;
		this.position = position;

	}
	
	//getters and setters
	public String getPlayList_Name() {
		return playList_Name;
	}
	public void setPlayList_Name(String playList_Name) {
		this.playList_Name = playList_Name;
	}
	public long getSongId() {
		return songId;
	}
	public void setSongId(long songId) {
		this.songId = songId;
	}
	public long getPodcastId() {
		return podcastId;
	}
	public void setPodcastId(long podcastId) {
		this.podcastId = podcastId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	

	
}
