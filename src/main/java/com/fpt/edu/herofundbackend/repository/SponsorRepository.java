package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.Sponsor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {

    @Query(value = "select s from Sponsor s where s.id = :id and s.status = :status")
    Optional<Sponsor> getSponsorByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select s from Sponsor s where s.status = :status")
    Page<Sponsor> getPageByStatus(@Param("status") int status, Pageable pageable);

    @Query(value = "select s from Sponsor s where s.id in (:ids)")
    List<Sponsor> findByIds(@Param("ids") List<Long> ids);

    @Query(value = "select s from Sponsor s")
    Page<Sponsor> getPageSponsors(Pageable pageable);

    @Query(value = "select s from Sponsor s where s.status = :status")
    List<Sponsor> findAllByStatus(@Param("status") int status);

    @Query(value = "select s from Sponsor s " +
            "where ( :keyword is null or concat(s.name, s.description, s.detail) like %:keyword% ) " +
            "AND ( :status is null or :status = s.status ) " +
            "order by s.createdAt desc")
    Page<Sponsor> findPageSponsor(@Param("keyword") String keyword,
                                                    @Param("status") Integer status,
                                                    Pageable pageable);
}
