package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.entity.FAQ;
import com.fpt.edu.herofundbackend.service.FAQService;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("api/v1/admin/faqs")
public class FAQAdminController {

    private final FAQService FAQService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@RequestBody FAQ request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.SAVE_FAIL)
                .build();
        FAQ faq = FAQService.save(request);
        if (faq == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(faq, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@RequestBody FAQ request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        FAQ faq = FAQService.update(request);
        if (faq == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(faq, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<FAQ>> getPage(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return ResponseEntity.ok(FAQService.getPage(offset, limit));
    }

    @RequestMapping(path = "update/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "id") long id,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 1, message = SystemConstant.Message.STATUS_VALIDATION)
            @RequestParam(name = "status") int status) {

        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        FAQ faq = FAQService.updateStatus(id, status);
        if (null == faq) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(faq, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateMultipleStatus(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        List<FAQ> faqs = FAQService.updateMultipleStatus(request.getIds(), request.getStatus());
        if (MyCollectionUtils.listIsNullOrEmpty(faqs)) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(faqs, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getDetail(@RequestParam(name = "id") long id) {
        CommonResponse response = new CommonResponse();
        FAQ faq = FAQService.getDetail(id);
        response.setSuccessWithDataResponse(faq);
        return ResponseEntity.ok(response);
    }

}
