package com.personal.oyl.code.example.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * PutOpr
 *
 */
public class DelOpr 
{
    public static void main( String[] args ) throws IOException
    {
        Connection conn = null;
        Table table = null;
        
        try {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            table = conn.getTable(TableName.valueOf("testtable"));
            
            Put put = new Put(Bytes.toBytes("row-1"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q4"), Bytes.toBytes("val-4"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"), Bytes.toBytes("val-3"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-2"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
            table.put(put);
            
            Delete delete = new Delete(Bytes.toBytes("row-1"));
            delete.addColumns(Bytes.toBytes("colfam1"), Bytes.toBytes("q4"));
            
            table.delete(delete);
            
            new Delete(Bytes.toBytes("row-1"));
            delete.addColumns(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"));
            
            assert table.checkAndDelete(Bytes.toBytes("row-1"), Bytes.toBytes("colfam1"), Bytes.toBytes("q3"), Bytes.toBytes("val-3"), delete);
            
            new Delete(Bytes.toBytes("row-1"));
            delete.addColumns(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"));
            
            assert !table.checkAndDelete(Bytes.toBytes("row-1"), Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-3"), delete);
            
        } finally {
            if (null != table) {
                table.close();
            }
            if (null != conn) {
                conn.close();
            }
        }
    }
}
