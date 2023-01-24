package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.dto.campaign.JpaFilterCampaignRequest;
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
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query(value = "select c from Campaign c where c.status in (:status)")
    Page<Campaign> getPageByStatus(@Param("status") List<Integer> status, Pageable pageable);

    @Query(value = "select c from Campaign c where c.status in (:status) order by c.createdAt desc")
    List<Campaign> getAllByStatus(@Param("status") List<Integer> status);

    @Query(value = "select c from Campaign c where c.status = :status and c.id = :id")
    Optional<Campaign> getCampaignByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select c from Campaign c where c.id in (:ids) order by c.createdAt desc")
    List<Campaign> getCampaignByIds(@Param("ids") List<Long> ids);

    @Query(value = "select c from Campaign c where c.id = :id and c.accountId = :accountId and c.portal = :portal and c.status = :status")
    Optional<Campaign> getCampaignByIdAndAccountIdAndPortalAndStatus(
            @Param("id") long id,
            @Param("accountId") long accountId,
            @Param("portal") String portal,
            @Param("status") int status);

    @Query(value = "select c from Campaign c where c.status = :status and c.id = :id and c.portal = :portal")
    Optional<Campaign> getCampaignByIdAndStatusAndPortal(@Param("id") long id,
                                                         @Param("status") int status,
                                                         @Param("portal") String portal);

    @Query(value = "select c from Campaign c " +
            "WHERE ( :#{#filter.keyword} is null or c.title like %:#{#filter.keyword}% ) " +
            "AND ( :#{#filter.startDateCreateAt} is null or :#{#filter.startDateCreateAt} <= c.createdAt ) " +
            "AND ( :#{#filter.endDateCreateAt} is null or c.createdAt <= :#{#filter.endDateCreateAt} ) " +
            "AND ( :#{#filter.category} is null or :#{#filter.category} = c.categoryId) " +
            "AND ( :#{#filter.id} is null or :#{#filter.id} = c.id) " +
            "AND ( :#{#filter.status} is null or :#{#filter.status} = c.status) " +
            "AND ( :#{#filter.accountId} is null or :#{#filter.accountId} = c.accountId) " +
            "AND ( :#{#filter.targetAmountStart} is null or :#{#filter.targetAmountStart} <= c.targetAmount ) " +
            "AND ( :#{#filter.targetAmountEnd} is null or  c.targetAmount <= :#{#filter.targetAmountEnd}) ")
    Page<Campaign> findCampaignPaging(@Param("filter") JpaFilterCampaignRequest filter,
                                      Pageable pageable);

    @Query(value = "select c from Campaign c")
    Page<Campaign> getPage(Pageable pageable);

    @Query(value = "select c from Campaign c " +
            "WHERE ( :keyword is null or c.title like %:keyword% ) " +
            "AND ( :category is null or :category = c.categoryId) ")
    Page<Campaign> searchCampaignByKeywordOrCategory(
            @Param("keyword") String keyword,
            @Param("category") Long category,
            Pageable pageable);
}
