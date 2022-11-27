package com.cleancode.tool.checker;

import com.cleancode.tool.Store;
import com.cleancode.tool.message.count.FieldMessageGenerator;
import com.cleancode.tool.message.count.MessageGenerator;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class FieldChecker implements Checker {

    private static final String ERROR_INVALID_INIT = "Cannot initialize to 0 or negative number.";

    private final MessageGenerator<Field[]> messageGenerator;
    private final int limitFields;

    public FieldChecker(int limitFields) {
        validateLimitFields(limitFields);
        this.limitFields = limitFields;
        this.messageGenerator = new FieldMessageGenerator();
    }

    @Override
    public boolean check(List<Store<Object>> scanned, StringBuilder buffer) {
        List<Store<Field[]>> fieldsCollect = scanned.stream()
                .map(object -> Store.of(object.getPackageName(), (Field[]) object.getValue()))
                .collect(Collectors.toList());
        boolean flag = true;
        for (Store<Field[]> fieldsStore : fieldsCollect) {
            Field[] fields = fieldsStore.getValue();
            if (fields.length > limitFields) {
                flag = false;
                buffer.append(messageGenerator.generateMessage(fieldsStore, limitFields));
            }
        }
        return flag;
    }

    void validateLimitFields(int limitFields) {
        if (limitFields <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_INIT);
        }
    }
}
