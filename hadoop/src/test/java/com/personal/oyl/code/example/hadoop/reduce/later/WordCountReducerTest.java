package com.personal.oyl.code.example.hadoop.reduce.later;

import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;

import com.personal.oyl.code.example.hadoop.reduce.WordCountReducer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WordCountReducerTest extends TestCase {
    public WordCountReducerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(WordCountReducerTest.class);
    }

    public void test() {
        new ReduceDriver<Text, IntWritable, Text, IntWritable>()
        .withReducer(new WordCountReducer())
        .withInput(new Text("abcdefg"), Arrays.asList(new IntWritable(1), new IntWritable(1)))
        .withOutput(new Text("abcdefg"), new IntWritable(2))
        .runTest();
        
    }
}
