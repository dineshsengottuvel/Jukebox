package JukeBox_Project;

public class Podcast {
	//Attributes
	long podcastId;
	String podcastName;
	String genre; 
	int noOfEpisodes ;
	String artistName;
	String total_duration;
	int rating;      
	String Published_Date; 
	String language;
	String podcastUrl;

	//constructor
	public Podcast(String podcastName, String genre, int noOfEpisodes, String artistName, String total_duration,
			int rating, String published_Date, String language,long podcastId,String podcastUrl) {
		this.podcastId=podcastId;
		this.podcastName = podcastName;
		this.genre = genre;
		this.noOfEpisodes = noOfEpisodes;
		this.artistName = artistName;
		this.total_duration = total_duration;
		this.rating = rating;
		this.Published_Date = published_Date;
		this.language = language;
		this.podcastUrl=podcastUrl;
	}
	
	//getters and setters
	public String getPodcastName() {
		return podcastName;
	}
	public void setPodcastName(String podcastName) {
		this.podcastName = podcastName;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getNoOfEpisodes() {
		return noOfEpisodes;
	}
	public void setNoOfEpisodes(int noOfEpisodes) {
		this.noOfEpisodes = noOfEpisodes;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getTotal_duration() {
		return total_duration;
	}
	public void setTotal_duration(String total_duration) {
		this.total_duration = total_duration;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getPublished_Date() {
		return Published_Date;
	}
	public void setPublished_Date(String published_Date) {
		Published_Date = published_Date;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		language = language;
	}
	public long getPodcastId() {
		return podcastId;
	}
	public void setPodcastId(long podcastId) {
		this.podcastId = podcastId;
	}
	public String getPodcastUrl() {
		return podcastUrl;
	}
	public void setPodcastUrl(String podcastUrl) {
		this.podcastUrl = podcastUrl;
	}
	

}
