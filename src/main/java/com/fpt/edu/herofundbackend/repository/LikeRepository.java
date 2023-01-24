package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.Comment;
import com.fpt.edu.herofundbackend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l where l.id = :id and l.status = :status")
    Optional<Like> getLikeByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select l from Like l where l.accountId = :accountId and l.itemId = :itemId and l.typeId = :typeId")
    Optional<Like> getLikeByAccountIdAndItemIdAndTypeId(@Param("accountId") long accountId,
                                                          @Param("itemId") long itemId,
                                                          @Param("typeId") long typeId);
}
