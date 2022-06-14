package JukeBox_Project;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;





public class JukeBox_DAO {
	Scanner sc=new Scanner(System.in);
	DBConfig d=new DBConfig();
	Connection con=null;
	List<Integer>highestPosition = new ArrayList<Integer>();
	 int position=0;  
	
	 //Adding a new user 
	public void addUser() throws SQLException{
		System.out.println("NAME      :"+"\n"+"MOBILENo  :"+"\n"+"EMAIL ID  :"+"\n"+"PASSWORD  :");
		String name=sc.next();
		long mobileNo=sc.nextLong();
		String emailId=sc.next();
		String password=sc.next();
		System.out.println("================================");
		
		//inserting user data into user table
		con = DBConfig.getConnection();
		PreparedStatement pst = con.prepareStatement("insert into User(User_Name,MobileNo,EmailId,password) values (?,?,?,?)");
		pst.setString(1, name);
		pst.setLong(2, mobileNo);
		pst.setString(3,emailId);
		pst.setString(4, password);	
		pst.execute();
		System.out.println("Account created successfully!!!"+"\n"+"================================");
		
		//fetching user id from database for displaying
		PreparedStatement ps = con.prepareStatement("select userid from User where emailid=? and mobileno=?;");
		ps.setString(1, emailId);
		ps.setLong(2, mobileNo);
		ResultSet rs = ps.executeQuery();
		rs.next();
		long userId=rs.getLong(1);//user id from database
		
		pst.close();
		ps.close();
		rs.close();
		con.close();
		//printing new account details
		System.out.println("ACCOUNT DETAILS:"+"\n"+"================================"+"\n"+"USER ID   :"+userId+"\n"+"NAME      :"+name+"\n"+"EMAIL ID  :"+emailId+"\n"+"MOBILE NO :"+mobileNo+"\n");
	}
	//checks weather the login email/mobile no. is valid
	public long checkCredenials() throws Exception {
		System.out.println("========================================"+"\n"+"ENTER (EMAIL/MOBILE.NO) AND PASSWORD :"+"\n"+"========================================");
		String id=sc.next();
		String password=sc.next();
		long num=0;
		//taking password input as string and converting using parsing to check it is a mobile number or email
		try {
		    long value  = Long.parseLong(id);
		    id ="select userId from user where MobileNo="+value+" and password='"+password+"'";
		} catch (NumberFormatException e) {
		    id="select userId from user where EmailId='"+id+"'"+" and password='"+password+"'";
		}
		//establishing connection for fetching email/mobile no to check credentials
		con = DBConfig.getConnection();
		PreparedStatement ps = con.prepareStatement(id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			num=rs.getLong(1);
		    }
		//throwing exception if email/mobile number is invalid
		if(num==0) {throw new Exception("Invalid username/password.Please re-enter correct details.Thank you!!!");}
		else {System.out.println("==========================================================="+"\n"+"WELCOME TO JUKEBOX!!!"+"\n");}
		
		ps.close();
		rs.close();
		con.close();
		return num;
		}

	//initialising the songs from the song table of database
	public List<Songs> initialiseSongs() throws SQLException,Exception {
		List<Songs>slist = new ArrayList<Songs>();
		con = DBConfig.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from  songview;");
		ResultSet r = ps.executeQuery();
			while(r.next()) {
			    slist.add(new Songs(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getInt(6),r.getString(7),r.getString(8),r.getLong(9),r.getString(10)));
			}
			
			ps.close();
			con.close();
		return slist;
		}
	
