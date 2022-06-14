package JukeBox_Project;


public class Songs {
	//Attributes
	long songId;
	String songName;
	String genre;   
	String albumName;     
	String artistName;
	String duration;     
	int rating;      
	String published;
	String language;
	String songUrl;

	//constructor
	public Songs(String songName, String genre, String albumName, String artistName,String duration, int rating, String published, String language,long songId,String songUrl) {
		this.songId=songId;
		this.songName = songName;
		this.genre = genre;
		this.albumName = albumName;
		this.artistName = artistName;
		this.duration = duration;
		this.rating = rating;
		this.published = published;
		this.language = language;
		this.songUrl= songUrl;
	}
	
	//getters and setters
	public long getSongId() {
		return songId;
	}
	public void setSongId(long songId) {
		this.songId = songId;
	}
	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSongUrl() {
		return songUrl;
	}
	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}
	
	
	
}
