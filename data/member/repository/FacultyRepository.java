package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Faculty;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.University;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends IdJpaRepository<Faculty, Long> {
    boolean existsByNameEnAndUniversity(String nameEN, University university);

    boolean existsByNameAndUniversity(String name, University university);
}
