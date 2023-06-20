package com.springwiki.api.data.repository;

import com.springwiki.api.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User getByUid(String uid);

}
