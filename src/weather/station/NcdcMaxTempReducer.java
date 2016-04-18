package weather.station;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NcdcMaxTempReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
	private NcdcStationMetadataParcer metadata;
	
	protected void setup(Context context) throws IOException,InterruptedException
	{
		metadata = new NcdcStationMetadataParcer();
		metadata.initialize(new File("station.txt"));
	}
	
	protected void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException, InterruptedException
	{ 
		
		String stationName = metadata.getStationName(key.toString());
		System.out.println("Station Name :" +stationName);
		System.out.println("Reducer Key :" +key.toString());
		int maxValue = Integer.MIN_VALUE;
		for (IntWritable val:values)
		{
			maxValue = Math.max(maxValue, val.get());
		}
		context.write(new Text(stationName),new IntWritable(maxValue));
	}
}
