package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.entity.Category;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.CategoryRepository;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import com.fpt.edu.herofundbackend.util.SystemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService {

    public final CategoryRepository categoryRepository;

    public Category save(Category request) {
        try {
            request.setStatus(BaseStatusEnum.ENABLE.getKey());
            return categoryRepository.save(request);
        } catch (Exception e) {
            return null;
        }
    }

    public Category update(Category request) {
        Category exist = categoryRepository
                .findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CATEGORY_NOT_FOUND));
        try {
            exist.setName(request.getName());
            exist.setImage(request.getImage());
            return categoryRepository.save(exist);
        } catch (Exception e) {
            return null;
        }
    }


    public Category updateStatus(long id, int status) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CATEGORY_NOT_FOUND));
        try {
            c.setStatus(status);
            return categoryRepository.save(c);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Category> updateMultipleStatus(List<Long> ids, int status) {
        try {
            List<Category> exists = categoryRepository.findByIds(ids);
            if (MyCollectionUtils.listIsNullOrEmpty(exists)) {
                return null;
            }
            exists.forEach(e -> e.setStatus(status));
            return categoryRepository.saveAll(exists);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Category> getRandomCategoryByStatus(int number, int status) {
        try {
            List<Category> exists = categoryRepository.getAllByStatus(status);
            if (MyCollectionUtils.listIsNullOrEmpty(exists)) {
                return null;
            }
            List<Category> result = new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < number; i++) {
                int size = exists.size();
                if (size == 0) break;
                int randomIndex = rand.nextInt(size);
                Category c = exists.get(randomIndex);
                if (c != null){
                    result.add(exists.get(randomIndex));
                    exists.remove(randomIndex);
                }

            }
            return new ArrayList<>(result);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Category> getAll() {
        return categoryRepository.getAllByStatus(BaseStatusEnum.ENABLE.getKey());
    }

    public PageDto<Category> getPage(int offset, int limit, String keyword, Integer status) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Category> page = categoryRepository.searchCategories(status, keyword, pageable);
        return PageDto.<Category>builder()
                .limit(limit)
                .offset(offset)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .items(page.getContent())
                .build();
    }

    public Category getDetail(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CATEGORY_NOT_FOUND));
    }

    public Category findByIdAndStatus(long id, int status) {
        return categoryRepository.findByIdAndStatus(id, status)
                .orElse(null);
    }
}
