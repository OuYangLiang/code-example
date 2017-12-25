package com.personal.oyl.code.example.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.personal.oyl.code.example.hadoop.mapper.later.WordCountMapper;
import com.personal.oyl.code.example.hadoop.reduce.later.WordCountReducer;

public class App2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = new Job();
        job.setJarByClass(App.class);
        job.setJobName("word count");
        
        FileInputFormat.addInputPath(job, new Path("/Users/ouyang/test"));
        FileOutputFormat.setOutputPath(job, new Path("/Users/ouyang/test/out"));
        
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        System.out.println(job.waitForCompletion(true));
    }
}
