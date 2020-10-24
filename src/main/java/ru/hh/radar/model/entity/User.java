package ru.hh.radar.model.entity;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @ToString.Exclude
    @Column(name = "authorization_code")
    private String authorizationCode;

    @ToString.Exclude
    @OneToOne(targetEntity = ClientAccessToken.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private ClientAccessToken clientAccessToken;

    public User(String username) {
        this.username = username;
    }

    public boolean isAuthorized() {
        return clientAccessToken != null;
    }
}
