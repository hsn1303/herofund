package com.fpt.edu.herofundbackend.entity;

import com.fpt.edu.herofundbackend.dto.campaign.CampaignRequest;
import com.fpt.edu.herofundbackend.util.DateUtils;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "campaigns")
@Builder
public class Campaign extends BaseEntity{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "donations")
    private long donations;

    @Column(name = "target_amount")
    private long targetAmount;

    @Column(name = "current_amount")
    private long currentAmount;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "portal")
    private String portal;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "sponsor_id")
    private long sponsorId;

    @Column(name = "account_id")
    private long accountId;

    public void updateCampaign(CampaignRequest request){
        this.title = request.getTitle();
        this.startDate = DateUtils.stringToLocalDateYMD(request.getStartDate());
        this.endDate = DateUtils.stringToLocalDateYMD(request.getEndDate());
        this.targetAmount = request.getTargetAmount();
        this.detail = request.getDetail();
        this.description = request.getDescription();
        this.image = request.getImage();
        this.categoryId = request.getCategoryId();
        this.sponsorId = request.getSponsorId();
    }

}
