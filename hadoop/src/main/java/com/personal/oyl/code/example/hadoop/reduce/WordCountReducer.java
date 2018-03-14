package com.personal.oyl.code.example.hadoop.reduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> vals,
            Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        
        int n = 0;
        Iterator<IntWritable> it = vals.iterator();
        while(it.hasNext()) {
            n+= it.next().get();
        }
        
        context.write(key, new IntWritable(n));
    }

}
