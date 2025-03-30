package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MemberCleanupScheduler {
    private static final Logger logger = LoggerFactory.getLogger(MemberCleanupScheduler.class);
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberCleanupScheduler(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void processDeleteRequests() {

        logger.info("Member cleanup");

        Iterable<Member> membersToDelete = memberService.getMembersToDelete();

        memberRepository.deleteAll(membersToDelete);
    }
}
