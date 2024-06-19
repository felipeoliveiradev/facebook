package com.facebook.modules.Category.application.create;

public record CreateCategoryCommand(
        String name,
        boolean isActive
) {
    public static CreateCategoryCommand with(
            final String nameField,
            final boolean isActive
    ) {
        return new CreateCategoryCommand(
                nameField,
                isActive);
    }
}
