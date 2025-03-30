package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.Identifiable;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.RoleTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(name = "role_type", columnNames = {"type"}),
        @UniqueConstraint(name = "role_description", columnNames = {"description"})
})
public class Role implements Identifiable, Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idRole;

    @Column(name = "type", nullable = false, length = 256)
    @Enumerated(EnumType.STRING)
    private RoleTypeEnum type;

    @Column(name = "description", length = 256)
    private String description;

    public Role(RoleTypeEnum type, String description) {
        this.type = type;
        this.description = description;
    }

    public Role() {

    }

    @Override
    public Long getId() {
        return this.idRole;
    }
}
