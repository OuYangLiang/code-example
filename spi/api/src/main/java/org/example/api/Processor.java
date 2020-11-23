package org.example.api;

public interface Processor {
    boolean canHandle(DataType dataType);

    void process(String string);
}
