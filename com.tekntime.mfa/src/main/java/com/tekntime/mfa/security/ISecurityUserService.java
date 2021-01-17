package com.tekntime.mfa.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
