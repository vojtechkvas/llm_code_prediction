package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.EntityAlreadyAssignedException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoleAssignment;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.BlockAccessTypeEnum;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.RoleTypeEnum;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.RoleAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleAssignmentService extends AbstractMemberService<RoleAssignment> {
    private final RoleAssignmentRepository roleAssignmentRepository;

    public RoleAssignmentService(RoleAssignmentRepository roleAssignmentRepository) {
        this.roleAssignmentRepository = roleAssignmentRepository;

    }

    @Override
    protected MemberRecordJpaRepository<RoleAssignment, Long> getRepository() {
        return this.roleAssignmentRepository;
    }

    @Override
    public RoleAssignment create(RoleAssignment e) {

        if (this.roleAssignmentRepository.existsByMemberIdAndRoleIdAndDeletedAtIsNull(e.getMember().getId(), e.getRole().getId())) {
            throw new EntityAlreadyAssignedException("Member already owns this role.");
        }

        return super.create(e);
    }


    public List<RoleTypeEnum> getValidRolesForMemberByActiveEmail(String username) {


        List<RoleTypeEnum> roles = roleAssignmentRepository.findValidRolesForMemberByActiveEmail(username,
                BlockAccessTypeEnum.NETWORK_ACCESS,
                BlockAccessTypeEnum.GYM_ACCESS);


        if (roles.stream().anyMatch(role -> role.equals(RoleTypeEnum.ROLE_GOD_ADMIN))) {
            roles.addAll(Arrays.stream(RoleTypeEnum.values())
                    .collect(Collectors.toSet()));
        }

        if (roles.stream().anyMatch(role -> role.equals(RoleTypeEnum.ROLE_HEAD_OF_NET_ADMINS))) {
            roles.add((RoleTypeEnum.ROLE_NET_ADMIN));
        }

        if (roles.stream().anyMatch(role -> role.equals(RoleTypeEnum.ROLE_HEAD_OF_GYM_ADMINS))) {
            roles.add((RoleTypeEnum.ROLE_GYM_ADMIN));
        }

        if (roles.stream().anyMatch(role -> role.equals(RoleTypeEnum.ROLE_NET_ADMIN))) {
            roles.add((RoleTypeEnum.ROLE_REGISTRAR));
        }


        return roles;

    }


}
