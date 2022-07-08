package com.icptechno.admincore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);

    @Query("SELECT u FROM ApplicationUser u JOIN FETCH u.role r JOIN FETCH r.permissions p WHERE u.email = (:email)")
    Optional<ApplicationUser> findByEmailAndFetchPermissionsEagerly(String email);

    @Query("SELECT u FROM ApplicationUser u JOIN FETCH u.role r JOIN FETCH r.permissions p WHERE u.id = (:id)")
    Optional<ApplicationUser> findByIdAndFetchPermissionsEagerly(Long id);
}
