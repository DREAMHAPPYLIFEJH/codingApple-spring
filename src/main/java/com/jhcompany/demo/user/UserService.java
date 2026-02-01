package com.jhcompany.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signIn(String id, String pw) {
        User user = new User();
        user.setUserid(id);
        user.setPassword(pw);
        userRepository.save(user);
    }
}
