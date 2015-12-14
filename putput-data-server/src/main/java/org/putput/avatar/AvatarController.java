package org.putput.avatar;

import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

@Controller
public class AvatarController {

  @Autowired
  UserRepository userRepository;

  @RequestMapping("/avatar/{userName}")
  public String getAvatar(@PathVariable("userName") String userName) {
    Optional<String> mailHash = ofNullable(userRepository.findByUsername(userName))
        .map(UserEntity::getEmail)
        .filter(email -> !email.isEmpty())
        .map(email -> md5DigestAsHex(email.getBytes()));

    if (mailHash.isPresent()) {
      return "redirect:https://www.gravatar.com/avatar/" + mailHash.get();
    } else {
      return "redirect:https://www.gravatar.com/avatar/" + md5DigestAsHex(userName.getBytes()) + "?f=y&d=monsterid";
    }
  }

}
