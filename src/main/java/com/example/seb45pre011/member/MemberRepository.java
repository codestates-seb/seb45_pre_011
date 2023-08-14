package com.example.seb45pre011.member;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query(value = "SELECT * FROM Member WHERE email = :email", nativeQuery = true)
    Optional<Member> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM Member WHERE id = :id", nativeQuery = true)
    Optional<Member> findById(@Param("id") String id);






}
