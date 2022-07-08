package com.icptechno.admincore.user;

import com.icptechno.admincore.common.ResourceStatus;
import com.icptechno.admincore.user.events.UserCreateEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    final private BCryptPasswordEncoder passwordEncoder;

    final private ApplicationEventPublisher applicationEventPublisher;

    final private UserRepository userRepository;

    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<ApplicationUser> getAll() {
        return userRepository.findAll();
    }

    public ApplicationUser getOneById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public ApplicationUser getOneByIdWithPermissions(Long id) {
        ApplicationUser applicationUser = userRepository.findByIdAndFetchPermissionsEagerly(id).orElseThrow();
        if (applicationUser.getRole().getStatus() == ResourceStatus.INACTIVE) {
            applicationUser.getRole().setPermissions(new HashSet<>());
        }
        return applicationUser;
    }

    public ApplicationUser getOneByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public ApplicationUser getOneByEmailWithPermissions(String email) {
        return userRepository.findByEmailAndFetchPermissionsEagerly(email).orElseThrow();
    }

    public ApplicationUser create(ApplicationUser user) {

        // check if password exists
        if (user.getPassword() == null || Objects.equals(user.getPassword(), "")) {
            user.setPassword("");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user = userRepository.save(user);

        user = userRepository.findById(user.getId()).orElse(user);

        // publish user create event
        UserCreateEvent userCreateEvent = new UserCreateEvent(this, user);
        applicationEventPublisher.publishEvent(userCreateEvent);

        return user;
    }

    public void updateById(Long id, ApplicationUser user) {
        user.setId(id);

        userRepository.save(user);
    }

    public void updateStatusById(Long id, UserStatus status) {

        ApplicationUser user = userRepository.findById(id).orElseThrow();
        user.setStatus(status);

        // emit update user status event

        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void updatePasswordById(Long id, String password) {
        ApplicationUser user = userRepository.findById(id).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
