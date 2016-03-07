package org.putput.profile;

import org.putput.api.model.UserProfile;
import org.putput.api.resource.Profile;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ProfileResource extends BaseResource implements Profile {

  @Autowired
  ProfileService profileService;

  @Override
  public GetProfileByUserNameResponse getProfileByUserName(String userName) throws Exception {
    Optional<UserProfile> userProfile = profileService.getProfileByUserName(userName, this);

    if (userProfile.isPresent()) {
      return GetProfileByUserNameResponse.withHaljsonOK(userProfile.get());
    } else {
      return GetProfileByUserNameResponse.withNotFound();
    }
  }

  @Override
  public PostProfileByUserNameFollowResponse postProfileByUserNameFollow(String profileUserName) throws Exception {
    profileService.addFollower(user(), profileUserName);
    return PostProfileByUserNameFollowResponse.withOK();
  }

  @Override
  public DeleteProfileByUserNameFollowResponse deleteProfileByUserNameFollow(String profileUserName) throws Exception {
    profileService.removeFollower(user(), profileUserName);
    return DeleteProfileByUserNameFollowResponse.withOK();
  }

}
