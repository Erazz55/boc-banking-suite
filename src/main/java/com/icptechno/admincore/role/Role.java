package com.icptechno.admincore.role;

import com.icptechno.admincore.common.CreateOnlyAuditable;
import com.icptechno.admincore.common.ResourceStatus;
import com.icptechno.admincore.permission.Permission;
import com.icptechno.admincore.user.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "um_role")
public class Role extends CreateOnlyAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", unique = true)
    private String name;

    @Column(name = "role_status")
    @Enumerated(value = EnumType.STRING)
    private ResourceStatus status = ResourceStatus.INACTIVE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "um_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "perm_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ApplicationUser> users = new HashSet<>();

    public Role() {
    }

    public Role(Long id, String name, ResourceStatus status, Set<Permission> permissions) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
