package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.MemberRecordJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.BlockAccess;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.BlockAccessRepository;
import org.springframework.stereotype.Service;

@Service
public class BlockAccessService extends AbstractMemberService<BlockAccess> {
    private final BlockAccessRepository blockAccessRepository;

    public BlockAccessService(BlockAccessRepository blockAccessRepository) {
        this.blockAccessRepository = blockAccessRepository;
    }

    @Override
    protected MemberRecordJpaRepository<BlockAccess, Long> getRepository() {
        return this.blockAccessRepository;
    }

    public boolean isBlockByMembership(Member e) {
        return blockAccessRepository.isBlock(e);
    }
}
