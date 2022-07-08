package com.icptechno.admincore.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface TokenRepository extends JpaRepository<Token, TokenId> {

    @Transactional
    void deleteAllByExpiresAtBefore(Date date);

}
