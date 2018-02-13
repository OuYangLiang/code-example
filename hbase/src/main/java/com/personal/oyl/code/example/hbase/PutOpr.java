package com.personal.oyl.code.example.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hello world!
 *
 */
public class PutOpr 
{
    public static void main( String[] args ) throws IOException
    {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("testtable"));
        
        Put put = new Put(Bytes.toBytes("row-1"));
        put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q4"), Bytes.toBytes("val-4"));
        
        table.put(put);
        table.close();
    }
}
