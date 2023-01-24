package com.fpt.edu.herofundbackend.repository;

import com.fpt.edu.herofundbackend.entity.Account;
import com.fpt.edu.herofundbackend.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a from Account a where a.id = :id and a.status = :status")
    Optional<Account> getAccountByIdAndStatus(@Param("id") long id, @Param("status") int status);

    @Query(value = "select a from Account a where a.username = :username and a.status = :status")
    Optional<Account> getAccountByUsernameAndStatus(@Param("username") String username, @Param("status") int status);

    @Query(value = "select a from Account a where a.username = :username and a.password = :password and a.status = :status")
    Optional<Account> getAccountByUsernameAndPasswordEncodeAndStatus(
            @Param("username") String username,
            @Param("password") String password,
            @Param("status") int status);

    @Query(value = "select a from Account a where a.status = :status")
    Page<Account> getPageAccountByStatus(@Param("status") int status, Pageable pageable);
}
