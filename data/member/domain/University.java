package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.Identifiable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "university", uniqueConstraints = {
        @UniqueConstraint(name = "university_university", columnNames = {"university"}),
        @UniqueConstraint(name = "university_university_en", columnNames = {"university_en"})
})
public class University implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_university")
    private Long idUniversity;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "name_en", nullable = false, length = 256)
    private String nameEn;

    public University() {
    }

    public University(String name, String nameEn) {
        this.name = name;
        this.nameEn = nameEn;
    }

    @Override
    public Long getId() {
        return idUniversity;
    }
}
