package com.fpt.edu.herofundbackend.dto.profile;

import com.fpt.edu.herofundbackend.dto.auth.AccountDtoResponse;
import com.fpt.edu.herofundbackend.entity.Profile;
import com.fpt.edu.herofundbackend.util.SystemUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String avatar;
    private String phone;
    private String address;

    private AccountDtoResponse account;

    public ProfileDto(Profile p) {
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.dateOfBirth = p.getDateOfBirth() == null ? "" : p.getDateOfBirth().toString();
        this.email = p.getEmail();
        this.avatar = p.getAvatar();
        this.phone = p.getPhone();
        this.address = p.getAddress();
    }
}
