package com.springboot.blog.bootstrap;

import com.springboot.blog.models.user.Role;
import com.springboot.blog.models.user.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

//@Component
public class Bootstrap implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Bootstrap(UserRepository userRepository,
                     RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //All roles require to be preceded by ROLE_ to be recognized as a role
        Role admin = new Role(1L, "ROLE_ADMIN");
        Role user = new Role(2L, "ROLE_USER");
        roleRepository.saveAll(List.of(admin, user));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User adnan = new User(2L, "Adnan", "adnxn@gmail.com", "adnan", passwordEncoder.encode("abxc"), Set.of(admin));
        User abc = new User(3L, "Abc", "abc@gmail.com", "abcxd", passwordEncoder.encode("password"),  Set.of(user));
        userRepository.save(adnan);
        userRepository.save(abc);


    }
}
