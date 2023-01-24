package com.fpt.edu.herofundbackend.entity;

import com.fpt.edu.herofundbackend.dto.sponsor.SponsorRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "sponsors")
public class Sponsor extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "detail", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    public void updateSponsor(SponsorRequest request){
        this.name = request.getName();
        this.description = request.getDescription();
        this.detail = request.getDetail();
        this.image = request.getImage();
    }

}
