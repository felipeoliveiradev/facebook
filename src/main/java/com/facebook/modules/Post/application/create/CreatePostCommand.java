package com.facebook.modules.Post.application.create;

import java.util.List;

public record CreatePostCommand(
        String name,
        String description,
        List<String> categories,
        boolean isActive

) {
    public static CreatePostCommand with(
            final String nameField,
            final String descriptionField,
            final List<String> categoriesField,
            final boolean isActive
    ) {
        return new CreatePostCommand(
                nameField,
                descriptionField,
                categoriesField,
                isActive);
    }
}
