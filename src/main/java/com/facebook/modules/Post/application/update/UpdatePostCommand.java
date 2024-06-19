package com.facebook.modules.Post.application.update;

import java.util.List;

public record UpdatePostCommand(
        String id,
        String name,
        String description,
        List<String> categories,
        boolean isActive
) {
    public static UpdatePostCommand with(
            final String aID,
            final String nameField,
            final String descriptionField,
            final List<String> categoriesField,
            final boolean isActive
    ) {
        return new UpdatePostCommand(
                aID,
                nameField,
                descriptionField,
                categoriesField,
                isActive);
    }
}
