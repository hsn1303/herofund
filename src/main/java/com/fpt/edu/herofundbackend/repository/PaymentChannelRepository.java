package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.PaymentChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentChannelRepository extends JpaRepository<PaymentChannel, Long> {

    @Query(value = "select p from PaymentChannel p where p.id = :id and p.status = :status")
    Optional<PaymentChannel> getPaymentChannelByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select p from PaymentChannel p where p.status = :status")
    List<PaymentChannel> getAllByStatus(@Param("status") int status);

    @Query(value = "select p from PaymentChannel p where p.id in (:ids)")
    List<PaymentChannel> getChannelByIds(@Param("ids") List<Long> ids);
}
