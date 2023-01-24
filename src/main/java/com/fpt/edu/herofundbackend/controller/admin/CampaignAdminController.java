package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.StatusAndIdsRequest;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignDto;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignRequest;
import com.fpt.edu.herofundbackend.dto.campaign.FilterCampaignRequest;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.enums.CampaignStatusEnum;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import com.fpt.edu.herofundbackend.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin/campaigns")
public class CampaignAdminController {

    private final CampaignService campaignService;
    private final AuthenticateService authenticateService;

    @RequestMapping(method = RequestMethod.GET)
    public PageDto<CampaignDto> getPageCampaign(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return campaignService.getPage(offset, limit);
    }

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<Campaign> getAll() {
        return campaignService.getAllByStatus(CampaignStatusEnum.convertKeyToList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid CampaignRequest request, HttpServletRequest servlet) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SAVE_FAIL);
        Campaign campaign = campaignService.save(request, authenticateService.getAccountByUsername(servlet.getUserPrincipal().getName()));
        if (null == campaign) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(campaign, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> update(@RequestBody CampaignRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        Campaign campaign = campaignService.updateCampaignOfAdmin(request);
        if (campaign == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(campaign, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public CampaignDto getDetail(@RequestParam(name = "id") long id) {
        return campaignService.getDetail(id);
    }

    @RequestMapping(path = "update/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "id") int id,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 4, message = SystemConstant.Message.STATUS_VALIDATION)
            @RequestParam(name = "status") int status) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        Campaign campaign = campaignService.updateStatusCampaignById(id, status);
        if (campaign == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(campaign, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "update-multiple/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatusCampaignMultiple(@RequestBody @Valid StatusAndIdsRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        List<Campaign> campaigns = campaignService.updateMultipleStatusCampaignByIds(request.getIds(), request.getStatus());
        if (campaigns == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(campaigns, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "search", method = RequestMethod.POST)
    public PageDto<CampaignDto> search(@RequestBody FilterCampaignRequest request) {
        return campaignService.findCampaignPaging(request);
    }

}
