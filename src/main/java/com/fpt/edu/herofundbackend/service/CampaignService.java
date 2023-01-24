package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.auth.AccountDtoResponse;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignDto;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignRequest;
import com.fpt.edu.herofundbackend.dto.campaign.FilterCampaignRequest;
import com.fpt.edu.herofundbackend.dto.campaign.JpaFilterCampaignRequest;
import com.fpt.edu.herofundbackend.dto.category.CategoryDto;
import com.fpt.edu.herofundbackend.dto.sponsor.SponsorDto;
import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.entity.Category;
import com.fpt.edu.herofundbackend.entity.Sponsor;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.enums.CampaignStatusEnum;
import com.fpt.edu.herofundbackend.enums.PortalEnum;
import com.fpt.edu.herofundbackend.enums.RoleEnum;
import com.fpt.edu.herofundbackend.exception.MyBadRequestException;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.*;
import com.fpt.edu.herofundbackend.util.MapUtils;
import com.fpt.edu.herofundbackend.util.MyCollectionUtils;
import com.fpt.edu.herofundbackend.util.SystemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CategoryRepository categoryRepository;
    private final SponsorRepository sponsorRepository;
    private final AccountRepository accountRepository;

    public Campaign save(CampaignRequest request, Account account) {
        categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CATEGORY_NOT_FOUND));
        try {
            Campaign newCampaign = MapUtils.fromRequestToCampaign(request);
            newCampaign.setPortal(account.getRole().equalsIgnoreCase(RoleEnum.ROLE_ADMIN.name()) ? PortalEnum.ADMIN.name() : PortalEnum.USER.name());
            newCampaign.setAccountId(account.getId());
            newCampaign.setStatus(account.getRole().equalsIgnoreCase(RoleEnum.ROLE_ADMIN.name()) ? CampaignStatusEnum.ENABLE.getKey() : CampaignStatusEnum.WAIT.getKey());
            return campaignRepository.save(newCampaign);
        } catch (Exception e) {
            return null;
        }
    }

    //Lấy ra campaign của chính user đó và đang trong trạng thái chờ duyệt thì mới được sửa
    public Campaign updateCampaignOfUser(CampaignRequest request, Account account) {
        categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CATEGORY_NOT_FOUND));
        Campaign campaignUser = campaignRepository.getCampaignByIdAndAccountIdAndPortalAndStatus(request.getId(), account.getId(), PortalEnum.USER.name(), CampaignStatusEnum.WAIT.getKey())
                .orElseThrow(() -> new UsernameNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        try {
            campaignUser.updateCampaign(request);
            return campaignRepository.save(campaignUser);
        } catch (Exception e) {
            return null;
        }
    }

    // Campaign do chính ADMIN tạo thì ADMIN mới được sửa,
    // ADMIN không được sửa Campaign của USER
    public Campaign updateCampaignOfAdmin(CampaignRequest request) {
        categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CATEGORY_NOT_FOUND));
        Campaign campaignAdmin = campaignRepository.findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        if (!campaignAdmin.getPortal().equalsIgnoreCase(PortalEnum.ADMIN.name())) {
            throw new MyBadRequestException(SystemConstant.Message.PERMISSION_EDIT_DO_NOT);
        }
        try {
            campaignAdmin.updateCampaign(request);
            return campaignRepository.save(campaignAdmin);
        } catch (Exception e) {
            return null;
        }
    }


    public CampaignDto getDetail(long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() ->
                new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        return convertCampaignToDto(campaign);
    }

    public List<Campaign> getAllByStatus(List<Integer> status) {
        return campaignRepository.getAllByStatus(status);
    }

    public PageDto<CampaignDto> getPage(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Campaign> page = campaignRepository.getPage(pageable);
        List<CampaignDto> list = page.getContent().stream().map(this::convertCampaignToDto).collect(Collectors.toList());
        return PageDto.<CampaignDto>builder()
                .totalElements(page.getTotalElements())
                .items(list)
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset).build();
    }

    private CampaignDto convertCampaignToDto(Campaign campaign) {
        Sponsor sponsor = sponsorRepository.getSponsorByIdAndStatus(campaign.getSponsorId(), BaseStatusEnum.ENABLE.getKey()).orElse(null);
        Category category = categoryRepository.findByIdAndStatus(campaign.getCategoryId(), BaseStatusEnum.ENABLE.getKey()).orElse(null);
        Account account = accountRepository.getAccountByIdAndStatus(campaign.getAccountId(), BaseStatusEnum.ENABLE.getKey()).orElse(null);
        CampaignDto campaignDto = new CampaignDto(campaign);
        if (category != null) {
            CategoryDto categoryDto = new CategoryDto(category);
            campaignDto.setCategory(categoryDto);
        }
        if (sponsor != null) {
            SponsorDto sponsorDto = new SponsorDto(sponsor);
            campaignDto.setSponsor(sponsorDto);
        }
        if (account != null) {
            AccountDtoResponse response = new AccountDtoResponse(account);
            campaignDto.setAccount(response);
        }
        return campaignDto;
    }

    public PageDto<CampaignDto> getPageByStatus(int offset, int limit, List<Integer> status) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Campaign> page = campaignRepository.getPageByStatus(status, pageable);
        List<CampaignDto> dtos = page.getContent().stream().map(this::convertCampaignToDto).collect(Collectors.toList());
        return PageDto.<CampaignDto>builder()
                .totalElements(page.getTotalElements())
                .items(dtos)
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset).build();
    }


    public Campaign updateStatusCampaignById(long id, int status) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        try {
            campaign.setStatus(status);
            return campaignRepository.save(campaign);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Campaign> updateMultipleStatusCampaignByIds(List<Long> ids, int status) {
        try {
            List<Campaign> campaigns = campaignRepository.getCampaignByIds(ids);
            if (MyCollectionUtils.listIsNullOrEmpty(campaigns)) {
                return null;
            }
            campaigns.forEach(c -> c.setStatus(status));
            return campaignRepository.saveAll(campaigns);
        } catch (Exception e) {
            return null;
        }
    }

    public PageDto<CampaignDto> findCampaignPaging(FilterCampaignRequest request) {
        int limit = request.getLimit() <= 0 ? 10 : request.getLimit();
        int offset = request.getOffset() <= 0 ? 1 : request.getOffset();
        JpaFilterCampaignRequest jpaRequest = new JpaFilterCampaignRequest(request);
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Campaign> page = campaignRepository.findCampaignPaging(jpaRequest, pageable);
        List<CampaignDto> dtos = page.getContent().stream().map(this::convertCampaignToDto).collect(Collectors.toList());
        return PageDto.<CampaignDto>builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset)
                .items(dtos)
                .build();
    }

    public CampaignDto getCampaignUrgentRandom(List<Integer> status) {
        List<Campaign> list = campaignRepository.getAllByStatus(status);
        if (MyCollectionUtils.listIsNullOrEmpty(list)) return new CampaignDto();
        return convertCampaignToDto(list.get(new Random().nextInt(list.size())));
    }


    public PageDto<CampaignDto> search(int offset, int limit, Long category, String keyword) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Campaign> page = campaignRepository.searchCampaignByKeywordOrCategory(keyword, category, pageable);
        List<CampaignDto> dtos = page.getContent().stream().map(this::convertCampaignToDto).collect(Collectors.toList());
        return PageDto.<CampaignDto>builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset)
                .items(dtos)
                .build();
    }
}
