package ru.hh.radar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hh.radar.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
