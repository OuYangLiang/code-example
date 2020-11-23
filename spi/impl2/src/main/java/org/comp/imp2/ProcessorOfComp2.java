package org.comp.imp2;

import org.example.api.DataType;
import org.example.api.Processor;

/**
 * @author OuYang Liang
 * @since 2020-11-22
 */
public class ProcessorOfComp2 implements Processor {
    @Override
    public boolean canHandle(DataType dataType) {
        return DataType.vangogh.equals(dataType);
    }

    @Override
    public void process(String string) {
        System.out.println(this.getClass().getSimpleName() + " is processing " + string);
    }
}
