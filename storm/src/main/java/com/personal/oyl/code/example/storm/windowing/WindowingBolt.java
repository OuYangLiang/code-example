package com.personal.oyl.code.example.storm.windowing;

import java.util.List;

import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.windowing.TupleWindow;

public class WindowingBolt extends BaseWindowedBolt {

    private static final long serialVersionUID = -7733106749066960262L;

    @Override
    public void execute(TupleWindow inputWindow) {
        List<Tuple> tuples = inputWindow.get();
        
        if (null != tuples) {
            StringBuilder sb = new StringBuilder(100);
            sb.append("window contains: ");
            for (Tuple tuple : tuples) {
                sb.append("[").append(tuple.getInteger(0)).append("] ");
            }
            System.out.println(sb.toString());
        }
        
    }

}
