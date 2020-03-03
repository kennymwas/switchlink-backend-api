package com.ke.switchlinkafrica.transactionprocessing.services.impl;

import com.ke.switchlinkafrica.transactionprocessing.entities.UserData;
import com.ke.switchlinkafrica.transactionprocessing.repositories.UserRepository;
import com.ke.switchlinkafrica.transactionprocessing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {


    @Autowired
    private UserRepository userRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.findByUserNameAndEnabled(username,1);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }


    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserData save(UserData user) {
        return userRepository.save(user);
    }

    @Override
    public UserData getUser(String userName, int enabled) {
        return userRepository.findByUserNameAndEnabled(userName,enabled);
    }

    @Override
    public Page<UserData> findAll(Pageable pg) {
        return userRepository.findAll(pg);
    }

}
