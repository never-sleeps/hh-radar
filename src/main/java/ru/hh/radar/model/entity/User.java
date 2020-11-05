package ru.hh.radar.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "username", unique = true)
    private String username;

    @ToString.Exclude
    @Column(name = "authorization_code")
    private String authorizationCode;

    @ToString.Exclude
    @OneToOne(targetEntity = ClientAccessToken.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private ClientAccessToken clientAccessToken;

    @ToString.Exclude
    @OneToOne(targetEntity = SearchParameters.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "search_id")
    private SearchParameters searchParameters;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AutoPublishingResume> autoPublishingVacancies;

    public User(String username) {
        this.username = username;
    }

    public boolean isAuthorized() {
        return clientAccessToken != null;
    }
}
