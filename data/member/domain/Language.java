package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableMember;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.PreferredLangEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "language")
public class Language extends RecordableMember implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_language")
    private Long idLanguage;

    @Column(name = "preferred_language", nullable = false, length = 256)
    @Enumerated(EnumType.STRING)
    private PreferredLangEnum preferredLanguage = PreferredLangEnum.CZ;

    @Override
    public Long getId() {
        return this.idLanguage;
    }

}
