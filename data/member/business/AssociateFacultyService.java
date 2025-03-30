package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.AssociateFaculty;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.AssociateFacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociateFacultyService extends AbstractMemberService<AssociateFaculty> {
    private final AssociateFacultyRepository associateFacultyRepository;

    @Autowired
    public AssociateFacultyService(AssociateFacultyRepository associateFacultyRepository) {
        this.associateFacultyRepository = associateFacultyRepository;
    }

    @Override
    protected MemberRecordJpaRepository<AssociateFaculty, Long> getRepository() {
        return this.associateFacultyRepository;
    }
}
