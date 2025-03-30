package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.MyEntityNotFoundException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Role;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.RoleTypeEnum;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractService<Role> {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    protected IdJpaRepository<Role, Long> getRepository() {
        return this.roleRepository;
    }

    public Role getByType(RoleTypeEnum type) {
        return roleRepository.findByType(type).orElseThrow(
                () -> new MyEntityNotFoundException(type + " does not exist in the database.")
        );
    }


}
