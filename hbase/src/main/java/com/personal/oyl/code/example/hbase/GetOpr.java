package com.personal.oyl.code.example.hbase;

import java.io.IOException;

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
public class GetOpr 
{
    public static void main( String[] args ) throws IOException
    {
        PutOpr.main(args);
        
        Connection conn = null;
        Table table = null;
        
        try {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            table = conn.getTable(TableName.valueOf("testtable"));
            
            Get get = new Get(Bytes.toBytes("row-1"));
            get.addFamily(Bytes.toBytes("colfam1"));
            
            Result result = table.get(get);
            byte[] v = result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("q4"));
            Cell cell = result.getColumnLatestCell(Bytes.toBytes("colfam1"), Bytes.toBytes("q4"));
            
            System.out.println(Bytes.toString(v));
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
            System.out.println(cell);
            
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
