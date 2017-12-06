package com.personal.oyl.code.example.storm.jdbc.spout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBQuery {
    public static List<Record> query(long lastId) throws SQLException {
        List<Record> list = new ArrayList<>(10);
        Connection conn = null;
        try {
            conn = ConnectionManager.getConn();
            
            PreparedStatement ps = conn.prepareStatement("SELECT CATEGORY_OID, CATEGORY_DESC FROM CATEGORY WHERE CATEGORY_OID > ? ORDER BY CATEGORY_OID LIMIT 10");
            ps.setLong(1, lastId);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Long oid = rs.getLong(1);
                String name = rs.getString(2);
                
                list.add(new Record(oid, name));
            }
            
        } finally {
            try {
                ConnectionManager.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return list;
    }
}
