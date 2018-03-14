package com.personal.oyl.code.example.hadoop.mapper.later;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;

import com.personal.oyl.code.example.hadoop.mapper.WordCountMapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WordCountMapperTest extends TestCase {
    public WordCountMapperTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(WordCountMapperTest.class);
    }

    public void test() {
        Text value = new Text("hello nice to meet you haha haha");
        
        new MapDriver<LongWritable, Text, Text, IntWritable>()
        .withMapper(new WordCountMapper())
        .withInputValue(value)
        .withOutput(new Text("hello"), new IntWritable(1))
        .withOutput(new Text("nice"), new IntWritable(1))
        .withOutput(new Text("to"), new IntWritable(1))
        .withOutput(new Text("meet"), new IntWritable(1))
        .withOutput(new Text("you"), new IntWritable(1))
        .withOutput(new Text("haha"), new IntWritable(1))
        .withOutput(new Text("haha"), new IntWritable(1))
        .runTest();
    }
    
}
