package com.icptechno.admincore.common.security;


import com.icptechno.admincore.user.ApplicationUser;
import com.icptechno.admincore.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        try {
            ApplicationUser user = userService.getOneByIdWithPermissions(id);
            return new AppUserDetail(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(id.toString());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            ApplicationUser user = userService.getOneByEmailWithPermissions(s);
            System.out.println(user.getPassword());
            return new AppUserDetail(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FAILED TO FIND USER!");
            throw new UsernameNotFoundException(s);
        }
    }
}
