package com.facebook.modules.Category.domain;

import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.ValidationField;
import com.facebook.modules.Flup.system.validation.Validator;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

import java.util.List;

public class CategoryValidator extends Validator {
    private final Category Category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.Category = aCategory;
    }

    @Override
    public void validate() {
        this.nameValidation();
    }


    public void nameValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("name", this.Category.getName()).Null().Name().Max(255).Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }

}
