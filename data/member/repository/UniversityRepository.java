package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.University;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends IdJpaRepository<University, Long> {
    boolean existsByNameEn(String nameEn);

    boolean existsByName(String name);
}
