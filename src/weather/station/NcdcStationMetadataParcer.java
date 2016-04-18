package weather.station;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NcdcStationMetadataParcer {
	private String stationMetadata; 
	private String stationName;
	private HashMap<String,String> hm = new HashMap<String,String>();
	public String getStationName(String key)
	{
	    // Get a set of the entries
//	    Set set = hm.entrySet();
	    // Get an iterator
//	    Iterator itr = set.iterator();
	    // Get the key,value for an elements in the HashMap
//	    while(itr.hasNext()) {

	    Set<Map.Entry<String,String>> set = hm.entrySet();
	    for (Map.Entry<String,String> me : set){
//	         Map.Entry<String,String> me = (Map.Entry)itr.next();
//	         System.out.println("Key : "+ me.getKey());
//	         System.out.println("Value : "+me.getValue());
	         if (me.getKey().compareTo(key)==0) 
	         {
//	        	stationName = me.getValue();
	        	 return me.getValue();
//	        	break;
	         }
	    }		
		return stationName;
	}
	public void initialize(File fs) throws IOException
	{
		FileReader fr = new FileReader(fs);
		BufferedReader buff = new BufferedReader(fr);
		while((stationMetadata = buff.readLine()) != null)
		{
			String s[] =stationMetadata.split(" ");
			String key = s[0];
			String value = s[1];
				if (key !=" " && value != " "){
					hm.put(key, value);
				}			
		}		
	}	
}
