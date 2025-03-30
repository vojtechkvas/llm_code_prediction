package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.Identifiable;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "country", uniqueConstraints =
        {
                @UniqueConstraint(name = "unique_constraint_country_short", columnNames = "country_short"),
                @UniqueConstraint(name = "unique_constraint_country_long", columnNames = "country_long")
        })
public class Country implements Identifiable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_country")
    private Long idCountry;

    @Column(name = "country_short", nullable = false, length = 3)
    private String countryShort;

    @Column(name = "country_long", nullable = false, length = 35)
    private String countryLong;

    @Column(name = "priority", nullable = false, columnDefinition = "int default 100")
    private int priority = 100;

    public Country(String countryShort, String countryLong, int priority) {
        this.countryShort = countryShort;
        this.countryLong = countryLong;
        this.priority = priority;
    }

    public Country(String countryShort, String countryLong) {
        this.countryShort = countryShort;
        this.countryLong = countryLong;
    }

    public Country() {
    }


    @Override
    public Long getId() {
        return this.idCountry;
    }

}
