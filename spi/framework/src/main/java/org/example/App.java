package org.example;

import org.example.api.Data;
import org.example.api.DataType;
import org.example.api.Processor;

import java.util.ServiceLoader;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Data d1 = new Data(DataType.BI, "message from BI");
        Data d2 = new Data(DataType.vangogh, "message from vangogh");
        Data d3 = new Data(DataType.UC, "message from user center");

        handle(d1);
        handle(d2);
        handle(d3);
    }

    static void handle(Data data) {
        ServiceLoader<Processor> processors = ServiceLoader.load(Processor.class);
        for (Processor processor : processors) {
            if (processor.canHandle(data.getType())) {
                processor.process(data.getContent());
                return;
            }
        }

        throw new RuntimeException("No processor found for " + data.getType());
    }
}
