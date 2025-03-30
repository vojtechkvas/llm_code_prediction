package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.BlockAccess;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockAccessRepository extends MemberRecordJpaRepository<BlockAccess, Long> {

    @Query("SELECT COUNT(bm) > 0 FROM BlockAccess bm " +
            "WHERE bm.member = :member " +
            "AND CURRENT_TIMESTAMP < bm.blockedEnd " +
            "AND bm.deletedAt IS NULL  "
    )
    boolean isBlock(Member member);
}
