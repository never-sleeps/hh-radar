package ru.hh.radar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "username")
    private String username;

//    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "id")
//    @JsonIgnore
//    private ClientAccessToken clientAccessToken = null;

    public User(Long chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }
}
