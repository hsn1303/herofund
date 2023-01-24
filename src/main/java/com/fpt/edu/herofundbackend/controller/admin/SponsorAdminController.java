package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.dto.sponsor.SponsorRequest;
import com.fpt.edu.herofundbackend.entity.Sponsor;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.service.SponsorService;
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
@RequestMapping("api/v1/admin/sponsors")
public class SponsorAdminController {

    private final SponsorService sponsorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody SponsorRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false).message(SystemConstant.Message.SAVE_FAIL)
                .build();
        Sponsor sponsor = sponsorService.save(request);
        if (null == sponsor) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(sponsor, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@Valid @RequestBody SponsorRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false).message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        Sponsor sponsor = sponsorService.update(request);
        if (null == sponsor) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(sponsor, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
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
        Sponsor sponsor = sponsorService.updateStatus(id, status);
        if (sponsor == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(sponsor, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateMultipleStatus(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        List<Sponsor> sponsors = sponsorService.updateMultipleStatus(request.getIds(), request.getStatus());
        if (MyCollectionUtils.listIsNullOrEmpty(sponsors)) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(sponsors, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public ResponseEntity<List<Sponsor>> getAll() {
        return ResponseEntity.ok(sponsorService.getAllByStatus(BaseStatusEnum.ENABLE.getKey()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<Sponsor>> getPage(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "status", required = false) Integer status,
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {

        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return ResponseEntity.ok(sponsorService.search(keyword, status, offset, limit));
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<Sponsor> getDetail(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(sponsorService.getDetail(id));
    }

}
