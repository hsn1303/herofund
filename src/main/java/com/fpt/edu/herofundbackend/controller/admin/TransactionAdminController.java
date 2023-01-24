package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.transaction.FilterTransactionRequest;
import com.fpt.edu.herofundbackend.dto.transaction.TransactionDto;
import com.fpt.edu.herofundbackend.dto.transaction.TransactionResponseJpaQuery;
import com.fpt.edu.herofundbackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin/transactions")
public class TransactionAdminController {

    private final TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET)
    public PageDto<TransactionDto> getPage(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return transactionService.getPage(offset, limit);
    }

    @RequestMapping(path = "search", method = RequestMethod.POST)
    public PageDto<TransactionResponseJpaQuery> search(@RequestBody FilterTransactionRequest request) {
        if (request.getOffset() <= 0) request.setOffset(1);
        if (request.getLimit() <= 0) request.setLimit(10);
        return transactionService.searchAdmin(request);
    }

    @RequestMapping(path = "campaign", method = RequestMethod.GET)
    public PageDto<TransactionDto> search(
            @RequestParam(name = "campaignId") long campaignId,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        return transactionService.getPageTransactionByCampaignId(campaignId, offset, limit);
    }
}
