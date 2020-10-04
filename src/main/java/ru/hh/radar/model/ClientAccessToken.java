package ru.hh.radar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_access_tokens")
public class ClientAccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "get_token_time", nullable = false)
    private ZonedDateTime time;

    @NotNull
    @Column(name = "accessToken", nullable = false)
    private String accessToken;

    @Column(name = "tokenType")
    private String tokenType;

    @Column(name = "expiresIn")
    private Integer expiresIn;

    @NotNull
    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    @Column(name = "scope")
    private String scope;

    @Column(name = "rawResponse", nullable = false)
    private String rawResponse;

    @JoinColumn(name = "user")
    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
