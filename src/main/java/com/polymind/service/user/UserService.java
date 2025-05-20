package com.polymind.service.user;
import com.polymind.entity.user.User;
import com.polymind.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveIfNotExists(String id, String email, String name,String provider,String profileImage) {

        return userRepository.findById(id).orElseGet(() -> {
            User user = new User(id, name,email,provider,profileImage);
            return userRepository.save(user);
        });
    }
}
