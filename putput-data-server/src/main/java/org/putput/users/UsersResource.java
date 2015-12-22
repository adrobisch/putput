package org.putput.users;

import org.putput.api.model.UserInfo;
import org.putput.api.model.UserList;
import org.putput.api.model.UserListLinks;
import org.putput.api.resource.Users;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Optional.ofNullable;

@Controller
public class UsersResource extends BaseResource implements Users {

  @Autowired
  UserRepository userRepository;

  @Override
  public GetUsersResponse getUsers(String search, BigDecimal page) throws Exception {
    List<UserInfo> users = new ArrayList<>();

    Pageable pageable = pageable(ofNullable(page).orElse(new BigDecimal(0)));

    Iterable<UserEntity> userPage = userPage(ofNullable(search), pageable);

    userPage.forEach(userEntity -> {
      users.add(new UserInfo()
          .withUserName(userEntity.getUsername())
          .withDisplayName(userEntity.getDisplayName())
          .withAbout(userEntity.getAbout()));
    });

    UserList userList = new UserList().withUsers(users);
    UserListLinks links = new UserListLinks().withSelf(link(Users.class));

    if (userPage instanceof Page && ((Page) userPage).hasNext()) {
      links.withNextPage(link(Users.class, nextPageParams(((Page) userPage).getNumber() + 1)));
    }

    userList.withLinks(links);

    return GetUsersResponse.withHaljsonOK(userList);
  }

  private Iterable<UserEntity> userPage(Optional<String> search, Pageable pageable) {
    if (search.isPresent()) {
      return userRepository.findByUsernameContaining(search.get());
    } else {
      return userRepository.findAll(pageable);
    }
  }

  Pageable pageable(BigDecimal page) {
    return new PageRequest(page.intValue(), 15);
  }

  private Map<String, Object> nextPageParams(Integer page) {
    HashMap<String, Object> pageParams = new HashMap<>();
    if (page != null) {
      pageParams.put("page", page);
    }
    return pageParams;
  }
}
