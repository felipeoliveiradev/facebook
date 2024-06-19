package com.facebook.modules.Post.domain;

import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.ValidationField;
import com.facebook.modules.Flup.system.validation.Validator;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

import java.util.List;

public class PostValidator extends Validator {
    private final Post Post;

    public PostValidator(final Post aPost, final ValidationHandler aHandler) {
        super(aHandler);
        this.Post = aPost;
    }

    @Override
    public void validate() {
        this.nameValidation();
        this.descriptionValidation();
        this.categoriesValidation();
    }


    public void nameValidation() {
        try {
            this.validationHandler().appendList(new ValidationField<String>("name", this.Post.getName()).Null().Name().Max(255).Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }

    public void categoriesValidation() {
        try {
            this.validationHandler().appendList(new ValidationField<List<CategoryID>>("categories", this.Post.getCategories()).Null().Empty().validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void descriptionValidation() {
        try {
            this.validationHandler().appendList(new ValidationField<String>("description", this.Post.getDescription()).Null().Max(255).Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }

}
