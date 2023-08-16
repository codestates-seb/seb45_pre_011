package com.example.seb45pre011.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberMapper mapper;
    private final MemberService service;

    @PostMapping("/users/signup")
    public ResponseEntity postMember(@RequestBody MemberDto.post postDto){
        Member saveMember = service.createMember(mapper.memberPostDtoToMember(postDto));
        return new ResponseEntity(saveMember.getNick(), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity loginMember(@RequestBody MemberDto.login loginDto){
        String jwtToken = service.loginMember(mapper.memberloginDtoToMember(loginDto));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);

        return ResponseEntity.ok()
                .headers(headers)
                .body("Login Successful");


    }
}
