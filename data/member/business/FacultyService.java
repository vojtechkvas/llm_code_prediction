package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.AttributeOfEntityTakenException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Faculty;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultyService extends AbstractService<Faculty> {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    protected IdJpaRepository<Faculty, Long> getRepository() {
        return this.facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {

        if (facultyRepository.existsByNameEnAndUniversity(faculty.getNameEn(), faculty.getUniversity())) {
            throw new AttributeOfEntityTakenException("NameEn already exist on university.");
        }

        if (facultyRepository.existsByNameAndUniversity(faculty.getName(), faculty.getUniversity())) {
            throw new AttributeOfEntityTakenException("Name already exist on university.");
        }

        return super.create(faculty);
    }
}
