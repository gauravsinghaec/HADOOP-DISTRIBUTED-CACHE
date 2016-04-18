package weather.station;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class NcdcMaxTempDriver extends Configured implements Tool {

	static class JobBuilder
	{
		public static Job parseInputAndOutput(Tool tool, Configuration conf,
				String[] arg) throws IOException  
		{
				if (arg.length != 2) {
				printUsage(tool, "<input> <output>");
				return null;
				}
				Job job = new Job(conf);
				job.setJarByClass(tool.getClass());
				FileInputFormat.addInputPath(job, new Path(arg[0]));
				FileOutputFormat.setOutputPath(job, new Path(arg[1]));
				return job;
		}
		public static void printUsage(Tool tool, String extraArgsUsage) 
		{
				System.err.printf("Usage: %s [genericOptions] %s\n\n",
				tool.getClass().getSimpleName(), extraArgsUsage);
				GenericOptionsParser.printGenericCommandUsage(System.err);
		}
	}
	public int run(String arg[]) throws ClassNotFoundException, IOException, InterruptedException
	{
		Job job = JobBuilder.parseInputAndOutput(this,getConf(),arg);
		if (job == null)
		{
			return -1;
		}
		
		job.setMapperClass(NcdcMaxTempMapper.class);
		job.setCombinerClass(NcdcMaxTempCombiner.class);
		job.setReducerClass(NcdcMaxTempReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		return job.waitForCompletion(true)?0:1;
		
	}
	public static void main(String arg[]) throws Exception
	{
		int exitCode = ToolRunner.run(new NcdcMaxTempDriver(),arg);
		System.exit(exitCode);
	}

}
