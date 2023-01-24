package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.transaction.*;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.entity.Transaction;
import com.fpt.edu.herofundbackend.enums.PaymentStatusEnum;
import com.fpt.edu.herofundbackend.repository.CampaignRepository;
import com.fpt.edu.herofundbackend.repository.PaymentChannelRepository;
import com.fpt.edu.herofundbackend.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactionService {
    private final CampaignRepository campaignRepository;
    private final PaymentChannelRepository paymentChannelRepository;

    private final TransactionRepository transactionRepository;

    public PageDto<TransactionDto> getPage(int offset, int limit) {
        Sort sendingTime = Sort.by(Sort.Direction.DESC, SystemConstant.FIELD.SENDING_TIME);
        Pageable pageable = PageRequest.of(offset - 1, limit, sendingTime);
        Page<Transaction> page = transactionRepository.getPage(pageable);
        List<TransactionDto> dtos = page.getContent().stream().map(this::convertTransactionToDto).collect(Collectors.toList());
        return PageDto.<TransactionDto>builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .items(dtos)
                .offset(offset)
                .limit(limit)
                .build();
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public PageDto<TransactionDto> getPageTransactionByToken(FilterTransactionRequest request, long accountId) {
        Sort sendingTime = Sort.by(Sort.Direction.DESC, SystemConstant.FIELD.SENDING_TIME);
        Pageable pageable = PageRequest.of(request.getOffset() - 1, request.getLimit(), sendingTime);
        JpaFilterTransactionRequest jpaRequest = new JpaFilterTransactionRequest(request);
        Page<Transaction> page = transactionRepository.findTransactionPagingByAccountId(accountId, jpaRequest, pageable);
        List<TransactionDto> dtos = page.getContent().stream().map(this::convertTransactionToDto).collect(Collectors.toList());
        return PageDto.<TransactionDto>builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .items(dtos)
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }


    public List<Transaction> getTopDonateByCampaignId(int campaignId, int number) {
        Sort amount = Sort.by(Sort.Direction.DESC, SystemConstant.FIELD.AMOUNT);
        Pageable pageable = PageRequest.of(0, number, amount);
        return transactionRepository.getTopTransactionByCampaignId(campaignId, pageable);
    }

    public PageDto<TransactionResponseJpaQuery> searchAdmin(FilterTransactionRequest request) {
        JpaFilterTransactionRequest jpaRequest = new JpaFilterTransactionRequest(request);
        Sort sendingTime = Sort.by(Sort.Direction.DESC, SystemConstant.FIELD.SENDING_TIME);
        Pageable pageable = PageRequest.of(request.getOffset() - 1, request.getLimit(), sendingTime);
        Page<TransactionResponseJpaQuery> page = transactionRepository.findTransactionPaging(jpaRequest, pageable);
        return PageDto.<TransactionResponseJpaQuery>builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .items(page.getContent())
                .offset(request.getOffset())
                .limit(request.getLimit())
                .build();
    }

    public List<TransactionBasicDto> getAllTransactionByCampaign(long campaignId) {
        return transactionRepository.getAllTransactionByCampaignIdAndStatus(campaignId, PaymentStatusEnum.DONE.name())
                .stream()
                .map(TransactionBasicDto::new)
                .collect(Collectors.toList());
    }

    public PageDto<TransactionDto> getPageTransactionByCampaignId(long campaignId, int offset, int limit) {
        Sort sendingTime = Sort.by(Sort.Direction.DESC, SystemConstant.FIELD.SENDING_TIME);
        Pageable pageable = PageRequest.of(offset - 1, limit, sendingTime);
        Page<Transaction> page = transactionRepository.getTransactionByCampaignId(campaignId, pageable);
        List<TransactionDto> dtos = page.getContent().stream().map(this::convertTransactionToDto).collect(Collectors.toList());
        return PageDto.<TransactionDto>builder()
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .items(dtos)
                .offset(offset)
                .limit(limit)
                .build();
    }

    private TransactionDto convertTransactionToDto(Transaction t) {
        TransactionDto dto = new TransactionDto();
        dto.setAmount(t.getAmount());
        dto.setPaypalTransactionId(t.getPaypalTransactionId());
        dto.setCampaign(Objects.requireNonNull(campaignRepository.findById(t.getCampaignId()).orElse(null)).getTitle());
        dto.setSenderName(t.getSenderName());
        dto.setMessage(t.getMessage());
        dto.setPaymentChannel(Objects.requireNonNull(paymentChannelRepository.findById(t.getPaymentChannel()).orElse(null)).getName());
        dto.setPaymentStatus(t.getPaymentStatus());
        dto.setSendingTime(t.getSendingTime());
        return dto;
    }
}
