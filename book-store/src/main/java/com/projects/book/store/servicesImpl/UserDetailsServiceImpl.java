package com.projects.book.store.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projects.book.store.constants.Messages;
import com.projects.book.store.exception.NotFound;
import com.projects.book.store.model.User;
import com.projects.book.store.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new NotFound(Messages.USER_NOT_FOUND));
    }

}
