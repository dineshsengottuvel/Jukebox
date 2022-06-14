package JukeBox_Project;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.sound.sampled.*;
public class Controller {
                                  //list of urls
   public static void musicPlayer(List<String> playSong) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	  Scanner sc=new Scanner(System.in);
    	  //using iterator to iterate the list of urls
    	  ListIterator<String> li = playSong.listIterator(0);
    	  String url=li.next();
    	 //using while loop to maintain continuity or go back to main menu
    	  boolean loop=false;
    	  File file=new File(url);
    	  System.out.println("1.Play"+"\n"+"2.Pause"+"\n"+"3.Resume"+"\n"+"4.Next"+"\n"+"5.Previous"+"\n"+"6.Forward"+"\n"+"7.Backward"+"\n"+"8.Stop"+"\n");
    	  boolean enter=false;
    	  while(enter==false)
    	  {	  
    	  //using AudioInputStream to get the input stream of the wav file
    	  AudioInputStream aStream=AudioSystem.getAudioInputStream(file);
    	  //Clip interface loads prior to playback, instead of being streamed in real time
    	  Clip clip=AudioSystem.getClip();
    	  clip.open(aStream);
    	  String response;
    	 //second  while loop to maintain continuity 
    	  boolean exit=false;
    	  while(exit==false)
    	  {	  
    		  if(loop==true){
    			  response="play";
    			  loop=false;		 
    		  }
    		  else {
    			  response=sc.next();
    		  }
    		  //switch case for playing song operations
    		  switch(response)
    		  {   //play song
    		      case("play"):clip.start();
    		   	      break;
    		   	  //pause song
    		      case("pause"):clip.stop();
    		  	      break;
    		  	  //next song
    		      case("next"):
    			      if(li.hasNext()) {
    				  file=new File(li.next());
    				  loop=true;
    	    	      }
    	    	      else {
    	    		  System.out.println("End of playlist!!");
    	    		  enter=true;
    	    	      }
    		         clip.close();
    		         exit=true;
    			     break;
    			 //previous song
    		     case("previous"):
    		    	  if(li.hasPrevious()) {
    	    		  li.previous();
    	    		  file=new File(li.previous());
    	    		  loop=true;
    	    	      }
    	    	      else {
    	    	      System.out.println("No previous song in playlist!!");
    	    	      enter=true;
    	    	      }
    			      clip.stop();
    			      exit=true;
    			      break;
    			 //stop and exit
    		     case ("stop"):
    			      enter=true;
    			      exit=true;
    		      	  clip.close();
    		     	  break;
    		    //forward 10 seconds
    		    case("forward"):
    		     	  clip.setMicrosecondPosition(clip.getMicrosecondPosition()+10000000);
    		    	  break;
    		    //backward 10 seconds
    		    case("backward"):
    		     	  clip.setMicrosecondPosition(clip.getMicrosecondPosition()-10000000);
    		   
    		    	  break;
    		    //resume song
    		    case("resume"):
    			      clip.start();
    		          break;
    		  }
    	  }
    	  }

}
   

}