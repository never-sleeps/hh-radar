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

//    @JoinColumn(name = "user")
//    @OneToOne
//    private User user;
}
