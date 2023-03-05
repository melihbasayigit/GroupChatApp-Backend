package com.melomanya.groupchatapp.util.response;

import com.melomanya.groupchatapp.model.response.BaseResponse;
import com.melomanya.groupchatapp.model.response.ErrorResponse;
import com.melomanya.groupchatapp.model.response.ErrorType;
import com.melomanya.groupchatapp.model.response.SuccessResponse;
import com.melomanya.groupchatapp.util.validator.BaseValidator;
import com.melomanya.groupchatapp.util.validator.EmptyListValidator;
import com.melomanya.groupchatapp.util.validator.EmptyStringValidator;
import com.melomanya.groupchatapp.util.validator.NullValidator;

public class ValidatorResponse {

    private final BaseValidator<Object>[] items;

    public ValidatorResponse(BaseValidator... items) {
        this.items = items;
    }

    public BaseResponse getResponse() {
        for (BaseValidator<Object> item : items) {
            if (!item.validate()) {
                try {
                    return handleError(item);
                } catch (Exception e) {
                    //logger eklenecek
                    return new ErrorResponse(null, ErrorType.WRONG_ENUM_TYPE, true);
                }
            }
        }
        return handleSuccess();
    }

    public boolean isSuccess() {
        for (BaseValidator<Object> item : items) {
            if (!item.validate()) {
                return false;
            }
        }
        return true;
    }

    private SuccessResponse handleSuccess() {
        return new SuccessResponse("Success!");
    }

    public ErrorResponse handleError(BaseValidator<Object> validator) throws Exception {
        if (validator.getClass() == NullValidator.class) {
            return new ErrorResponse(validator.getItems(), ErrorType.NULL_VARIABLE, false);
        }
        if (validator.getClass() == EmptyStringValidator.class) {
            return new ErrorResponse(validator.getItems(), ErrorType.EMPTY_STRING_VARIABLE, false);
        }
        if (validator.getClass() == EmptyListValidator.class) {
            return new ErrorResponse(validator.getItems(), ErrorType.EMPTY_LIST_VARIABLE, false);
        }
        throw new Exception("Added new validator!!!");
    }

}