	//initialising  the podcast from the podcast table of database
	public List<Podcast> initialisePodcast() throws SQLException {	 
			List<Podcast>plist = new ArrayList<Podcast>();
			con = DBConfig.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from  podcastview;");
			ResultSet rsp = ps.executeQuery();
			while(rsp.next()) {
		    plist.add(new Podcast(rsp.getString(1),rsp.getString(2),rsp.getInt(3),rsp.getString(4),rsp.getString(5),rsp.getInt(6),rsp.getString(7),rsp.getString(8),rsp.getLong(9),rsp.getString(10)));
			}
			ps.close();
			con.close();
			 return plist;
		}
	//initialising  the playlists of the particular user  from the playlist table of database
	//also calculating the highest position
	public List<Playlist> initialisePlaylist(long userId) throws SQLException {	 
		List<Playlist>playlist = new ArrayList<Playlist>();
		con = DBConfig.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from  playlist where userId=?;");
		ps.setLong(1, userId);
		ResultSet rsp = ps.executeQuery();
		while(rsp.next()) {
	    playlist.add(new Playlist(rsp.getString(2),rsp.getLong(3),rsp.getLong(4),rsp.getLong(5),rsp.getString(6),rsp.getInt(7)));
	    highestPosition.add(rsp.getInt(7));
		}
		if(highestPosition.isEmpty()==false) {
			position=Collections.max(highestPosition);
		}
		ps.close();
		rsp.close();
		con.close();
		return playlist;
	}
	//to search songs and podcast by user choice  
	                                 //podcast list       //song list        //boolean parameter if used for creating playlist
	public String selectByPreference(List<Podcast> plist, List<Songs> slist, boolean once) {
		Collections.sort(slist,(o1,o2)-> o1.getSongName().compareTo(o2.getSongName()));//sorting in alphabetical order
		String mediatype="";// useful for the playlist creation 
	    boolean exit=true; //exit , once will be false if this method is use for creating playlist
	    while(exit!=false) {//exit, once will be true if it is used for searching 
	    System.out.println("============================================================================================="+"\n"+"ENTER YOUR PREFERENCE:"+"\n"+"1.ARTIST"+"\n"+"2.GENRE"+"\n"+"3.ALBUM"+"\n"+"4.SONG NAME"+"\n"+"5.ALPHABETICAL ORDER"+"\n"+"6.ALPHEBETICAL SEARCH"+"\n"+"7.SEARCH BY LANGUAGE"+"\n"+"8.SEARCH PODCAST BY PUBLISHED DATE"+"\n"+"9.SEARCH PODCAST BY CELEBRITY"+"\n"+"10.BACK TO MENU"+"\n"+"==============================================");
	    int preference=sc.nextInt();
	    //switch case to select choice of user to display by catagory
	    switch(preference) {
	    case 1://search by Artist name
	    	System.out.println("ENTER ARTIST NAME :");
	    	sc.nextLine();
	    	String artist=sc.nextLine();
	    	List<Songs> alist=slist.stream().filter(r->r.getArtistName().equalsIgnoreCase(artist)).collect(Collectors.toList());
	    	System.out.println("======================================================================================================================================================================");
        	System.out.println("ARTIST               SONG ID         SONG NAME                      GENRE                ALBUM                DURATION             RATING               PUBLISHED DATE");
        	System.out.println("======================================================================================================================================================================");
        	alist.forEach(i-> System.out.format("%-20s %-15s %-30s %-20s %-20s %-20s %-20s %-10s\n",i.getArtistName(),i.getSongId(),i.getSongName(),i.getGenre(),i.getAlbumName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    	System.out.println("======================================================================================================================================================================");
	    	exit=once;
	    	mediatype="Song";
	    	break;
	    case 2://search by Genre
	    	System.out.println("ENTER GENRE :");
	    	sc.nextLine();
	    	String genre=sc.nextLine();
	    	List<Songs> glist=slist.stream().filter(r->r.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());//using stream to collect data
	    	System.out.println("=====================================================================================================================================================================");
        	System.out.println("GENRE                SONG ID              SONG NAME                      ALBUM           ARTIST               DURATION             RATING               PUBLISHED DATE");
        	System.out.println("=====================================================================================================================================================================");
	    	glist.forEach(i-> System.out.format("%-20s %-20s %-30s %-15s %-20s %-20s %-20s %-10s\n",i.getGenre(),i.getSongId(),i.getSongName(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    	System.out.println("=====================================================================================================================================================================");
	    	exit=once;
	    	mediatype="Song";
	    	break;
	    case 3://search by Album
	    	System.out.println("ENTER ALBUM NAME :");
	    	sc.nextLine();
	    	String album=sc.nextLine();
	    	List<Songs> blist=slist.stream().filter(r->r.getAlbumName().equalsIgnoreCase(album)).collect(Collectors.toList());
	    	System.out.println("========================================================================================================================================================");
        	System.out.println("ALBUM           SONG ID    SONG NAME                      ARTIST          GENRE                DURATION             RATING               PUBLISHED DATE ");
        	System.out.println("========================================================================================================================================================");
	    	blist.forEach(i-> System.out.format("%-15s %-10s %-30s %-15s %-20s %-20s %-20s %-10s\n",i.getAlbumName(),i.getSongId(),i.getSongName(),i.getArtistName(),i.getGenre(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    	System.out.println("========================================================================================================================================================");
	    	exit=once;
	    	mediatype="Song";
	    	break;
	    case 4://search by Songname using predicate
	    	 System.out.println("ENTER SONG NAME :");
	    	 sc.nextLine();
	    	 String name=sc.nextLine();
	    	 Predicate<Songs> filtered =p->(p.getSongName().trim().equalsIgnoreCase(name.trim()));
	    	 System.out.println("=============================================================================================================================================================");
	         System.out.println("SONG NAME                      SONG ID              GENRE                ALBUM                ARTIST               DURATION        RATING     PUBLISHED DATE ");
	         System.out.println("=============================================================================================================================================================");
	    	 filter(slist,filtered).forEach(i->System.out.format("%-30s %-20s %-20s %-20s %-20s %-15s %-10s %-15s\n",i.getSongName(),i.getSongId(),i.getGenre(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    	 System.out.println("=============================================================================================================================================================");
	    	 exit=once;
	    	 mediatype="Song";
	    	 break;
	    case 5: //sort by Alphabetical order
	    	 System.out.println("========================================================================================================================================================");
	         System.out.println("SONG NAME                      SONG ID    GENRE                ALBUM                ARTIST               DURATION   RATING               PUBLISHED DATE");
	         System.out.println("========================================================================================================================================================");
	    	slist.forEach(i-> System.out.format("%-30s %-10s %-20s %-20s %-20s %-10s %-20s %-20s\n",i.getSongName(),i.getSongId(),i.getGenre(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    	mediatype="Song";
	    	exit=once;
	        break;
	    case 6: //search by Alphabet
	    	 System.out.println("ENTER FIRST LETTERS OF SONG:");
	    	 String letter=sc.next();
	    	 Predicate<Songs> filtered2 = p->(p.getSongName().toLowerCase().startsWith(letter));
	    	 System.out.println("========================================================================================================================================================");
	        	System.out.println("SONG NAME                      SONG ID    GENRE                ALBUM                ARTIST               DURATION   RATING               PUBLISHED DATE");
	        	System.out.println("========================================================================================================================================================");
	    	 filter(slist,filtered2).forEach(i->System.out.format("%-30s %-10s %-20s %-20s %-20s %-10s %-20s %-10s\n",i.getSongName(),i.getSongId(),i.getGenre(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));
	    	 System.out.println("========================================================================================================================================================");
	    	 exit=once;
	    	 mediatype="Song";
	    	 break;
	    case 7://search by Language
	    	System.out.println("ENTER LANGUAGE:");
	    	sc.nextLine();
	    	String language=sc.nextLine();
	    	List<Songs> llist=slist.stream().filter(r->r.getLanguage().equalsIgnoreCase(language)).collect(Collectors.toList());
	    	System.out.println("=========================================================================================================================================================");
        	System.out.println("LANGUAGE             SONG ID              SONG NAME                      GENRE                ALBUM                ARTIST     DURATION             RATING");
        	System.out.println("=========================================================================================================================================================");
	    	llist.forEach(i-> System.out.format("%-20s %-20s %-30s %-20s %-20s %-10s %-20s %-10s\n",i.getLanguage(),i.getSongId(),i.getSongName(),i.getGenre(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished()));
	    	System.out.println("=========================================================================================================================================================");
	    	exit=once;
	    	mediatype="Song";
	    	break;
	    case 8: //Search podcast by published date
	    	System.out.println("ENTER START AND END DATE(YYYY/MM/DD) :");
	    	String sd = sc.next();
			String ed = sc.next();
			LocalDate start = LocalDate.parse(sd);
			LocalDate end = LocalDate.parse(ed); 
			List<Podcast> publishedlist=plist.stream().filter(r->LocalDate.parse(r.getPublished_Date()).isAfter(start)&& LocalDate.parse(r.getPublished_Date()).isBefore(end) || LocalDate.parse(r.getPublished_Date()).isEqual(start)).collect(Collectors.toList());
			System.out.println("======================================================================================================================================================================");
        	System.out.println("PUBLISHED DATE       PODCAST ID           PODCAST NAME                   LANGUAGE             GENRE                NO OF EPISODES       CELEBRITY            DURATION");
        	System.out.println("======================================================================================================================================================================");
			publishedlist.forEach(i-> System.out.format("%-20s %-20s %-30s %-20s %-20s %-20s %-20s %-10s\n",i.getPublished_Date(),i.getPodcastId(),i.getPodcastName(),i.getLanguage(),i.getGenre(),i.getNoOfEpisodes(),i.getArtistName(),i.getTotal_duration(),i.getRating()));
			System.out.println("=====================================================================================================================================================================");
			exit=once;
			mediatype="Podcast";
			break;
	     case 9: // Search podcast by Artist
	    	System.out.println("ENTER PODCAST CELEBRITY NAME :");
	    	sc.nextLine();
	    	String pArtist=sc.nextLine();
	    	List<Podcast> palist=plist.stream().filter(r->r.getArtistName().equalsIgnoreCase(pArtist)).collect(Collectors.toList());
	    	System.out.println("===========================================================================================================================================================================");
	    	System.out.println("CELEBRITY            PODCAST ID           PODCAST NAME                   GENRE                NO OF EPISODES       DURATION             RATING               PUBLISHED DATE");
	    	System.out.println("===========================================================================================================================================================================");
	    	palist.forEach(i-> System.out.format("%-20s %-20s %-30s %-20s %-20s %-20s %-20s %-10s\n",i.getArtistName(),i.getPodcastId(),i.getPodcastName(),i.getGenre(),i.getNoOfEpisodes(),i.getTotal_duration(),i.getRating(),i.getPublished_Date(),i.getLanguage()));
	    	System.out.println("===========================================================================================================================================================================");
	    	exit=once;
	    	mediatype="Podcast";
	    	break;  
	     case 10:
	    	 exit=false;
	    	 break;
	    }
	    }
		return mediatype;
		
	}
 //using predicate to filter
	 public static List<Songs> filter(List<Songs> list, Predicate<Songs> pre) {
	        List<Songs> preList = new ArrayList<>();
	        for (Songs l : list) {
	            if (pre.test(l))
	            {
	                preList.add(l);
	            }
	        }
	        return preList;
	    }

 //create playlist method 
	 public List<Playlist> createPlaylist(List<Songs> slist, List<Podcast> plist,List<Playlist> playlist, long userId) throws SQLException {
		 List<Playlist>IDlist = new LinkedList<Playlist>();
		 System.out.println("SELECT YOUR CHOICE: "+"\n"+"===================================="+"\n"+"1.CREATE NEW PLAYLIST"+"\n"+"2.ENTER SONG INTO EXISTING PLAYLIST"+"\n"+"====================================");
		 String playlistName="";
		 boolean once=false;
		 String status;
		 long id=0;
		 long pid=0;
		 String mediaType="";
		
		 int choice=sc.nextInt();
		
		 switch(choice) {
		 //case 1 to create new playlist
		 case 1: System.out.println("ENTER PLAYLIST NAME :");
		 		 sc.nextLine();
			     playlistName=sc.nextLine();
			     position=0;
		 case 2://case 2 to enter into existing playlist
			 if(choice==2) {
			 System.out.println("ENTER PLAYLIST NAME :");
	 		 sc.nextLine();
	 		 playlistName=sc.nextLine();
	 		 //calling checkPlaylistExistance method to check weather the entered playlist exists or not
	 		 //recursion to call the method again if condition fails
	 		 if(checkPlaylistExistance(playlistName,playlist)==false) {
	 			 createPlaylist(slist,plist,playlist,userId);}
			 }
	 		 boolean exit=true;
			 while(exit!=false) {//adding song /podcast into playlist loop
			 System.out.println("1.SEARCH AND SELECT(SONG/PODCAST) BY PREFERENCE(ALBUMS/ARTIST/GENRE)"+"\n"+"2.SELECT FROM LIST OF ALL SONGS"+"\n"+"3.SELECT FROM LIST OF ALL PODCASTS");
			 int preference=sc.nextInt();
			 //nested switch to select song / podcast
			 switch(preference) {
			 case 1://nested case 1 to SEARCH AND SELECT(SONG/PODCAST) BY PREFERENCE(ALBUMS/ARTIST/GENRE) 
				 mediaType=selectByPreference(plist, slist, once);
				 System.out.println("ENTER ID:");
				 id=sc.nextLong();
				 //if the selected by preference (ALBUMS/ARTIST/GENRE) it checks the media type(podcast /song )
				 if(mediaType.equalsIgnoreCase("podcast")) {
					 pid=id;
					 id=0;
				 }//this method checks if the song ID exists or not
				 if(checkCreatedAddedSongValidity(slist, plist, mediaType, pid, id)==true) {
					 position=position+1;
					 //creating a list of objects for the new playlist
					 IDlist.add(new Playlist(playlistName,id,pid, userId, mediaType, position));
					 //updating database playlist table
                     d.addPlaylist(new Playlist(playlistName,id,pid, userId, mediaType, position));
				 }
				 //checks weather we want to enter the loop again to add a song/podcast into the playlist
				 System.out.println("DO YOU WISH TO CONTINUE(Y/N):");
				 status=sc.next();
				 if (status.equalsIgnoreCase("n")) {
				 exit=false; 
				 }
			     break;
			 case 2://nested case 2 to enter song id into playlist from list of all songs
				System.out.println("=============================================================================================================================================================================");
		        System.out.println("SONG NAME            SONG ID                        GENRE      ALBUM                ARTIST               DURATION             RATING     PUBLISHED DATE         LANGUAGE");
 				System.out.println("===============================================================================================================================================================================");
 				slist.forEach(i-> System.out.format("%-20s %-30s %-10s %-20s %-20s %-20s %-10s %-20s %-10s\n",i.getSongId(),i.getSongName(),i.getGenre(),i.getAlbumName(),i.getArtistName(),i.getDuration(),i.getRating(),i.getPublished(),i.getLanguage()));		        	
				 System.out.println("==============================================================================================================================================================================");
				 System.out.println("ENTER SONG ID:");
				 id=sc.nextLong();
				 //this method checks if the song ID exists or not
				 if(checkCreatedAddedSongValidity(slist, plist, "song", pid, id)==true) {
				 position=position+1;
				 //creating a list of objects for the new playlist
				 IDlist.add(new Playlist(playlistName,id,0, userId, "Song", position));
				 //adding this playlist song into database
				 d.addPlaylist(new Playlist(playlistName,id,0, userId, "Song", position));
				 }
				 //checks weather we want to enter the loop again to add a song/podcast into the playlist
				 System.out.println("DO YOU WISH TO CONTINUE(Y/N):");
				 status=sc.next();
				 if (status.equalsIgnoreCase("n")) {
				 exit=false; 
				 }
				 break;
			 case 3:
				 System.out.println("========================================================================================================================================================================================================");
		        	System.out.println("PODCAST NAME         PODCAST ID                     GENRE                NO OF EPISODES       CELEBRITY            DURATION             RATING               PUBLISHED DATE                 LANGUAGE");
 				 System.out.println("=========================================================================================================================================================================================================");
		        		
				 plist.forEach(i-> System.out.format("%-20s %-30s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n",i.getPodcastId(),i.getPodcastName(),i.getGenre(),i.getNoOfEpisodes(),i.getArtistName(),i.getTotal_duration(),i.getRating(),i.getPublished_Date(),i.getLanguage()));
				 System.out.println("=========================================================================================================================================================================================================");
				 System.out.println("ENter PODCAST ID:");
				 id=sc.nextLong();
				 //this method checks if the song ID exists or not
				 if(checkCreatedAddedSongValidity(slist, plist, "podcast", pid, id)==true) {
				 position=position+1;
				//creating a list of objects for the new playlist
				 IDlist.add(new Playlist(playlistName,0,id, userId, "Podcast", position));
				 //adding this playlist song into database by calling this method
				 d.addPlaylist(new Playlist(playlistName,0,id, userId, "Podcast", position));
				 }
				 //checks weather we want to enter the loop again to add a song/podcast into the playlist
				 System.out.println("DO YOU WISH TO CONTINUE(Y/N):");
				 status=sc.next();
				 if (status.equalsIgnoreCase("n")) {
				 exit=false; 
				 }
				 break;
		    }
		 }
	   }
		return IDlist;		  
	 }

	 //check playlist existance method to check the entered playlist exists or not
Boolean checkPlaylistExistance(String playlistName, List<Playlist> playlist) {
	List<Playlist> playNamelist=playlist.stream().filter(r->r.getPlayList_Name().equalsIgnoreCase(playlistName)).collect(Collectors.toList());
	//if the the entered playlist name is not  there it displays playlist does not exist
	if(playNamelist.isEmpty()) {
		System.out.println("Playlist dosen't exist!!!"+"\n"+"==========================");
		return false;
	}
	else {
		return true;
	}
}

//this method checks if the added song id or podcast id is present or not
private boolean checkCreatedAddedSongValidity( List<Songs> slist, List<Podcast> plist, String mediatype, long id, long pid) {
	boolean playValidity=true;
	
	if(mediatype.equalsIgnoreCase("song")) {
		List<Songs> llist=slist.stream().filter(r->r.getSongId()==id ||r.getSongId()==pid).collect(Collectors.toList());
		if(llist.isEmpty()) {
			System.out.println("SongID dosen't exist!!!");
			playValidity= false;
		}
	}
	else if(mediatype.equalsIgnoreCase("Podcast")) {
		List<Podcast> palist=plist.stream().filter(r->r.getPodcastId()==pid || r.getPodcastId()==id).collect(Collectors.toList());
		if(palist.isEmpty()) {
			System.out.println("PodcastID dosen't exist!!!");
			playValidity= false;
		}
	}
	else {playValidity= true;
		}
	return playValidity;
	
	}


}

	
	
	

