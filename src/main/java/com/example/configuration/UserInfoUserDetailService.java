package com.example.configuration;

import com.example.configuration.UserInfoUserDetails;
import com.example.entity.UserInfo;
import com.example.repository.IUserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class UserInfoUserDetailService implements UserDetailsService {

    @Autowired
    private IUserInfoRepository iUserInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = iUserInfoRepository.findByName(username);
        return userInfo.map(UserInfoUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("user not found " + username));
    }
}
