package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableMember;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "room_accommodation")
public class RoomAccommodation extends RecordableMember {

    @Id
    @Column(name = "id_room_accommodation")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idRoomAccommodation;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    public RoomAccommodation() {
        super();
    }

    @Override
    public Long getId() {
        return this.idRoomAccommodation;
    }
}
