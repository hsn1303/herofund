package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.FAQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {

    @Query(value = "select f from FAQ f where f.id = :id and f.status = :status")
    Optional<FAQ> getByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select f from FAQ f")
    Page<FAQ> getPage(Pageable pageable);

    @Query(value = "select count(f) from FAQ f where f.status = :status")
    long countByStatus(@Param("status") int status);

    @Query(value = "select f from FAQ f where f.id in (:ids)")
    List<FAQ> findByIds(@Param("ids") List<Long> ids);
}
