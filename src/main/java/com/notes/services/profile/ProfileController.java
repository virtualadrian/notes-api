package com.notes.services.profile;

import com.notes.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ResponseEntity<ProfileModel> getForCurrentUser() {
        return Ok(profileService.getForCurrentUser());
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public ResponseEntity<ProfileModel> saveForCurrentUser(
        @RequestBody final ProfileModel profileModel) {
        return Ok(profileService.saveForCurrentUser(profileModel));
    }
}
