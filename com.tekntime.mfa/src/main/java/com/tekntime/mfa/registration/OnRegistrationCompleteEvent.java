package com.tekntime.mfa.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.tekntime.mfa.model.UserLogin;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserLogin user;

    public OnRegistrationCompleteEvent(final UserLogin user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserLogin getUser() {
        return user;
    }

}
