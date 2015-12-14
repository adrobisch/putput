package org.putput.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfilePageController {

  @RequestMapping("/@{username}")
  public String profilePage(@PathVariable("username") String username) {
    return "redirect:/#/profile/" + username;
  }
}
