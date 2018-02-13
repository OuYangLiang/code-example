package com.personal.oyl.code.example.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hello world!
 *
 */
public class CachePutOpr 
{
    public static void main( String[] args ) throws IOException
    {
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf, "testtable");
        table.setAutoFlushTo(false);
        System.out.println(table.getWriteBufferSize());
        
        Put put1 = new Put(Bytes.toBytes("row-2"));
        put1.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
        table.put(put1);
        
        Put put2 = new Put(Bytes.toBytes("row-3"));
        put2.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
        table.put(put2);
        
        Put put3 = new Put(Bytes.toBytes("row-4"));
        put3.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
        table.put(put3);
        
        table.flushCommits();
        table.close();
    }
}
