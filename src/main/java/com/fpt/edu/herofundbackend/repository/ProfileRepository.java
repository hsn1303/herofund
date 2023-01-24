package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByAccountId(long accountId);
}
