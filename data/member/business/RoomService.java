package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Room;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.RoomRepository;
import org.springframework.stereotype.Service;


@Service
public class RoomService extends AbstractService<Room> {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    protected IdJpaRepository<Room, Long> getRepository() {
        return this.roomRepository;
    }

}
