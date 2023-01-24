package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.entity.Category;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping(path = "active", method = RequestMethod.GET)
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @RequestMapping(path = "random", method = RequestMethod.GET)
    public List<Category> getRandomCategory(@RequestParam(name = "number") int number) {
        return categoryService.getRandomCategoryByStatus(number, BaseStatusEnum.ENABLE.getKey());
    }


}
