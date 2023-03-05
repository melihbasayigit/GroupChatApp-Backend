package com.melomanya.groupchatapp.util.validator;

import java.lang.reflect.Array;

public interface BaseValidator<T> {

    T[] getItems();

    boolean validate();

}
