package com.tekntime.mfa.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.tekntime.mfa.model.PasswordResetToken;
import com.tekntime.mfa.model.UserLogin;
import com.tekntime.mfa.model.VerificationToken;
import com.tekntime.mfa.web.dto.UserDto;
import com.tekntime.mfa.web.error.UserAlreadyExistException;

public interface IUserService {

    UserLogin registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    UserLogin getUser(String verificationToken);

    void saveRegisteredUser(UserLogin user);

    void deleteUser(UserLogin user);

    void createVerificationTokenForUser(UserLogin user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(UserLogin user, String token);

    UserLogin findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    UserLogin getUserByPasswordResetToken(String token);

    Optional<UserLogin> getUserByID(long id);

    void changeUserPassword(UserLogin user, String password);

    boolean checkIfValidOldPassword(UserLogin user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(UserLogin user) throws UnsupportedEncodingException;

    UserLogin updateUser2FA(boolean use2FA);

    List<Object> getUsersFromSessionRegistry();

}
