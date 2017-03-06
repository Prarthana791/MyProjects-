import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Errorcode_JOB {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, Text>{

   // private final static IntWritable one = new IntWritable(1);
    private Text URL = new Text();
private Text error = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
		if(!value.toString().trim().startsWith("#"))
		{
		String[] splittext= value.toString().split(" ");
		String urltext=splittext[4];
		String errorcode=splittext[10];
		if(errorcode.equals("404") && !urltext.equals("/"))
		{
		URL.set(urltext);
		error.set(errorcode);
        context.write(URL, error);	
		}
		}

    }
  }

  public static class IntSumReducer
       extends Reducer<Text,Text,Text,Text> {
    
	private Text key_output = new Text();
    private Text value_output = new Text();

	public void reduce(Text key, Text values,Context context
                       ) throws IOException, InterruptedException {
						 
		key_output.set(key);
		value_output.set(values);
		context.write(key_output,value_output);
				
	   }}

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Max Error Page count");
    job.setJarByClass(Errorcode_JOB.class);
    job.setMapperClass(TokenizerMapper.class);
   job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
	job.setMapOutputKeyClass(Text.class);
job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
