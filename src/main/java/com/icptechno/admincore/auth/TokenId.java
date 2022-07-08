package com.icptechno.admincore.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenId implements Serializable {

    @Enumerated(value = EnumType.STRING)
    private TokenType type;
    private String tokenHash;
    private Long userId;

    @Override
    public String toString() {
        return "TokenId{" +
                "type=" + type +
                ", tokenHash='" + tokenHash + '\'' +
                ", userId=" + userId +
                '}';
    }
}
