package org.kpax.oauth2.service;

import org.kpax.oauth2.model.CustomUserDetails;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return user.map(CustomUserDetails::new).get();
    }

    public boolean exists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return user.get();
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + name));
        return user.get();
    }
}
