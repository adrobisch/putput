package org.putput.profile;

import org.putput.api.model.*;
import org.putput.api.model.Follower;
import org.putput.api.resource.Profile;
import org.putput.common.UuidService;
import org.putput.common.web.BaseResource;
import org.putput.stream.StreamItemRepository;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  StreamItemRepository streamItemRepository;

  @Autowired
  FollowerRepository followerRepository;

  @Autowired
  UuidService uuidService;

  public Optional<UserProfile> getProfileByUserName(String profileUsername, BaseResource resource) {
    UserEntity userEntity = userRepository.findByUsername(profileUsername);

    if (userEntity == null) {
      return Optional.empty();
    }

    HalLink followLink = resource.link(Profile.class, profileUsername, "follow");
    HalLink self = resource.link(Profile.class, profileUsername);

    ProfileLinks profileLinks = new ProfileLinks()
        .withSelf(self)
        .withFollow(followLink)
        .withUnfollow(followLink);

    List<Follower> followers = getFollowers(profileUsername);

    ProfileInfo profileInfo = new ProfileInfo()
        .withAbout(userEntity.getAbout())
        .withFollowers(followers)
        .withFollowing(getFollowing(profileUsername))
        .withItemCount(streamItemRepository.countByUsername(profileUsername).intValue());

    UserProfile profile = new UserProfile()
        .withProfile(profileInfo)
        .withFollowing(followerOrSelf(profileUsername, resource, followers))
        .withLinks(profileLinks);

    return Optional.of(profile);
  }

  private boolean followerOrSelf(String profileUsername, BaseResource resource, List<Follower> followers) {
    return profileUsername.equals(resource.user().getUsername()) || followers
        .stream()
        .filter(follower -> follower.getFollower().equals(resource.user().getUsername()) &&
            follower.getFollowed().equals(profileUsername))
        .findFirst()
        .isPresent();
  }

  private List<Follower> getFollowing(String userName) {
    return followerRepository
        .findByFollower(userName)
        .stream()
        .map(followerEntityToDto())
        .collect(Collectors.toList());
  }

  private List<Follower> getFollowers(String userName) {
    return followerRepository
        .findByFollowed(userName)
        .stream()
        .map(followerEntityToDto())
        .collect(Collectors.toList());
  }

  private Function<FollowerEntity, Follower> followerEntityToDto() {
    return follower -> new Follower()
      .withFollowed(follower.getFollowed())
      .withFollower(follower.getFollower());
  }

  public FollowerEntity addFollower(String follower, String followed) {
    return followerRepository.save(new FollowerEntity()
        .setId(uuidService.uuid())
        .setFollower(follower)
        .setFollowed(followed));
  }

  public void removeFollower(String follower, String followed) {
    Optional.ofNullable(followerRepository.findByFollowerAndFollowed(follower, followed))
        .ifPresent(followerRepository::delete);
  }
}
