package cz.cvut.fit.kvasvojt.sinis.modules.member.business;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.AttributeOfEntityTakenException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.EntityAlreadyDeletedException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.MyEntityNotFoundException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.repository.IdJpaRepository;
import cz.cvut.fit.kvasvojt.sinis.modules.email_sinis_alias.business.EmailSinisAliasService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.exception.MemberCannotCheckIdentityException;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Address;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoomAccommodation;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService extends AbstractService<Member> {

    private final MemberRepository memberRepository;
    private final EmailSinisAliasService emailSinisAliasService;
    private final LanguageService languageService;
    private final AddressService addressService;
    private final RoomAccommodationService roomAccommodationService;

    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, EmailSinisAliasService emailSinisAliasService, LanguageService languageService, AddressService addressService, RoomAccommodationService roomAccommodationService, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.emailSinisAliasService = emailSinisAliasService;
        this.languageService = languageService;
        this.addressService = addressService;
        this.roomAccommodationService = roomAccommodationService;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean existsByActiveEmail(String email) {
        return this.memberRepository.existsByActiveEmail(email);
    }

    public Optional<Member> getByActiveEmail(String email) {
        return this.memberRepository.findValidMemberByActiveEmail(email);
    }

    @Override
    protected IdJpaRepository<Member, Long> getRepository() {
        return this.memberRepository;
    }


    public Page<Member> getAllMembersIdentityCheckAndDeletionCheck(Pageable pageable, boolean isIdentityCheck, boolean isDeletionRequested) {
        if (isDeletionRequested) {
            return memberRepository.findAllMembersIdentityCheckAndDeleted(pageable, isIdentityCheck);
        } else {
            return memberRepository.findAllMembersIdentityCheckAndNotDeleted(pageable, isIdentityCheck);

        }
    }

    public Member deleteRequest(Member login, String comment, Member member) {

        if (member.isDeleted()) {
            throw new EntityAlreadyDeletedException();
        }

        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        LocalDateTime willBeDeleteAt = currentDate.plusYears(1).withMonth(10).withDayOfMonth(1);

        member.deleteRequest(login, comment, willBeDeleteAt);
        member = memberRepository.save(member);

        return member;
    }

    public Member changePassword(String oldPassword, String newPassword, Member updateMember) {

        if (oldPassword != null && !passwordEncoder.matches(oldPassword, updateMember.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        newPassword = passwordEncoder.encode(newPassword);
        updateMember.setPassword(newPassword);
        updateMember = memberRepository.save(updateMember);

        return updateMember;
    }

    public Iterable<Member> getMembersToDelete() {
        return memberRepository.findMembersToDelete();
    }

    public Member getValidEntityById(Long idMember) {
        return memberRepository.findByIdAndDeletionRequestAtIsNull(idMember)
                .orElseThrow(() -> new MyEntityNotFoundException("Member with id = " + idMember + " not found, because does not exist or was deleted."));

    }

    public Member getValidAndCheckMemberById(Long idMember) {
        return memberRepository.findValidAndCheckMemberById(idMember)
                .orElseThrow(() -> new MyEntityNotFoundException("Member with id = " + idMember + " not found, because does not exist or was deleted or does not have identity check."));

    }

    @Transactional
    @Override
    public Member create(Member member) {

        if (memberRepository.existsByActiveEmail(member.getActiveEmail())) {
            throw new AttributeOfEntityTakenException("Member already exist with this email address.");
        }

        member = super.create(member);
        emailSinisAliasService.createDefault(member);

        if (member.isIdentityCheck() && !hasMemberAllRequiredFieldsFilledOut(member)) {
            throw new MemberCannotCheckIdentityException("Cannot check identity, some fields are not filled out.");
        }

        languageService.createDefault(member);

        return member;
    }


    @Override
    public Member update(Member member) {

        if (member.isIdentityCheck() && !hasMemberAllRequiredFieldsFilledOut(member)) {
            throw new MemberCannotCheckIdentityException("Cannot check identity, some fields are not filled out.");
        }

        return super.update(member);
    }

    private boolean hasMemberAllRequiredFieldsFilledOut(Member member) {

        Iterable<Address> addressList = addressService.getByMemberId(member.getIdMember());
        Iterable<RoomAccommodation> roomAccommodationList = roomAccommodationService.getByMemberId(member.getIdMember());

        return addressList != null && addressList.iterator().hasNext() &&
                roomAccommodationList != null && roomAccommodationList.iterator().hasNext();

    }


}

