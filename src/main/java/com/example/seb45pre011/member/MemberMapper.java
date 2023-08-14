package com.example.seb45pre011.member;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.post postDto);
    Member memberloginDtoToMember(MemberDto.login loginDto);
}
