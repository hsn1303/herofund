package com.fpt.edu.herofundbackend.dto.sponsor;

import com.fpt.edu.herofundbackend.entity.Sponsor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorDto {

    private long id;
    private String name;
    private String image;

    public SponsorDto(Sponsor dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.image = dto.getImage();
    }
}
