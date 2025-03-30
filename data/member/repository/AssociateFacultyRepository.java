package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.AssociateFaculty;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateFacultyRepository extends MemberRecordJpaRepository<AssociateFaculty, Long> {

}
