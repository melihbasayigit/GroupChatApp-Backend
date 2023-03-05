package com.melomanya.groupchatapp.util.validator;

import org.springframework.lang.NonNull;

public class EmptyStringValidator implements BaseValidator<String>{

    @NonNull
    private final String[] items;

    public EmptyStringValidator(@NonNull String... items) {
        this.items = items;
    }

    @Override
    public String[] getItems() {
        return items;
    }

    @Override
    public boolean validate() {
        for (String item : items) {
            if (item.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
