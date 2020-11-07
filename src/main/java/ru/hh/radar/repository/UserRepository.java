package ru.hh.radar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hh.radar.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId (Long userId);
}
