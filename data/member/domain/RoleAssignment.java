package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableMember;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role_assignment")
public class RoleAssignment extends RecordableMember implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "id_role_assignment")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idRoleAssignment;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    public RoleAssignment() {
        super();
    }

    @Override
    public Long getId() {
        return this.idRoleAssignment;
    }
}
