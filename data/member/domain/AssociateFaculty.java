package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableMember;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "associate_faculty")
public class AssociateFaculty extends RecordableMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_associate_faculty")
    private Long idAssociateFaculty;


    @ManyToOne
    @JoinColumn(name = "id_faculty", nullable = false)
    private Faculty faculty;

    public AssociateFaculty() {
        super();
    }


    @Override
    public Long getId() {
        return idAssociateFaculty;
    }
}
