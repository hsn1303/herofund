package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.entity.Category;
import com.fpt.edu.herofundbackend.service.CategoryService;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid Category category) {
        CommonResponse response = CommonResponse.builder()
                .status(false).message(SystemConstant.Message.SAVE_FAIL)
                .build();
        Category newCategory = categoryService.save(category);
        if (null == newCategory) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(newCategory, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@RequestBody @Valid Category category) {
        CommonResponse response = CommonResponse.builder()
                .status(false).message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        Category updateCategory = categoryService.update(category);
        if (null == updateCategory) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(updateCategory, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public PageDto<Category> getPage(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return categoryService.getPage(offset, limit, keyword, status);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public Category getDetail(@RequestParam(name = "id") long id ) {
        return categoryService.getDetail(id);
    }

    @RequestMapping(path = "update/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "id") long id,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 1, message = SystemConstant.Message.STATUS_VALIDATION)
            @RequestParam("status") int status) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        Category category = categoryService.updateStatus(id, status);
        if (category == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(category, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatusMultiple(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        List<Category> categories = categoryService.updateMultipleStatus(request.getIds(), request.getStatus());
        if (MyCollectionUtils.listIsNullOrEmpty(categories)) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(categories, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
