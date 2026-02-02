package com.jhcompany.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signIn(String id, String pw, String displayName) {
        Member member = new Member();
        member.setUserid(id);
        var hash = passwordEncoder.encode(pw);
        member.setPassword(hash);
        member.setDisplayName(displayName);
        userRepository.save(member);
    }
}
