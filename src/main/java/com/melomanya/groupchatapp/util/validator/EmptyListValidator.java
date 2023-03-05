package com.melomanya.groupchatapp.util.validator;

import org.springframework.lang.NonNull;
import java.util.List;

public class EmptyListValidator implements BaseValidator<List<Object>>{

    @NonNull
    private final List<Object>[] items;

    @SafeVarargs
    public EmptyListValidator(@NonNull List<Object>... items) {
        this.items = items;
    }

    @Override
    public List<Object>[] getItems() {
        return items;
    }

    @Override
    public boolean validate() {
        for (List<Object> item : items) {
            if (item.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}