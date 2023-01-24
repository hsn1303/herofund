package com.fpt.edu.herofundbackend.dto.category;

import com.fpt.edu.herofundbackend.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private long id;
    private String name;
    private String image;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.image = category.getImage();
    }
}
