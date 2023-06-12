package com.kaab.service;

import com.kaab.dao.UserDao;
import com.kaab.entity.JwtRequest;
import com.kaab.entity.JwtResponse;
import com.kaab.entity.RoleType;
import com.kaab.entity.User;
import com.kaab.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userDao.findById(userName).get();
        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        if(user.getRoleType().equals("Admin")) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleType()));
//        }
//        if(user.getRoleType() != null && user.getRoleType().equals("TEACHER") && user.getActivationStatus().equals("ACTIVE")) {
//            System.out.println("techer _ activate");
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleType()));
//        }
//        String x = "ROLE_" + user.getRoleType();
//        System.out.println(x + " " + user.getRoleType());

        if(user.getRoleType() != null &&  user.getActivationStatus().equals("ACTIVE")) {
            String role =  user.getRoleType().toString();
            authorities.add(new SimpleGrantedAuthority(role));

        }

        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
