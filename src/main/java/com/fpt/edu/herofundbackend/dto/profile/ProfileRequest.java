package com.fpt.edu.herofundbackend.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String avatar;
    private String phone;
    private String address;
}
