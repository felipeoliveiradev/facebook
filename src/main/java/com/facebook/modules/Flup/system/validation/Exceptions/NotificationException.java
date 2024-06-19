package com.facebook.modules.Flup.system.validation.Exceptions;

import com.facebook.modules.Flup.system.validation.handlers.Notification;

public class NotificationException extends DomainException {
    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}
