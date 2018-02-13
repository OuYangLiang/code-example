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
public class CheckAndPutOpr 
{
    public static void main( String[] args ) throws IOException
    {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("testtable"));
        
        Put put = new Put(Bytes.toBytes("row-1"));
        put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
        table.put(put);
        
        Put put2 = new Put(Bytes.toBytes("row-1"));
        put2.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-2"));
        boolean rlt = table.checkAndPut(Bytes.toBytes("row-1"), Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"), put2);
        
        assert rlt;
        
        Put put3 = new Put(Bytes.toBytes("row-1"));
        put3.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-3"));
        rlt = table.checkAndPut(Bytes.toBytes("row-1"), Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"), put3);
        
        assert !rlt;
        
        Put put4 = new Put(Bytes.toBytes("row-1"));
        put4.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-1"));
        rlt = table.checkAndPut(Bytes.toBytes("row-1"), Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), null, put4);
        
        assert rlt;
        
        table.close();
    }
}
