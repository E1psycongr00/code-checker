package org.lhg.codechecker.tool.message.count;

import org.lhg.codechecker.tool.Store;
import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldMessageGenerator implements MessageGenerator<Field[]> {

    private static final String INFO_FORMAT = "fields: %s, count: %d.\n";
    private static final String MESSAGE = "fields count must be %d but %d.\n\n";

    @Override
    public String generateMessage(Store<Field[]> store, int limitCount) {
        String packageName = store.getPackageName();
        Field[] fields = store.getValue();
        String fieldsInfo = String.format(INFO_FORMAT,
                packageName + " # " + Arrays.deepToString(fields),
                fields.length);
        String message = String.format(MESSAGE, limitCount, fields.length);
        return fieldsInfo + message;
    }
}
