package com.personal.oyl.code.example.storm.trident.state.transactional;

import java.util.LinkedList;
import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseQueryFunction;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class QueryWordCountDB extends BaseQueryFunction<WordCountDB, Long> {

    @Override
    public List<Long> batchRetrieve(WordCountDB state, List<TridentTuple> args) {
        List<Long> list = new LinkedList<>();
        for (TridentTuple tuple : args) {
            list.add(state.getCount(tuple.getString(0)));
        }
        return list;
    }

    @Override
    public void execute(TridentTuple tuple, Long result, TridentCollector collector) {
        collector.emit(new Values(result));
    }

}
