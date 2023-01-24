package com.fpt.edu.herofundbackend.service;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.PageDto;
import com.fpt.edu.herofundbackend.dto.auth.AccountWithProfile;
import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Profile;
import com.fpt.edu.herofundbackend.enums.BaseStatusEnum;
import com.fpt.edu.herofundbackend.enums.RoleEnum;
import com.fpt.edu.herofundbackend.exception.MyNotFoundException;
import com.fpt.edu.herofundbackend.repository.AccountRepository;
import com.fpt.edu.herofundbackend.repository.ProfileRepository;
import com.fpt.edu.herofundbackend.util.SystemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticateService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByUsernameAndStatus(username, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new UsernameNotFoundException(SystemConstant.Message.ACCOUNT_OR_PASSWORD_INVALID));
        return new User(account.getUsername(), account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(account.getRole())));
    }

    public Account signUp(String username, String passwordEncoder) {
        Account exist = accountRepository.getAccountByUsernameAndStatus(username, BaseStatusEnum.ENABLE.getKey()).orElse(null);
        if (null != exist) {
            throw new MyNotFoundException(SystemConstant.Message.ACCOUNT_EXIST);
        }
        try {

            Account account = accountRepository
                    .save(Account.builder()
                            .username(username)
                            .password(passwordEncoder)
                            .role(RoleEnum.ROLE_USER.name())
                            .build());
            account.setStatus(BaseStatusEnum.ENABLE.getKey());
            profileRepository.save(Profile.builder().accountId(account.getId()).build());
            return account;
        } catch (Exception e) {
            return null;
        }
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.getAccountByUsernameAndStatus(username, BaseStatusEnum.ENABLE.getKey())
                .orElseThrow(() -> new UsernameNotFoundException(SystemConstant.Message.ACCOUNT_OR_PASSWORD_INVALID));
    }

    public long getAccountIdByUsername(String username) {
        try {
            Account account = accountRepository.getAccountByUsernameAndStatus(username, BaseStatusEnum.ENABLE.getKey())
                    .orElse(null);
            return account == null ? 0 : account.getId();
        }catch (Exception e){
            return 0;
        }
    }

    public PageDto<Account> getPage(int offset, int limit) {
        Pageable pageable = SystemUtils.createPageable(offset, limit);
        Page<Account> items = accountRepository.getPageAccountByStatus(BaseStatusEnum.ENABLE.getKey(), pageable);
        return PageDto.<Account>builder()
                .items(items.getContent())
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .offset(offset)
                .limit(limit)
                .build();
    }

    public Account updateStatus(long accountId, int status) {
        Account exist = accountRepository.findById(accountId)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ACCOUNT_NOT_FOUND));
        exist.setStatus(status);
        try {
            return accountRepository.save(exist);
        } catch (Exception e) {
            return null;
        }
    }

    public AccountWithProfile getDetail(long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new MyNotFoundException(SystemConstant.Message.ACCOUNT_NOT_FOUND));
        Profile profile = profileRepository.findProfileByAccountId(accountId).orElse(null);
        if (profile == null) {
            profile = new Profile();
            profile.setAccountId(accountId);
        }
        return new AccountWithProfile(account, profile);
    }

    public Account findByIdAndStatus(long accountId, int key) {
        return accountRepository.getAccountByIdAndStatus(accountId, key).orElse(null);
    }
}
