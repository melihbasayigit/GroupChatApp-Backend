package com.melomanya.groupchatapp.util.validator;

public class NullValidator implements BaseValidator<Object>{

    private final Object[] items;

    public NullValidator(Object... items) {
        this.items = items;
    }

    @Override
    public Object[] getItems() {
        return items;
    }

    @Override
    public boolean validate() {
        for (Object item : items) {
            if (item == null) {
                return false;
            }
        }
        return true;
    }
}