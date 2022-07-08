package com.icptechno.admincore.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_token")
public class Token {

    @EmbeddedId
    private TokenId tokenId;

    private Date expiresAt;

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
