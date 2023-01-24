package com.fpt.edu.herofundbackend.dto.auth;

import com.fpt.edu.herofundbackend.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private long id;
    private String username;
    private String role;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.role = account.getRole();
        this.createdAt = account.getCreatedAt();
        this.updateAt = account.getUpdatedAt();
        this.status = account.getStatus();
    }
}
