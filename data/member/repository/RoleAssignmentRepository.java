package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoleAssignment;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.BlockAccessTypeEnum;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.RoleTypeEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleAssignmentRepository extends MemberRecordJpaRepository<RoleAssignment, Long> {

    boolean existsByMemberIdAndRoleIdAndDeletedAtIsNull(Long idMember, Long roleId);

    @Query("SELECT r.type   FROM Role r " +
            "JOIN RoleAssignment ra ON ra.role = r " +
            "JOIN Member m ON ra.member.idMember = m.idMember " +
            "LEFT JOIN Membership ms ON ms.member.idMember = m.idMember " +
            "LEFT JOIN Semester sem ON ms.semester.idSemester = sem.idSemester " +
            "LEFT JOIN MembershipType mt ON ms.membershipType.idMembershipType = mt.idMembershipType  " +
            "LEFT JOIN PaymentMembershipForgiveness pf ON pf.membership.idMembership = ms.idMembership " +
            "LEFT JOIN PaymentMapping pm ON ms.paymentMapping.idPaymentMapping = pm.idPaymentMapping " +
            "LEFT JOIN BlockAccess ba ON ba.member.idMember = m.idMember " +


            "WHERE " +


            "   ( ms IS NOT NULL AND mt IS NOT NULL AND m.identityCheck = true AND  " +

            "  sem.startOfSemester < CURRENT_TIMESTAMP " +
            "  AND CURRENT_TIMESTAMP < sem.endOfSemester " +

            "  AND " +
            "   (( " +
            "    pm IS  NULL " +
            "    AND pf IS NOT NULL " +
            "    AND pf.deletedAt IS NULL " +

            "    ) OR (" +
            "    pf IS  NULL " +
            "    AND pm  IS NOT NULL " +
            "    AND pm.deletedAt IS NULL " +
            "))) " +


            // access is NOT blocked
            " AND ( ba IS  NULL OR " +
            "   (NOT( ba.blockAccessType = :blockAccessTypeEnumNetwork " +
            "       AND CURRENT_TIMESTAMP < ba.blockedEnd AND ba.deletedAt IS NULL )" +
            "   AND  " +
            "   NOT( ba.blockAccessType = :blockAccessTypeEnumGym  " +
            "       AND CURRENT_TIMESTAMP < ba.blockedEnd AND ba.deletedAt IS NULL )" +
            "  )" +
            ")  " +

            "AND m.activeEmail = :username " +
            "AND ra.deletedAt IS NULL " +
            "AND m.deletionRequestAt IS NULL AND m.willBeDeleteAt IS NULL  "

    )
    List<RoleTypeEnum> findValidRolesForMemberByActiveEmail(@Param("username") String username,
                                                            @Param("blockAccessTypeEnumNetwork") BlockAccessTypeEnum blockAccessTypeEnumNetwork,
                                                            @Param("blockAccessTypeEnumGym") BlockAccessTypeEnum blockAccessTypeEnumGym);


}
