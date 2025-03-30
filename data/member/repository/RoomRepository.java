package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends IdJpaRepository<Room, Long> {
}
