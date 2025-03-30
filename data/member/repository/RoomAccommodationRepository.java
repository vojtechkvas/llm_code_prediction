package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoomAccommodation;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAccommodationRepository extends MemberRecordJpaRepository<RoomAccommodation, Long> {

    boolean existsByMemberIdAndDeletedAtIsNull(Long idMember);
}
