package com.fpt.edu.herofundbackend.controller;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.auth.AccountDtoResponse;
import com.fpt.edu.herofundbackend.dto.auth.AuthenticateRequest;
import com.fpt.edu.herofundbackend.dto.auth.AuthenticateResponse;
import com.fpt.edu.herofundbackend.dto.auth.FormRegisterRequest;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignDto;
import com.fpt.edu.herofundbackend.dto.campaign.CampaignRequest;
import com.fpt.edu.herofundbackend.dto.campaign.FilterCampaignRequest;
import com.fpt.edu.herofundbackend.dto.profile.ProfileDto;
import com.fpt.edu.herofundbackend.dto.profile.ProfileRequest;
import com.fpt.edu.herofundbackend.dto.transaction.FilterTransactionRequest;
import com.fpt.edu.herofundbackend.dto.transaction.TransactionDto;
import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Campaign;
import com.fpt.edu.herofundbackend.entity.Profile;
import com.fpt.edu.herofundbackend.exception.MyBadRequestException;
import com.fpt.edu.herofundbackend.security.JwtUtils;
import com.fpt.edu.herofundbackend.service.AuthenticateService;
import com.fpt.edu.herofundbackend.service.CampaignService;
import com.fpt.edu.herofundbackend.service.ProfileService;
import com.fpt.edu.herofundbackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TransactionService transactionService;
    private final CampaignService campaignService;
    private final ProfileService profileService;
    private final AuthenticateService accountService;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(path = "authenticate", method = RequestMethod.POST)
    public AuthenticateResponse authenticate(@RequestBody @Valid AuthenticateRequest request) {
        Account account = accountService.getAccountByUsername(request.getUsername());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new UsernameNotFoundException(SystemConstant.Message.ACCOUNT_OR_PASSWORD_INVALID);
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = accountService.loadUserByUsername(request.getUsername());
        if (user != null) {
            return jwtUtils.createToken(user);
        }
        throw new UsernameNotFoundException(SystemConstant.Message.ACCOUNT_OR_PASSWORD_INVALID);
    }

    @RequestMapping(path = "sign-up", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> SignUp(@RequestBody @Valid FormRegisterRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SIGN_UP_FAIL);
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            response.setFailResponse(SystemConstant.Message.PASSWORD_NOT_EQUAL_CONFIRM_PASSWORD);
            return ResponseEntity.ok(response);
        }
        String passwordEncoder = bCryptPasswordEncoder.encode(request.getPassword());
        Account account = accountService.signUp(request.getUsername(), passwordEncoder);
        if (null == account) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessResponse(SystemConstant.Message.SIGN_UP_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "profile", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getProfile(HttpServletRequest request) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SYSTEM_ERROR);
        String username = request.getUserPrincipal().getName();
        Account account = accountService.getAccountByUsername(username);
        Profile profile = profileService.getProfileById(account.getId());
        AccountDtoResponse accountDto = new AccountDtoResponse(account.getId(), username);
        if (profile == null) {
            return ResponseEntity.ok(response);
        }
        ProfileDto dto = new ProfileDto(profile);
        dto.setAccount(accountDto);
        response.setSuccessWithDataResponse(dto);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "profile", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> saveProfile(@RequestBody ProfileRequest request, HttpServletRequest servletRequest) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.SAVE_PROFILE_FAIL);
        String username = servletRequest.getUserPrincipal().getName();
        Account account = accountService.getAccountByUsername(username);
        Profile profile = profileService.saveProfile(request, account.getId());
        if (null == profile) {
            return ResponseEntity.ok().body(response);
        }
        response.setSuccessWithDataResponse(profile, SystemConstant.Message.SAVE_PROFILE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "transactions", method = RequestMethod.POST)
    public PageDto<TransactionDto> getPageTransactionByToken(
            @RequestBody FilterTransactionRequest request, HttpServletRequest req) {
        if (request.getOffset() <= 0) request.setOffset(1);
        if (request.getLimit() <= 0) request.setLimit(10);
        Account account = accountService.getAccountByUsername(req.getUserPrincipal().getName());
        try {
            return transactionService.getPageTransactionByToken(request, account.getId());
        } catch (Exception e) {
            throw new MyBadRequestException(HttpStatus.BAD_REQUEST.name());
        }
    }

    @RequestMapping(path = "campaign", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> createCampaign(@RequestBody CampaignRequest request, HttpServletRequest req) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.SAVE_FAIL)
                .build();
        Campaign campaign = campaignService.save(request, accountService.getAccountByUsername(req.getUserPrincipal().getName()));
        if (campaign == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(campaign, SystemConstant.Message.SAVE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(path = "campaign", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateMyCampaign(@RequestBody CampaignRequest request, HttpServletRequest req) {
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message(SystemConstant.Message.UPDATE_FAIL)
                .build();
        Campaign campaign = campaignService.updateCampaignOfUser(request, accountService.getAccountByUsername(req.getUserPrincipal().getName()));
        if (campaign == null) {
            return ResponseEntity.ok(response);
        }
        response.setSuccessWithDataResponse(campaign, SystemConstant.Message.UPDATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "campaign/list", method = RequestMethod.POST)
    public PageDto<CampaignDto> getPageCampaignByToken(
            @RequestBody FilterCampaignRequest request, HttpServletRequest req) {
        if (request.getOffset() <= 0) request.setOffset(1);
        if (request.getLimit() <= 0) request.setLimit(10);
        Account account = accountService.getAccountByUsername(req.getUserPrincipal().getName());
        try {
            request.setAccountId(account.getId());
            return campaignService.findCampaignPaging(request);
        } catch (Exception e) {
            throw new MyBadRequestException(HttpStatus.BAD_REQUEST.name());
        }
    }

}
