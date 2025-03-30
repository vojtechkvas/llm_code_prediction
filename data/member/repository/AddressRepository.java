package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MemberRecordJpaRepository<Address, Long> {

}
