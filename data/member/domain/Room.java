package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.Identifiable;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.DormitoryEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room", uniqueConstraints = {
        @UniqueConstraint(name = "room_dormitory_roomNumber", columnNames = {"dormitory", "roomNumber"})
})
public class Room implements Identifiable {

    @Id
    @Column(name = "id_room")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idRoom;

    @Column(name = "dormitory", nullable = false, length = 256)
    @Enumerated(EnumType.STRING)
    private DormitoryEnum dormitory;

    @Column(name = "roomNumber", nullable = false, length = 256)
    private String roomNumber;

    public Room(DormitoryEnum dormitory, String roomNumber) {
        this.dormitory = dormitory;
        this.roomNumber = roomNumber;
    }

    public Room() {

    }

    @Override
    public Long getId() {
        return this.idRoom;
    }
}
