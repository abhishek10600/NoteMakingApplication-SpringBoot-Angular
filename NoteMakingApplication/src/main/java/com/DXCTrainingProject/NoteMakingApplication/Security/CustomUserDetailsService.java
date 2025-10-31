package com.DXCTrainingProject.NoteMakingApplication.Security;

import com.DXCTrainingProject.NoteMakingApplication.Entity.User;
import com.DXCTrainingProject.NoteMakingApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User with this username not found."));

        var authorities = Arrays.stream(user.getRoles().split(","))
                .map(String::trim)
                .filter(s-> !s.isEmpty())
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
