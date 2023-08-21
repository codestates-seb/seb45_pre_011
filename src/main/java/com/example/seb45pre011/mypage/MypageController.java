package com.example.seb45pre011.mypage;

import com.example.seb45pre011.comment.CommentService;
import com.example.seb45pre011.member.Member;
import com.example.seb45pre011.member.MemberDto;
import com.example.seb45pre011.member.MemberService;
import com.example.seb45pre011.post.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
public class MypageController {
    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    public MypageController(MemberService memberService, PostService postService, CommentService commentService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.postService = postService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity getUser() {
        // TODO Postman에서 "X-AUTH-TOKEN" 으로 넘어온 토큰 값을 통해 사용자 정보를 얻어야한다
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getName()!= null) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String email = userDetails.getUsername();
//            Member member = memberService.findMemberByEmail(email);
//            System.out.println(member.getUserId());
//            System.out.println(member.getPhone());
//            int userPostCount = postService.getUserPostCount(member);
////
//            // 답변 게시물 개수 조회
//            //int userAnswerCount = postService.getUserAnswerCount(member);
//
//            // 답글 개수 조회
//            int userCommentCount = commentService.getUserCommentCount(member);
//
//            return new ResponseEntity<>(
//                    myPageMapper.MemberAndCountsToResponseDto(member,userPostCount,userCommentCount),
//                    HttpStatus.OK);
//        } else {
//            System.out.println("No logged-in user found.");
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        Member member = memberService.getUserByAuthentication();
        if(member == null) {
            System.out.println("No logged-in user found.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        else{
            int userPostCount = postService.getUserPostCount(member);
            // 답글 개수 조회
            int userCommentCount = commentService.getUserCommentCount(member);
            return new ResponseEntity<>(
                    mapMemberToResponseDto(member,userPostCount,userCommentCount),
                    HttpStatus.OK);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity verifyUser(@RequestBody String password){
        Member member = memberService.getUserByAuthentication();
        // 비밀번호를 통해 사용자 검증
        boolean matches = passwordEncoder.matches(password, member.getPassword());

        if(matches)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PatchMapping("/update")
    public ResponseEntity  updateUser(@RequestBody MemberDto.Patch patch){
        // 이미 사용자 검증은 끝
        Member member = memberService.getUserByAuthentication();
        Member updatedMember = memberService.updateMember(member,patch);

        int userPostCount = postService.getUserPostCount(updatedMember);
        // 답글 개수 조회
        int userCommentCount = commentService.getUserCommentCount(updatedMember);
        return new ResponseEntity<>(
                mapMemberToResponseDto(updatedMember,userPostCount,userCommentCount),
                HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(){
        Member requestMember = memberService.getUserByAuthentication();
        Member deleteMember = memberService.findMemberByEmail(requestMember.getEmail());

        memberService.deleteMember(deleteMember);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 직접 매핑 메서드 구현
    private MyPageDto.ResponseDto mapMemberToResponseDto(Member member, int userPostCount, int userCommentCount) {
        MyPageDto.ResponseDto responseDto = new MyPageDto.ResponseDto();
        responseDto.setUserId(member.getUserId());
        responseDto.setEmail(member.getEmail());
        responseDto.setUsername(member.getUsername());
        responseDto.setGender(member.getGender());
        responseDto.setPhone(member.getPhone());
        responseDto.setNick(member.getNick());
        responseDto.setUserPostCount(userPostCount);
        responseDto.setUserCommentCount(userCommentCount);
        return responseDto;
    }
}
