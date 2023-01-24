package com.fpt.edu.herofundbackend.dto.auth;

import com.fpt.edu.herofundbackend.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoResponse {
    private long id;
    private String username;

    public AccountDtoResponse(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
    }
}
