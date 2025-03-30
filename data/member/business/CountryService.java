package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.AttributeOfEntityTakenException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.MyEntityNotFoundException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Country;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends AbstractService<Country> {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    protected IdJpaRepository<Country, Long> getRepository() {
        return this.countryRepository;
    }

    public Country getByCountryShort(String shortName) {
        return countryRepository.findByCountryShort(shortName)
                .orElseThrow(() -> new MyEntityNotFoundException("Short country not found: " + shortName));
    }

    public Iterable<Country> getAllSorted() {
        return countryRepository.findAllSorted();
    }

    @Override
    public Country create(Country country) {

        if (countryRepository.existsByCountryShort(country.getCountryShort())) {
            throw new AttributeOfEntityTakenException("Country already exist with this country_short attribute.");
        }

        if (countryRepository.existsByCountryLong(country.getCountryLong())) {
            throw new AttributeOfEntityTakenException("Country already exist with this country_long attribute.");
        }

        return super.create(country);
    }
}
