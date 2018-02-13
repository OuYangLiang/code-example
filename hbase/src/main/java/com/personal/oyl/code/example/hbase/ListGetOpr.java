package com.personal.oyl.code.example.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hello world!
 *
 */
public class ListGetOpr 
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
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-1"));
            table.put(put);
            
            put = new Put(Bytes.toBytes("row-2"));
            put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"), Bytes.toBytes("val-2"));
            table.put(put);
            
            
            List<Get> list = new ArrayList<>(2);
            Get get = new Get(Bytes.toBytes("row-1"));
            get.addFamily(Bytes.toBytes("colfam1"));
            list.add(get);
            
            get = new Get(Bytes.toBytes("row-2"));
            get.addFamily(Bytes.toBytes("colfam1"));
            list.add(get);
            
            Result[] results = table.get(list);
            
            for (Result result : results) {
                byte[] v = result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"));
                Cell cell = result.getColumnLatestCell(Bytes.toBytes("colfam1"), Bytes.toBytes("q1"));
                
                System.out.println(Bytes.toString(v));
                System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println(cell);
            }
            
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
