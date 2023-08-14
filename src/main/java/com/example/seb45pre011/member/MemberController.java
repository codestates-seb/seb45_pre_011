package com.example.seb45pre011.member;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class MemberController {


    private MemberService service;
    private MemberMapper mapper;

    public MemberController(MemberService service, MemberMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PostMapping("/users/signup")
    public ResponseEntity postMember(@RequestBody MemberDto.post postDto) {
        Member member = mapper.memberPostDtoToMember(postDto);
        Member saveMember = service.createMember(member);

        return new ResponseEntity<>(saveMember.getEmail(), HttpStatus.CREATED);
    }

    @GetMapping("/users/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberDto.login loginDto){
        Member member = mapper.memberloginDtoToMember(loginDto);
        String jwtToken = service.loginMember(member);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);

        return ResponseEntity.ok()
                .header(String.valueOf(headers))
                .body("Login Successful");

    }
}
