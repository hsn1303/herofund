package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select c from Category c where c.status = :status")
    Page<Category> getPageByStatus(@Param("status") int status, Pageable pageable);

    @Query(value = "select c from Category c " +
            "where ( :keyword is null or c.name like %:keyword% ) " +
            "and (:status is null  or c.status = :status)")
    Page<Category> searchCategories(
            @Param("status") Integer status,
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query(value = "select c from Category c where c.status = :status")
    List<Category> getAllByStatus(@Param("status") int status);

    @Query(value = "select c from Category c where c.id = :id and c.status = :status")
    Optional<Category> findByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select c from Category c where c.id in (:ids)")
    List<Category> findByIds(@Param("ids") List<Long> ids);


}
