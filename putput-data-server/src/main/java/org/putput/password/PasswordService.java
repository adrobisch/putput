package org.putput.password;

import org.apache.commons.lang3.RandomStringUtils;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Transactional
public class PasswordService {
  PasswordEncoder passwordEncoder;

  UserRepository userRepository;

  MailSender mailSender;

  @Autowired
  public PasswordService(PasswordEncoder passwordEncoder, UserRepository userRepository, MailSender mailSender) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.mailSender = mailSender;
  }

  public class PasswordResetSuccess {
    private transient String newPassword;

    public PasswordResetSuccess(String newPassword) {
      this.newPassword = newPassword;
    }

    public String getNewPassword() {
      return newPassword;
    }
  }

  public Optional<PasswordResetSuccess> changePassword(String userName, String newPassword, String passwordRepeat) {
    UserEntity userWithMail = userRepository.findByUsername(userName);
    if (newPassword == null || !newPassword.equals(passwordRepeat)) {
      return Optional.empty();
    }

    return Optional.of(updatePasswordHash(userWithMail, newPassword));
  }

  public Optional<PasswordResetSuccess> resetPassword(String email) {
    Optional<UserEntity> userWithMail = userRepository.findByEmail(email);
    if (!userWithMail.isPresent()) {
      return Optional.empty();
    } else {
      PasswordResetSuccess passwordSet = updatePasswordHash(userWithMail.get(), newRandomPassword());
      mailSender.send(passwordResetMail(userWithMail.get().getEmail(), passwordSet.getNewPassword()));
      return Optional.of(passwordSet);
    }
  }

  SimpleMailMessage passwordResetMail(String recipient, String newPassword) {
    SimpleMailMessage mailWithNewPassword = new SimpleMailMessage();
    mailWithNewPassword.setTo(recipient);
    mailWithNewPassword.setSubject("Your Password Reset Request");
    mailWithNewPassword.setText(format("Your new Password: %s", newPassword));
    return mailWithNewPassword;
  }

  PasswordResetSuccess updatePasswordHash(UserEntity userWithMail, String newPassword) {
    userWithMail.setPasswordHash(passwordEncoder.encode(newPassword));
    return new PasswordResetSuccess(newPassword);
  }

  protected String newRandomPassword() {
    return RandomStringUtils.randomAlphanumeric(8);
  }
}
