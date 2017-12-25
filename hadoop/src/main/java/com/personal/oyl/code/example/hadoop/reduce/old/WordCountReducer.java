package com.personal.oyl.code.example.hadoop.reduce.old;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class WordCountReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterator<IntWritable> vals, OutputCollector<Text, IntWritable> output, Reporter arg3)
            throws IOException {
        int n = 0;
        while(vals.hasNext()) {
            n+= vals.next().get();
        }
        
        output.collect(key, new IntWritable(n));
    }

    /*@Override
    protected void reduce(Text key, Iterable<IntWritable> vals,
            Reducer<Text, IntWritable, Text, Text>.Context context) throws IOException, InterruptedException {
        super.reduce(key, vals, context);
        
        int n = 0;
        Iterator<IntWritable> it = vals.iterator();
        while(it.hasNext()) {
            n+= it.next().get();
        }
        
        context.write(key, new Text(Integer.toString(n)));
    }*/

}
