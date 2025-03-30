package cz.cvut.fit.kvasvojt.sinis.modules.member.repository;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableDeleteEntity;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableEntity;
import cz.cvut.fit.kvasvojt.sinis.modules.election.domain.Candidate;
import cz.cvut.fit.kvasvojt.sinis.modules.election.domain.Commissar;
import cz.cvut.fit.kvasvojt.sinis.modules.election.domain.Vote;
import cz.cvut.fit.kvasvojt.sinis.modules.fitness.domain.FitnessCard;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.payment.domain.Membership;
import cz.cvut.fit.kvasvojt.sinis.modules.printer.domain.Transaction;
import cz.cvut.fit.kvasvojt.sinis.modules.stuff.domain.StuffAssignment;
import jakarta.persistence.PreRemove;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberEntityListener {


    @PreRemove
    @Transactional
    public void preRemove(Member member) {


        Iterable<FitnessCard> fitnessCards = member.getFitnessCards();

        fitnessCards.forEach(fitnessCard -> {
            fitnessCard.setMember(null);
            if (!fitnessCard.isDeleted()) {

                fitnessCard.delete(member.getDeletionRequestBy(), "Card delete, because of member deletion.");
            }

        });


        Iterable<StuffAssignment> stuffAssignments = member.getStuffAssignments();

        stuffAssignments.forEach(stuffAssignment -> stuffAssignment.setMember(null));

        Iterable<Transaction> transactions = member.getTransactions();

        transactions.forEach(transaction -> transaction.setMember(null));

        Iterable<Membership> memberships = member.getMemberships();

        memberships.forEach(transaction -> transaction.setMember(null));

        Iterable<Vote> votes = member.getVotes();
        votes.forEach(vote -> vote.setMember(null));

        Iterable<Candidate> candidates = member.getCandidates();
        candidates.forEach(candidate -> candidate.setMember(null));

        Iterable<Commissar> commissars = member.getCommissars();
        commissars.forEach(candidate -> candidate.setMember(null));


        setCreatedByToNull(member.getElectionsCreatedBy());
        setCreatedByToNull(member.getCommissarsCreated());
        setCreatedByToNull(member.getOfflineVotesCreated());
        setCreatedByToNull(member.getCandidatesCreated());
        setCreatedByToNull(member.getBlockDevicesCreated());
        setCreatedByToNull(member.getSwitchesCreated());
        setCreatedByToNull(member.getPortsCreated());
        setCreatedByToNull(member.getSocketsCreated());
        setCreatedByToNull(member.getDeviceCreated());
        setCreatedByToNull(member.getResourceCreated());
        setCreatedByToNull(member.getTransactionsCreated());
        setCreatedByToNull(member.getPaymentAbstractsCreated());
        setCreatedByToNull(member.getPaymentMappingsCreated());
        setCreatedByToNull(member.getPaymentMembershipForgivenessesCreated());
        setCreatedByToNull(member.getRoleAssignmentsCreated());
        setCreatedByToNull(member.getRoomAccommodationsCreated());
        setCreatedByToNull(member.getAddressesCreated());
        setCreatedByToNull(member.getBlockMembershipsCreated());
        setCreatedByToNull(member.getAssociateFacultiesCreated());
        setCreatedByToNull(member.getEmailSinisAliasesCreated());
        setCreatedByToNull(member.getFitnessCardsCreated());
        setCreatedByToNull(member.getStuffAssignmentsCreated());


        setDeletedByToNull(member.getElectionsDeletedBy());
        setDeletedByToNull(member.getCommissarsDeletedBy());
        setDeletedByToNull(member.getOfflineVotesDeletedBy());
        setDeletedByToNull(member.getCandidatesDeletedBy());
        setDeletedByToNull(member.getBlockDevicesDeletedBy());
        setDeletedByToNull(member.getSwitchesDeletedBy());
        setDeletedByToNull(member.getPortsDeletedBy());
        setDeletedByToNull(member.getSocketsDeletedBy());
        setDeletedByToNull(member.getDeviceDeletedBy());
        setDeletedByToNull(member.getResourceDeletedBy());
        setDeletedByToNull(member.getTransactionsDeletedBy());
        setDeletedByToNull(member.getPaymentAbstractsDeletedBy());
        setDeletedByToNull(member.getPaymentMappingsDeletedBy());
        setDeletedByToNull(member.getPaymentMembershipForgivenessesDeletedBy());
        setDeletedByToNull(member.getRoleAssignmentsDeletedBy());
        setDeletedByToNull(member.getRoomAccommodationsDeletedBy());
        setDeletedByToNull(member.getAddressesDeletedBy());
        setDeletedByToNull(member.getBlockMembershipsDeletedBy());
        setDeletedByToNull(member.getAssociateFacultiesDeletedBy());
        setDeletedByToNull(member.getEmailSinisAliasDeletedBy());
        setDeletedByToNull(member.getFitnessCardsDeletedBy());
        setDeletedByToNull(member.getStuffAssignmentsDeletedBy());


        setCreatedByToNull(member.getVoteOnlyCreatedBy());
        setCreatedByToNull(member.getSessionOnlyCreatedBy());
        setCreatedByToNull(member.getGymAccessLogOnlyCreatedBy());

        member.getMembersIdentityCheck().forEach(m -> m.setIdentityCheckBy(null));
        member.getMembersDeletionRequest().forEach(m -> m.setDeletionRequestBy(null));
        member.getMembersUpdatedBy().forEach(s -> s.setUpdatedBy(null));
    }

    private void setCreatedByToNull(Iterable<? extends RecordableEntity> list) {
        list.forEach(created -> created.setCreatedBy(null));
    }

    private void setDeletedByToNull(Iterable<? extends RecordableDeleteEntity> list) {
        list.forEach(deleted -> deleted.setDeletedBy(null));
    }
}
