package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.AttributeOfEntityTakenException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.University;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.UniversityRepository;
import org.springframework.stereotype.Service;

@Service
public class UniversityService extends AbstractService<University> {
    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    protected IdJpaRepository<University, Long> getRepository() {
        return this.universityRepository;
    }


    @Override
    public University create(University university) {

        if (universityRepository.existsByNameEn(university.getNameEn())) {
            throw new AttributeOfEntityTakenException("NameEn already exist with this universityEn attribute.");
        }

        if (universityRepository.existsByName(university.getName())) {
            throw new AttributeOfEntityTakenException("Name already exist with this university attribute.");
        }

        return super.create(university);
    }

}
