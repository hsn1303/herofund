package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.dto.profile.ProfileRequest;
import com.fpt.edu.herofundbackend.entity.Profile;
import com.fpt.edu.herofundbackend.repository.ProfileRepository;
import com.fpt.edu.herofundbackend.util.MapUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile saveProfile(ProfileRequest request, long accountId) {
        Profile profile = profileRepository.findProfileByAccountId(accountId).orElse(null);
        if (null == profile) {
            profile = MapUtils.formRequestToProfileEntity(request);
            profile.setAccountId(accountId);
        }else {
            profile.updateProfile(request);
        }
        profileRepository.save(profile);
        return profile;
    }


    public Profile getProfileById(long accountId) {
        return profileRepository.findProfileByAccountId(accountId).orElse(null);
    }

}
