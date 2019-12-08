package com.msh.mrfix.services;

import com.msh.mrfix.models.User;
import com.msh.mrfix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


//Crea un User del tipo UserDetailsService a partir de nuestro usuario que saca de base de datos
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userR;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User u = userR.findUserByName(s);

            return this.userBuilder(u.getName(), new BCryptPasswordEncoder().encode(u.getPassword()), u.getRol());
        }catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
    }

    private org.springframework.security.core.userdetails.User userBuilder(String username, String password, String... roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return new org.springframework.security.core.userdetails.User(
                username, password,true, true,
                true, true, authorities);
    }
}
