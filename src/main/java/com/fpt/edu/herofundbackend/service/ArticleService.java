package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.article.ArticleRequest;
import com.fpt.edu.herofundbackend.dto.article.FilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.article.JpaFilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.campaign.FilterCampaignRequest;
import com.fpt.edu.herofundbackend.dto.campaign.JpaFilterCampaignRequest;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.ArticleRepository;
import com.fpt.edu.herofundbackend.repository.CampaignRepository;
import com.fpt.edu.herofundbackend.util.MapUtils;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CampaignRepository campaignRepository;

    public Article save(ArticleRequest request) {
        campaignRepository.getCampaignByIdAndStatus(request.getCampaignId(), BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        try {
            Article articleNew = MapUtils.fromRequestToArticle(request);
            articleNew.setStatus(BaseStatusEnum.ENABLE.getKey());
            return articleRepository.save(articleNew);
        } catch (Exception e) {
            return null;
        }
    }

    public Article update(ArticleRequest request) {
        campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.CAMPAIGN_NOT_FOUND));
        Article articleExist = articleRepository
                .findById(request.getId())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
        try {
            articleExist.update(request);
            return articleRepository.save(articleExist);
        } catch (Exception e) {
            return null;
        }
    }

    public PageDto<Article> getPageByStatusAndCampaignId(int offset, int limit, long campaignId) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Article> page = articleRepository.getPageArticleByStatusAndCampaignId(campaignId, BaseStatusEnum.ENABLE.getKey(), pageable);
        return PageDto.<Article>builder()
                .items(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
    }

    public List<Article> getAll() {
        return articleRepository.getAllArticleByStatus(BaseStatusEnum.ENABLE.getKey());
    }

    public List<Article> getAllAdmin() {
        return articleRepository.findAll();
    }

    public Article updateStatus(long id, int status) {
        try {
            Article articleExist = articleRepository
                    .findById(id)
                    .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
            articleExist.setStatus(status);
            articleRepository.save(articleExist);
            return articleExist;
        } catch (Exception e) {
            return null;
        }
    }

    public Article getDetail(long id) {
        return articleRepository
                .getArticleByIdAndStatus(id, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
    }

    public Article getDetailAdmin(long id) {
        return articleRepository
                .getArticleById(id)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ARTICLE_NOT_FOUND));
    }

    public PageDto<Article> getPage(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Article> items = articleRepository.getPageArticleByStatus(BaseStatusEnum.ENABLE.getKey(), pageable);
        return PageDto.<Article>builder()
                .items(items.getContent())
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
    }

    public PageDto<Article> getPageAdmin(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Article> items = articleRepository.findAll(pageable);
        return PageDto.<Article>builder()
                .items(items.getContent())
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
    }

    public List<Article> updateMultipleStatusArticleByIds(List<Long> ids, int status) {
        try {
            List<Article> articles = articleRepository.getArticleByIds(ids);
            if (MyCollectionUtils.listIsNullOrEmpty(articles)) {
                return null;
            }
            articles.forEach(c -> c.setStatus(status));
            return articleRepository.saveAll(articles);
        } catch (Exception e) {
            return null;
        }
    }

    public PageDto<Article> findArticlePaging(FilterArticleRequest request) {
        int limit = request.getLimit() <= 0 ? 10 : request.getLimit();
        int offset = request.getOffset() <= 0 ? 1 : request.getOffset();
        JpaFilterArticleRequest jpaRequest = new JpaFilterArticleRequest(request);
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Article> page = articleRepository.findCampaignPaging(jpaRequest, pageable);
        return PageDto.<Article>builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .limit(limit)
                .offset(offset)
                .items(page.getContent())
                .build();
    }
}
