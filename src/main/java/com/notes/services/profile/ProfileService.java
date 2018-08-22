package com.notes.services.profile;

import com.notes.core.BaseCrudService;
import com.notes.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService extends BaseCrudService<ProfileModel, ProfileEntity, Long> {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        super(ProfileModel.class, ProfileEntity.class);
        this.profileRepository = profileRepository;
    }

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
