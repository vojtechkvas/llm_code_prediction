package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableMember;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "address")
public class Address extends RecordableMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_address")
    private Long idAddress;

    @ManyToOne
    @JoinColumn(name = "id_country", referencedColumnName = "id_country", nullable = false)
    private Country country;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "street_number", nullable = false)
    private int streetNumber;


    public Address() {
        super();
    }

    @Override
    public Long getId() {
        return idAddress;
    }

}
