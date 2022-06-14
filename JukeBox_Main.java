package JukeBox_Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class JukeBox_Main {

	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		
		JukeBox_DAO j=new JukeBox_DAO();
		DBConfig d=new DBConfig();
		Controller c =new Controller();
		
		List<Songs> slist=j.initialiseSongs();
	    List<Podcast> plist=j.initialisePodcast();
	    List<Playlist>playlist = new ArrayList<Playlist>();
	    List<Playlist>IdList=new ArrayList<Playlist>();
	   
		System.out.println("==============================================================="+"\n"+"JukeBox Welcomes you-Experience the new world of Music!!!"+"\n"+"===============================================================");
		
		System.out.println("1.SIGN UP:"+"\n"+"2.LOGIN  :"+"\n"+"===============================================================");
		int input=sc.nextInt();
		//switch case to sign up or login
		switch(input) {
		  case 1:j.addUser();//case 1 sign up
		  case 2://case 2 checks weather the entered login is valid or not
			  long validity=j.checkCredenials();
			  //initialising playlists of the particular user from database
		      playlist =j.initialisePlaylist(validity);
		 
		  //enters this nested switch if the entered login is valid
			boolean exit=true;
		    while(exit!=false) {
		  if (validity!=0) {
			  System.out.println("Enter YOUR CHOICE:"+"\n"+"==========================================================="+"\n"+"1.CREATE PLAYLIST"+"\n"+"2.SEARCH BY CATAGORIES(ARTIST/GENRE/ALBUM/SONG NAME)"+"\n"+"3.DISPLAY ALL SONGS"+"\n"+"4.DISPLAY ALL PODCASTS"+"\n"+"5.LISTEN TO PLAYLIST SONGS"+"\n"+"6.EXIT"+"\n"+"===========================================================");
			  int choice=sc.nextInt();
			  switch(choice) {
			        case 1://1.creating playlist
		  				IdList= j.createPlaylist(slist,plist,playlist,validity);
		  				playlist.addAll(IdList);
		  				
			        	break;
			        case 2://2.select movie name by Album/Artist
       	        	  j.selectByPreference(plist, slist, true);
			        	break;
			        case 3://Display all songs
			        	System.out.println("========================================================================================================================================================");
			        	System.out.println("SONG NAME                      SONG ID    GENRE                ALBUM                ARTIST               DURATION        RATING     PUBLISHED DATE    ");
	    				System.out.println("========================================================================================================================================================");
	    				slist.forEach(i-> System.out.format("%-30s %-10s %-20s %-20s %-20s %-15s %-10s %-10s\n",i.getSongName(),i.getSongId(),i.getGenre(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    				System.out.println("========================================================================================================================================================");
	    				
	    				break;
			        case 4://4.Display all podcasts
			        	System.out.println("=================================================================================================================================================================");
			        	System.out.println("PODCAST NAME                   PODCAST ID           GENRE                NO OF EPISODES       CELEBRITY            DURATION             RATING     PUBLISHED DATE    ");
	    				System.out.println("=================================================================================================================================================================");
			        	plist.forEach(i-> System.out.format("%-30s %-20s %-20s %-20s %-20s %-20s %-10s %-20s\n",i.getPodcastName(),i.getPodcastId(),i.getGenre(),i.getNoOfEpisodes(),i.getArtistName(),i.getTotal_duration(),i.getRating(),i.getPublished_Date(),i.getLanguage()));
			        	System.out.println("=================================================================================================================================================================");
			        	break;
			        case 5://5.play song
			        	List <String>playSong=d.fetchPlaylistUrl(playlist, j);
			        	if(playSong.isEmpty()==false) {
			        	c.musicPlayer(playSong);}
			        	break;
			        case 6://6.exit
			        	System.out.println("THANK YOU!!!");
			        	exit=false;
			        	break;
			  }
		  }
	    }
		}
		  
	}

}
