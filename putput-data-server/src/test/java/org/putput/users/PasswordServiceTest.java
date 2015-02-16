package org.putput.users;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.putput.password.PasswordService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class PasswordServiceTest {
  @Test
  public void shouldSendMailWithNewPassword() {
    // given:
    String userMailAddress = "lostmypassword@putput.org";
    String newPassword = "newPassword";

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    UserRepository userRepository = mock(UserRepository.class);
    MailSender mailSender = mock(MailSender.class);

    PasswordService passwordService = createPasswordService(newPassword, passwordEncoder, userRepository, mailSender);

    // when:
    when(passwordEncoder.encode(anyString())).thenReturn(newPassword);
    when(userRepository.findByEmail(userMailAddress)).thenReturn(Optional.of(new UserEntity()));

    Optional<PasswordService.PasswordResetSuccess> passwordReset = passwordService.resetPassword(userMailAddress);

    // then:
    assertThat(passwordReset.isPresent());
    verify(mailSender).send(argThat(messageContaining(newPassword)));
  }

  private PasswordService createPasswordService(final String newPassword, final PasswordEncoder passwordEncoder, final UserRepository userRepository, final MailSender mailSender) {
    return new PasswordService(passwordEncoder, userRepository, mailSender) {
      @Override
      protected String newRandomPassword() {
        return newPassword;
      }
    };
  }

  private ArgumentMatcher<SimpleMailMessage> messageContaining(final String newPassword) {
    return new ArgumentMatcher<SimpleMailMessage>() {
      @Override
      public boolean matches(Object message) {
        return ((SimpleMailMessage) message).getText().contains(newPassword);
      }
    };
  }
}