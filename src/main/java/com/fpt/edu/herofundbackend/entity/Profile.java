package com.fpt.edu.herofundbackend.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fpt.edu.herofundbackend.dto.profile.ProfileRequest;
import com.fpt.edu.herofundbackend.util.DateUtils;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fpt.edu.herofundbackend.constant.SystemConstant.DATETIME_PATTERN;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "profiles")
@Builder
public class Profile {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }


    public void updateProfile(ProfileRequest request){
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.dateOfBirth = DateUtils.stringToLocalDateDMY(request.getDateOfBirth());
        this.phone = request.getPhone();
        this.email = request.getEmail();
        this.avatar = request.getAvatar();
        this.address = request.getAddress();
    }

}
