package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Address;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractMemberService<Address> {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    protected MemberRecordJpaRepository<Address, Long> getRepository() {
        return this.addressRepository;
    }
}
