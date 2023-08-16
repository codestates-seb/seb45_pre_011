//package com.example.seb45pre011.security;
//
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//public class PrincipalOAuth2DetailsService extends DefaultOAuth2UserService {
//
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        System.out.println(oAuth2User.getAttributes());
//
//
//        return oAuth2User;
//    }
//}
