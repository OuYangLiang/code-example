package com.personal.oyl.code.example.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import com.personal.oyl.code.example.hadoop.mapper.old.WordCountMapper;
import com.personal.oyl.code.example.hadoop.reduce.old.WordCountReducer;

public class App 
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException
    {
        runOld();
    }
    
    public static void runOld() throws IOException {
        JobConf conf = new JobConf(App.class);
        conf.setJobName("wordcount");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(WordCountMapper.class);
        conf.setCombinerClass(WordCountReducer.class);
        conf.setReducerClass(WordCountReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.addInputPath(conf, new Path("/Users/ouyang/test"));
        FileOutputFormat.setOutputPath(conf, new Path("/Users/ouyang/test/out"));

        JobClient.runJob(conf);
    }
    
}
