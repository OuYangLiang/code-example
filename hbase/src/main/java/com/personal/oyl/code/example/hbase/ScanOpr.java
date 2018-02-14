package com.personal.oyl.code.example.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * PutOpr
 *
 */
public class ScanOpr 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        Connection conn = null;
        Table table = null;
        
        try {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            table = conn.getTable(TableName.valueOf("testtable"));
            
            Put put = new Put(Bytes.toBytes("row-1"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-1q"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"), Bytes.toBytes("val-1qq"));
            table.put(put);
            
            put = new Put(Bytes.toBytes("row-2"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-2"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-2q"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"), Bytes.toBytes("val-2qq"));
            table.put(put);
            
            put = new Put(Bytes.toBytes("row-3"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-3"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-3q"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"), Bytes.toBytes("val-3qq"));
            table.put(put);
            
            put = new Put(Bytes.toBytes("row-4"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-4"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"), Bytes.toBytes("val-4q"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"), Bytes.toBytes("val-4qq"));
            table.put(put);
            
            Scan scan = new Scan();
            scan.addFamily(Bytes.toBytes("colfam1"));
            scan.setCaching(2);
            scan.setBatch(1);
            
            ResultScanner scanner = table.getScanner(scan);
            
            for (Result result : scanner) {
                System.out.println(Bytes.toString(result.getRow()));
                System.out.println("-----------------------------");
                System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"))));
                System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("q2"))));
                System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("q3"))));
                System.out.println();
                
            }
            
            scanner.close();
            
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
