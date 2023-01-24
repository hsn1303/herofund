package com.fpt.edu.herofundbackend.dto.auth;

import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Profile;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountWithProfile {

    private long id;
    private String username;
    private String role;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Profile profile;

    public AccountWithProfile(Account account, Profile profile) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.role = account.getRole();
        this.createdAt = account.getCreatedAt();
        this.updateAt = account.getUpdatedAt();
        this.status = account.getStatus();
        this.profile = profile;
    }
}
