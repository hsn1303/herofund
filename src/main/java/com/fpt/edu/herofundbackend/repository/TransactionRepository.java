package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.dto.transaction.JpaFilterTransactionRequest;
import com.fpt.edu.herofundbackend.dto.transaction.TransactionResponseJpaQuery;
import com.fpt.edu.herofundbackend.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select c from Transaction c")
    Page<Transaction> getPage(Pageable pageable);

    @Query(value = "select t from Transaction t where t.campaignId = :campaignId")
    List<Transaction> getTopTransactionByCampaignId(@Param("campaignId") long campaignId, Pageable pageable);

    @Query(value = "select new com.fpt.edu.herofundbackend.dto.transaction.TransactionResponseJpaQuery(" +
            "c.senderName, c.message, c.amount, c.sendingTime, t.title, p.name , c.paymentStatus, c.paypalTransactionId) " +
            "from Transaction c " +
            "inner join Campaign t on t.id = c.campaignId " +
            "inner join PaymentChannel p on p.id = c.paymentChannel " +
            "where ( :#{#filter.keyword} is null or concat(c.senderName, c.message,t.title, c.paypalTransactionId) like %:#{#filter.keyword}% ) " +
            "AND ( :#{#filter.startDateSendingTime} is null or :#{#filter.startDateSendingTime} <= c.sendingTime ) " +
            "AND ( :#{#filter.endDateSendingTime} is null or c.sendingTime <= :#{#filter.endDateSendingTime} ) " +
            "AND ( :#{#filter.campaignId} is null or :#{#filter.campaignId} = c.campaignId) " +
            "AND ( :#{#filter.paymentChannelId} is null or :#{#filter.paymentChannelId} = c.campaignId) " +
            "AND ( :#{#filter.accountId} is null or :#{#filter.accountId} = c.accountId) " +
            "AND ( :#{#filter.paymentStatus} is null or :#{#filter.paymentStatus} = c.paymentStatus) " +
            "AND ( :#{#filter.startAmount} is null or :#{#filter.startAmount} <= c.amount ) " +
            "AND ( :#{#filter.endAmount} is null or  c.amount <= :#{#filter.endAmount}) " +
            "order by c.sendingTime desc")
    Page<TransactionResponseJpaQuery> findTransactionPaging(@Param("filter") JpaFilterTransactionRequest filter,
                                                            Pageable pageable);

    @Query(value = "select t from  Transaction  t where t.campaignId = :campaignId and t.paymentStatus = :status order by t.sendingTime desc")
    List<Transaction> getAllTransactionByCampaignIdAndStatus(@Param(("campaignId")) long campaignId,
                                                             @Param("status") String status);


    @Query("select t from Transaction t " +
            "inner join Campaign c on c.id = t.campaignId " +
            "where :accountId is null or t.accountId = :accountId " +
            "and ( :#{#filter.keyword} is null or concat(t.senderName, t.message, c.title, t.paypalTransactionId) like %:#{#filter.keyword}% )" +
            "AND ( :#{#filter.startDateSendingTime} is null or :#{#filter.startDateSendingTime} <= t.sendingTime ) " +
            "AND ( :#{#filter.endDateSendingTime} is null or t.sendingTime <= :#{#filter.endDateSendingTime} ) " +
            "AND ( :#{#filter.campaignId} is null or :#{#filter.campaignId} = t.campaignId) " +
            "AND ( :#{#filter.paymentChannelId} is null or :#{#filter.paymentChannelId} = t.campaignId) " +
            "AND ( :#{#filter.accountId} is null or :#{#filter.accountId} = c.accountId) " +
            "AND ( :#{#filter.paymentStatus} is null or :#{#filter.paymentStatus} = t.paymentStatus) " +
            "AND ( :#{#filter.startAmount} is null or :#{#filter.startAmount} <= t.amount ) " +
            "AND ( :#{#filter.endAmount} is null or  t.amount <= :#{#filter.endAmount}) " +
            "order by t.sendingTime desc ")
    Page<Transaction> findTransactionPagingByAccountId(
            @Param("accountId") Long accountId,
            @Param("filter") JpaFilterTransactionRequest filter,
            Pageable pageable);

    @Query(value = "select t from Transaction t where t.orderId = :orderId")
    Optional<Transaction> getTransactionByPaypalOrderId(@Param("orderId") String orderId);

    @Query(value = "select t from Transaction t where t.campaignId = :campaignId")
    Page<Transaction> getTransactionByCampaignId(@Param("campaignId") long campaignId, Pageable pageable);
}
