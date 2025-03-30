package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.EntityAlreadyAssignedException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoomAccommodation;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.RoomAccommodationRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomAccommodationService extends AbstractMemberService<RoomAccommodation> {

    private final RoomAccommodationRepository roomAccommodationRepository;

    public RoomAccommodationService(RoomAccommodationRepository roomAccommodationRepository) {
        this.roomAccommodationRepository = roomAccommodationRepository;
    }

    @Override
    protected MemberRecordJpaRepository<RoomAccommodation, Long> getRepository() {
        return this.roomAccommodationRepository;
    }

    @Override
    public RoomAccommodation create(RoomAccommodation roomAccommodation) {

        if (roomAccommodationRepository.existsByMemberIdAndDeletedAtIsNull(roomAccommodation.getMember().getIdMember())) {
            throw new EntityAlreadyAssignedException("Member with id = " + roomAccommodation.getMember().getIdMember() + " already has room.");
        }

        return super.create(roomAccommodation);
    }
}
