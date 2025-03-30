package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends IdJpaRepository<Member, Long> {
    @Query("SELECT e FROM Member e WHERE e.activeEmail = :email " +
            "AND e.deletionRequestAt IS NULL " +
            "AND e.deletionRequestComment IS NULL AND e.willBeDeleteAt IS NULL ")
    Optional<Member> findValidMemberByActiveEmail(String email);


    @Query("SELECT e FROM Member e WHERE  " +
            " e.deletionRequestAt IS NOT NULL  " +
            "AND e.willBeDeleteAt IS NOT NULL " +
            "AND e.willBeDeleteAt < CURRENT_TIMESTAMP")
    Iterable<Member> findMembersToDelete();

    boolean existsByActiveEmail(String email);

    @Query("SELECT e FROM Member e WHERE  " +
            " e.identityCheck = :isIdentityCheck " +
            "AND e.deletionRequestAt IS NOT NULL  " +
            "AND e.willBeDeleteAt IS NOT NULL "
    )
    Page<Member> findAllMembersIdentityCheckAndDeleted(Pageable pageable, boolean isIdentityCheck);

    @Query("SELECT e FROM Member e WHERE  " +
            " e.identityCheck = :isIdentityCheck " +
            "AND e.deletionRequestAt IS  NULL  " +
            "AND e.willBeDeleteAt IS NULL "
    )
    Page<Member> findAllMembersIdentityCheckAndNotDeleted(Pageable pageable, boolean isIdentityCheck);

    Optional<Member> findByIdAndDeletionRequestAtIsNull(Long idMember);


    @Query("SELECT e FROM Member e WHERE  " +
            "e.idMember = :idMember " +
            "AND e.identityCheck = true " +
            "AND e.deletionRequestAt IS NULL  " +
            "AND e.deletionRequestComment IS NULL " +
            "AND e.willBeDeleteAt IS NULL "
    )
    Optional<Member> findValidAndCheckMemberById(Long idMember);


    Optional<Member> findByActiveEmailAndDeletionRequestAtIsNull(String email);
}
