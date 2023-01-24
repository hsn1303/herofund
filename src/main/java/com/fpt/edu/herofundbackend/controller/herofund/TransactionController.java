package com.fpt.edu.herofundbackend.controller.herofund;

import com.fpt.edu.herofundbackend.dto.transaction.TransactionBasicDto;
import com.fpt.edu.herofundbackend.entity.Transaction;
import com.fpt.edu.herofundbackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @RequestMapping(path = "top", method = RequestMethod.GET)
    public List<Transaction> getTopDonateByCampaignId(
            @RequestParam(name = "number", required = false, defaultValue = "10") int number,
            @RequestParam(name = "campaignId") int campaignId) {
        if (number <= 0) number = 10;
        return transactionService.getTopDonateByCampaignId(campaignId, number);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TransactionBasicDto>> getAllTransaction(@RequestParam(name = "campaignId") long campaignId) {
        return ResponseEntity.ok(transactionService.getAllTransactionByCampaign(campaignId));
    }
}
