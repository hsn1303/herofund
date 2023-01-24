package com.fpt.edu.herofundbackend.dto.campaign;

import com.fpt.edu.herofundbackend.dto.auth.AccountDtoResponse;
import com.fpt.edu.herofundbackend.dto.category.CategoryDto;
import com.fpt.edu.herofundbackend.dto.sponsor.SponsorDto;
import com.fpt.edu.herofundbackend.entity.Campaign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDto {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private long donations;
    private long targetAmount;
    private long currentAmount;
    private String detail;
    private String description;
    private String image;
    private String portal;
    private int status;
    private CategoryDto category;
    private SponsorDto sponsor;
    private AccountDtoResponse account;

    public CampaignDto(Campaign campaign) {
        this.id = campaign.getId();
        this.title = campaign.getTitle();
        this.detail = campaign.getDetail();
        this.description = campaign.getDescription();
        this.image = campaign.getImage();
        this.donations = campaign.getDonations();
        this.startDate = campaign.getStartDate();
        this.endDate = campaign.getEndDate();
        this.createdAt = campaign.getCreatedAt();
        this.updateAt = campaign.getUpdatedAt();
        this.targetAmount = campaign.getTargetAmount();
        this.currentAmount = campaign.getCurrentAmount();
        this.portal = campaign.getPortal();
        this.status = campaign.getStatus();
    }
}
