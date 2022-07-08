package com.icptechno.admincore.user;

import com.icptechno.admincore.common.Auditable;
import com.icptechno.admincore.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "um_user")
public class ApplicationUser extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String mobileNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role")
    private Role role;

    @Column(name = "user_status")
    @Enumerated(value = EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    private String password;

    private String avatarLink;

    private Boolean logged = false;

    public ApplicationUser() {
        super();
    }

    public ApplicationUser(Long id, String firstName, String lastName, String email, String password, String mobileNumber, Role role, UserStatus status) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.status = status;
    }


    @Override
    public String toString() {
        return "ApplicationUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", logged=" + logged +
                '}';
    }
}
