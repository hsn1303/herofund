package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.dto.article.JpaFilterArticleRequest;
import com.fpt.edu.herofundbackend.dto.campaign.JpaFilterCampaignRequest;
import com.fpt.edu.herofundbackend.entity.Article;
import com.fpt.edu.herofundbackend.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a where a.id = :id and a.status = :status")
    Optional<Article> getArticleByIdAndStatus(@Param("id") long id, @Param("status") int status);

    Optional<Article> getArticleById(@Param("id") long id);

    @Query("select a from Article a where a.status = :status and a.campaignId = :campaignId")
    Page<Article> getPageArticleByStatusAndCampaignId(@Param("campaignId") long campaignId, @Param("status") int status, Pageable pageable);

    @Query("select a from Article a where a.status = :status")
    List<Article> getAllArticleByStatus(@Param("status") int status);

    @Query("select a from Article a where a.status = :status")
    Page<Article> getPageArticleByStatus(@Param("status") int status, Pageable pageable);

    @Query(value = "select a from Article a " +
            "WHERE ( :#{#filter.keyword} is null or a.title like %:#{#filter.keyword}% ) " +
            "AND ( :#{#filter.campaignTitle} is null or a.campaignTitle like %:#{#filter.campaignTitle}% ) " +
            "AND ( :#{#filter.startDateCreateAt} is null or :#{#filter.startDateCreateAt} <= a.createdAt ) " +
            "AND ( :#{#filter.endDateCreateAt} is null or a.createdAt <= :#{#filter.endDateCreateAt} ) " +
            "AND ( :#{#filter.campaignId} is null or :#{#filter.campaignId} = a.campaignId) " +
            "AND ( :#{#filter.id} is null or :#{#filter.id} = a.id) " +
            "AND ( :#{#filter.status} is null or :#{#filter.status} = a.status) ")
    Page<Article> findCampaignPaging(@Param("filter") JpaFilterArticleRequest filter,
                                      Pageable pageable);

    @Query("select a from Article a where a.id in (:ids)")
    List<Article> getArticleByIds(@Param("ids") List<Long> ids);
}
