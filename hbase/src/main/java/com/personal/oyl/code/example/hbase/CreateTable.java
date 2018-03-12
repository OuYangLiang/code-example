package com.personal.oyl.code.example.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

public class CreateTable {
    public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        Connection conn = null;
        
        try {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            Admin admin = conn.getAdmin();
            
            HTableDescriptor desc = new HTableDescriptor(TableName.valueOf("test1"));
            HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes("colfam1"));
            HColumnDescriptor coldef2 = new HColumnDescriptor(Bytes.toBytes("colfam2"));
            desc.addFamily(coldef);
            desc.addFamily(coldef2);
            admin.createTable(desc);
            System.out.println(admin.isTableAvailable(TableName.valueOf("test1")));
        } finally {
            if (null != conn) {
                conn.close();
            }
        }
    }
}
