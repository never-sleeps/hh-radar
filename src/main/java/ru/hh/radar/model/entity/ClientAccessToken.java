package ru.hh.radar.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 *
 * {
 *     "access_token": "{access_token}",
 *     "token_type": "bearer",
 *     "expires_in": 1209600,
 *     "refresh_token": "{refresh_token}"
 * }
 */

@Builder
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

//    @NotNull
//    @Column(name = "get_token_time", nullable = false)
//    private ZonedDateTime time;

    @NotNull
    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "expires_in")
    private Integer expiresIn;

    @NotNull
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}