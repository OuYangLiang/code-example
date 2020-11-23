package org.comp.imp1;

import org.example.api.DataType;
import org.example.api.Processor;

/**
 * @author OuYang Liang
 * @since 2020-11-22
 */
public class ProcessorOfImpl1 implements Processor {
    @Override
    public boolean canHandle(DataType dataType) {
        return DataType.BI.equals(dataType);
    }

    @Override
    public void process(String string) {
        System.out.println(this.getClass().getSimpleName() + " is processing " + string);
    }
}
