package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.sponsor.SponsorRequest;
import com.fpt.edu.herofundbackend.entity.Sponsor;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.SponsorRepository;
import com.fpt.edu.herofundbackend.util.MapUtils;
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
public class SponsorService {

    private final SponsorRepository sponsorRepository;

    public Sponsor save(SponsorRequest request) {
        try {
            Sponsor sponsor = MapUtils.formRequestToSponsorEntity(request);
            sponsor.setStatus(BaseStatusEnum.ENABLE.getKey());
            return sponsorRepository.save(sponsor);
        } catch (Exception e) {
            return null;
        }
    }

    public Sponsor update(SponsorRequest request) {
        Sponsor exist = sponsorRepository
                .findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.SPONSOR_NOT_FOUND));
        try {
            exist.updateSponsor(request);
            return sponsorRepository.save(exist);
        } catch (Exception e) {
            return null;
        }
    }

    public Sponsor updateStatus(long id, int status) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.SPONSOR_NOT_FOUND));
        try {
            sponsor.setStatus(status);
            return sponsorRepository.save(sponsor);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Sponsor> getAllByStatus(int status) {
        return sponsorRepository.findAllByStatus(status);
    }

    public List<Sponsor> getAll() {
        return sponsorRepository.findAll();
    }

    public Sponsor getDetail(long id) {
        return sponsorRepository
                .findById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.SPONSOR_NOT_FOUND));
    }

    public Sponsor getDetailByStatus(long id, int status) {
        return sponsorRepository
                .getSponsorByIdAndStatus(id, status)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.SPONSOR_NOT_FOUND));
    }

    public PageDto<Sponsor> getPage(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Sponsor> page = sponsorRepository.getPageSponsors(pageable);
        return PageDto.<Sponsor>builder()
                .items(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset)
                .build();
    }

    public List<Sponsor> updateMultipleStatus(List<Long> ids, int status) {
        try {
            List<Sponsor> sponsors = sponsorRepository.findByIds(ids);
            if (sponsors.isEmpty()) {
                return null;
            }
            sponsors.forEach(s -> s.setStatus(status));
            return sponsorRepository.saveAll(sponsors);
        } catch (Exception e) {
            return null;
        }

    }

    public PageDto<Sponsor> search(String keyword, Integer status, int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Sponsor> page = sponsorRepository.findPageSponsor(keyword, status, pageable);
        return PageDto.<Sponsor>builder()
                .items(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset)
                .build();
    }
}
