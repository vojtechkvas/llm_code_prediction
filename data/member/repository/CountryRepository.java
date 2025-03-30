package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends IdJpaRepository<Country, Long> {

    Optional<Country> findByCountryShort(String shortName);

    @Query("SELECT c FROM Country c ORDER BY  c.priority DESC, c.countryShort, c.countryLong")
    Iterable<Country> findAllSorted();

    boolean existsByCountryShort(String countryShort);

    boolean existsByCountryLong(String countryLong);
}
