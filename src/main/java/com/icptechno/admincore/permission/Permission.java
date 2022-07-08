package com.icptechno.admincore.permission;

import com.icptechno.admincore.common.CreateOnlyAuditable;
import com.icptechno.admincore.common.ResourceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "um_permission")
public class Permission extends CreateOnlyAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perm_id")
    private Long id;

    @Column(name = "perm_name", unique = true)
    private String name;

    @Column(name = "perm_value")
    private String value;

    @Column(name = "perm_status")
    @Enumerated(value = EnumType.STRING)
    private ResourceStatus status = ResourceStatus.INACTIVE;

//    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
//    private Set<Role> roles;

    public Permission() {
        super();
    }

    public Permission(Long id, String name, String value, ResourceStatus status) {
        super();
        this.id = id;
        this.name = name;
        this.value = value;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", status=" + status +
                '}';
    }
}
