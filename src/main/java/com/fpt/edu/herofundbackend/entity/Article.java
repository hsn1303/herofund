package com.fpt.edu.herofundbackend.entity;


import com.fpt.edu.herofundbackend.dto.article.ArticleRequest;
import com.fpt.edu.herofundbackend.enums.TypeEnum;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "articles")
public class Article extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "campaign_id")
    private long campaignId;

    @Formula("(select count(l.id) from likes l where l.item_id = id and l.type_id = 1)")
    private long numberOfLike;

    @Formula("(select c.title from campaigns c where c.id = campaign_id)")
    private String campaignTitle;

    public void update(ArticleRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.detail = request.getDetail();;
        this.image = request.getImage();
        this.campaignId = request.getCampaignId();
    }
}
