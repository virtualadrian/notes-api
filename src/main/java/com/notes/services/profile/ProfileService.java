package com.notes.services.profile;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService extends BaseCrudService<ProfileModel, ProfileEntity, Long> {

    private final ProfileRepository profileRepository;

    ProfileModel getForCurrentUser() {
        ProfileEntity profileEntity = profileRepository
            .getByAccountId(SecurityUtil.getCurrentUserAccountId());
        return toModel(profileEntity);
    }

    ProfileModel saveForCurrentUser(ProfileModel profileModel) {
        ProfileEntity profileEntity = profileRepository
            .getByAccountId(SecurityUtil.getCurrentUserAccountId());
        return (profileEntity != null) ? update(profileModel) : create(profileModel);
    }
}
