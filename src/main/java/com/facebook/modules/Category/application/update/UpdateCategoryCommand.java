package com.facebook.modules.Category.application.update;

public record UpdateCategoryCommand(
        String id,
        String name,
        boolean isActive
) {
    public static UpdateCategoryCommand with(
            final String aID,
            final String nameField,
            final boolean isActive
    ) {
        return new UpdateCategoryCommand(
                aID,
                nameField,
                isActive);
    }
}
