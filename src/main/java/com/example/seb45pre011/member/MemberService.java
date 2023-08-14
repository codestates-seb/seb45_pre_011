package com.example.seb45pre011.member;

import com.example.seb45pre011.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository repository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;





    public Member createMember(Member member){
        verifyExist(member.getEmail());
        String password = passwordEncoder.encode(member.getPassword());
        member.setPassword(password);
        member.setRoles(Collections.singletonList("ROLE_USER"));

        return repository.save(member);
    }


    public String loginMember(Member member) {
        Optional<Member> findMember = repository.findById(member.getId());
        Member existMember = findMember.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));
        if (existMember.getId().equals( member.getId()) && passwordEncoder.matches(member.getPassword(), findMember.get().getPassword())) {
            return jwtProvider.createToken(member.getId(), member.getRoles());
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }

    private Member verifyExist(String email){
        return repository.findByEmail(email).orElseThrow(IllegalAccessError::new);
    }
//
//    public void verifyExist(String email) {
//        Optional<Member> findUser = repository.findByEmail(email);
//
//        findUser.ifPresent(user->{ throw new IllegalArgumentException("이미 가입된 아이디 입니다.");
//        });
////        if (findUser.isPresent() && findUser.get() != null) {
////            throw new IllegalArgumentException("이미 가입된 아이디 입니다.");
////        }
////
////        if (!findUser.isPresent()) {
////            return;
////        }
//    }
}

