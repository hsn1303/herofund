package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.entity.FAQ;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.CampaignRepository;
import com.fpt.edu.herofundbackend.repository.FAQRepository;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import com.fpt.edu.herofundbackend.util.SystemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FAQService {

    private final FAQRepository FAQRepository;
    private final CampaignRepository campaignRepository;

    public FAQ update(FAQ request) {

        FAQ existFaq = FAQRepository.findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.FAQ_NOT_FOUND));
        try {
            existFaq.setDetail(request.getDetail());
            existFaq.setCampaignId(request.getCampaignId());
            return FAQRepository.save(existFaq);
        } catch (Exception e) {
            return null;
        }
    }

    public FAQ save(FAQ request) {
        campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        try {
            request.setStatus(BaseStatusEnum.ENABLE.getKey());
            return FAQRepository.save(request);
        } catch (Exception e) {
            return null;
        }
    }

    public FAQ updateStatus(long id, int status) {
        FAQ faq = FAQRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.FAQ_NOT_FOUND));
        try {
            faq.setStatus(status);
            return FAQRepository.save(faq);
        } catch (Exception e) {
            return null;
        }
    }

    public PageDto<FAQ> getPage(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<FAQ> page = FAQRepository.getPage(pageable);
        return PageDto.<FAQ>builder()
                .items(page.getContent())
                .limit(limit)
                .offset(offset)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }

    public FAQ getDetail(long id) {
        return FAQRepository.getByIdAndStatus(id, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.FAQ_NOT_FOUND));
    }

    public List<FAQ> updateMultipleStatus(List<Long> ids, int status) {
        try {
            List<FAQ> faqs = FAQRepository.findByIds(ids);
            if (MyCollectionUtils.listIsNullOrEmpty(faqs)) {
                return null;
            }
            faqs.forEach(f -> f.setStatus(status));
            return FAQRepository.saveAll(faqs);
        } catch (Exception e) {
            return null;
        }
    }
}
