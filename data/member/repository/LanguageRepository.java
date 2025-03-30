package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Language;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends MemberRecordJpaRepository<Language, Long> {

}
