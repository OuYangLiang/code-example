package com.personal.oyl.code.example.storm.trident.state.nontransactional;

import java.util.LinkedList;
import java.util.List;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.BaseQueryFunction;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class QueryWordCountDB extends BaseQueryFunction<WordCountDB, Integer> {

    @Override
    public List<Integer> batchRetrieve(WordCountDB state, List<TridentTuple> args) {
        List<Integer> list = new LinkedList<>();
        for (TridentTuple tuple : args) {
            list.add(state.getCount(tuple.getString(0)));
        }
        return list;
    }

    @Override
    public void execute(TridentTuple tuple, Integer result, TridentCollector collector) {
        collector.emit(new Values(result));
    }

}
