package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.Identifiable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "faculty", uniqueConstraints = {
        @UniqueConstraint(name = "unique_university_faculty", columnNames = {"id_university", "faculty"}),
        @UniqueConstraint(name = "unique_university_faculty_en", columnNames = {"id_university", "faculty_en"})
})
public class Faculty implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_faculty")
    private Long idFaculty;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "name_en", nullable = false, length = 60)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "id_university", nullable = false)
    private University university;

    public Faculty() {
    }

    public Faculty(String name, String nameEn, University university) {
        this.name = name;
        this.nameEn = nameEn;
        this.university = university;
    }

    @Override
    public Long getId() {
        return idFaculty;
    }
}
