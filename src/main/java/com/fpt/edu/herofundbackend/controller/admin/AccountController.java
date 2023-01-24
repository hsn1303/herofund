package com.fpt.edu.herofundbackend.controller.admin;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.auth.AccountDto;
import com.fpt.edu.herofundbackend.dto.auth.AccountWithProfile;
import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Validated
@CrossOrigin("*")
@RequestMapping("api/v1/admin/accounts")
public class AccountController {

    private final AuthenticateService authenticateService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<AccountDto>> getPage(
            @RequestParam(name = "offset", required = false, defaultValue = "1") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
    ) {
        if (offset <= 0) offset = 1;
        if (limit <= 0) offset = 10;
        PageDto<Account> page = authenticateService.getPage(offset, limit);
        return ResponseEntity.ok(PageDto.<AccountDto>builder()
                .items(page.getItems().stream().map(AccountDto::new).collect(Collectors.toList()))
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build());
    }

    @RequestMapping(path = "update/status", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateStatus(
            @RequestParam(name = "accountId") long accountId,
            @Min(value = 0, message = SystemConstant.Message.STATUS_VALIDATION)
            @Max(value = 1, message = SystemConstant.Message.STATUS_VALIDATION)
            @RequestParam(name = "status") int status
    ) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.UPDATE_FAIL);
        Account account = authenticateService.updateStatus(accountId, status);
        if (account == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(new AccountDto(account), SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public ResponseEntity<AccountWithProfile> detail(@RequestParam(name = "accountId") long accountId) {
        return ResponseEntity.ok(authenticateService.getDetail(accountId));
    }

}
