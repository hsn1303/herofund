package com.fpt.edu.herofundbackend.entity;


import com.fpt.edu.herofundbackend.constant.SystemConstant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "faqs")
public class FAQ extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "campaign_id")
    private long campaignId;

    @Column(name = "detail", columnDefinition = "TEXT")
    @NotBlank(message = SystemConstant.Message.DETAIL_NOT_EMPTY)
    private String detail;

}
