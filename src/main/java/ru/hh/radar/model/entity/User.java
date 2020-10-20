package ru.hh.radar.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hh.radar.model.entity.ClientAccessToken;

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

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "authorization_code")
    private String authorizationCode;

    // Задает поле, по которому происходит объединение с таблицей для хранения связанной сущности
    @OneToOne(targetEntity = ClientAccessToken.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id")
    private ClientAccessToken clientAccessToken;

    public User(Long chatId, String username, String authorizationCode) {
        this.chatId = chatId;
        this.username = username;
        this.authorizationCode = authorizationCode;
    }

    public boolean isAuthorized() {
        return clientAccessToken != null;
    }
}
