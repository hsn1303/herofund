package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignDto;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.enums.CampaignStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.service.CampaignService;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    @RequestMapping(method = RequestMethod.GET)
    public PageDto<CampaignDto> getCampaigns(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        List<Integer> status = Arrays.asList(
                CampaignStatusEnum.ENABLE.getKey(),
                CampaignStatusEnum.URGENT.getKey());
        return campaignService.getPageByStatus(offset, limit, status);
    }

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<Campaign> getCampaigns() {
        List<Integer> status = Arrays.asList(
                CampaignStatusEnum.ENABLE.getKey(),
                CampaignStatusEnum.URGENT.getKey());
        return campaignService.getAllByStatus(status);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public CampaignDto getDetail(@RequestParam(name = "id") long id) {
        List<Integer> status = Arrays.asList(
                CampaignStatusEnum.DISABLE.getKey(),
                CampaignStatusEnum.WAIT.getKey(),
                CampaignStatusEnum.REJECT.getKey());

        CampaignDto campaignDto = campaignService.getDetail(id);
        if (status.contains(campaignDto.getStatus())) {
            throw new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND);
        }
        return campaignDto;
    }

    @RequestMapping(path = "urgent/random", method = RequestMethod.GET)
    public CampaignDto getCampaignUrgentRandom() {
        return campaignService.getCampaignUrgentRandom(Collections.singletonList(CampaignStatusEnum.URGENT.getKey()));
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public PageDto<CampaignDto> search(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword

            ) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        if (category == 0) category = null;
        if (MyStringUtils.isNullOrEmptyWithTrim(keyword)) keyword = null;
        return campaignService.search(offset, limit, category, keyword);
    }



}
