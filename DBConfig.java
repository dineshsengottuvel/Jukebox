package JukeBox_Project;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;



public class DBConfig 
{    
	Scanner sc=new Scanner(System.in);
	//=new JukeBox_DAO();
	Connection con =null;

	//method to establish connection
	public static Connection getConnection() throws SQLException
    {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","india");
        return con; 
    }

//method to add created playlist into database
public void addPlaylist(Playlist p) throws SQLException {
	Connection con = getConnection();
	PreparedStatement ps = con.prepareStatement("SET FOREIGN_KEY_CHECKS=0;");
	PreparedStatement pst = con.prepareStatement("insert into playlist(PlayList_Name,SongId,PodcastId,UserId,MediaType,Position) values(?,?,?,?,?,?)");
	//updating data into playlist table
	pst.setString(1, p.getPlayList_Name());
	pst.setLong(2, p.getSongId());
	pst.setLong(3, p.getPodcastId());
	pst.setLong(4, p.getUserId());
	pst.setString(5, p.getMediaType());
	pst.setInt(6, p.getPosition());
	ps.execute();
	pst.execute();
	
	pst.close();
	ps.close();
	con.close();
}

//fetching the url from the database from the particular playlist
public List<String> fetchPlaylistUrl(List<Playlist>playList, JukeBox_DAO j) throws SQLException{
	System.out.println("ENTER PLAYLIST NAME:");
	String name=sc.next();
	boolean check=j.checkPlaylistExistance(name,playList);
	Connection con = getConnection();
	//using linked list to so that it can be used to move forward and backward
	List <String> list=new LinkedList<String>();
	//running the playlist of the particular user and get urls of the particular playlist
	for(Playlist i : playList)
 	{
		if((i.getSongId()==0 || i.getMediaType().equalsIgnoreCase("podcast")) && i.getPlayList_Name().equalsIgnoreCase(name) ) {
			PreparedStatement pst = con.prepareStatement("select url from podcast where podcastID=?;");
			pst.setLong(1, i.getPodcastId());
			ResultSet rs=pst.executeQuery();
			rs.next();
			list.add(rs.getString(1));
     	}	
		else if((i.getPodcastId()==0 || i.getMediaType().equalsIgnoreCase("song")) && i.getPlayList_Name().equalsIgnoreCase(name)) {
			PreparedStatement ps = con.prepareStatement("select url from songs where songID=?;");
			ps.setLong(1, i.getSongId());
			ResultSet rss=ps.executeQuery();
			rss.next();
			list.add(rss.getString(1));
     	}	
		
	}
	//returning list of urls
	return list;
}

}











