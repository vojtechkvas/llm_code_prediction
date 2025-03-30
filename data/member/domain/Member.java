package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.Identifiable;
import cz.cvut.fit.kvasvojt.sinis.modules.election.domain.*;
import cz.cvut.fit.kvasvojt.sinis.modules.email_sinis_alias.domain.EmailSinisAlias;
import cz.cvut.fit.kvasvojt.sinis.modules.fitness.domain.FitnessCard;
import cz.cvut.fit.kvasvojt.sinis.modules.fitness.domain.GymAccessLog;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.PreferredLangEnum;
import cz.cvut.fit.kvasvojt.sinis.modules.member.repository.MemberEntityListener;
import cz.cvut.fit.kvasvojt.sinis.modules.network.domain.*;
import cz.cvut.fit.kvasvojt.sinis.modules.payment.domain.*;
import cz.cvut.fit.kvasvojt.sinis.modules.printer.domain.Resource;
import cz.cvut.fit.kvasvojt.sinis.modules.printer.domain.Transaction;
import cz.cvut.fit.kvasvojt.sinis.modules.session.domain.Session;
import cz.cvut.fit.kvasvojt.sinis.modules.stuff.domain.StuffAssignment;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(name = "unique_active_email_constraint", columnNames = "active_email"))
@EntityListeners(MemberEntityListener.class)
public class Member implements Identifiable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_member")
    private Long idMember;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Address> addresses;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<BlockAccess> blockAccesses;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<RoomAccommodation> roomAccommodations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<EmailSinisAlias> emailSinisAliases;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<AssociateFaculty> associateFaculties;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Device> devices;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<RoleAssignment> roleAssignments;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "member", fetch = FetchType.EAGER)
    private Collection<Language> languages;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<FitnessCard> fitnessCards;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Vote> votes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Candidate> candidates;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Commissar> commissars;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<StuffAssignment> stuffAssignments;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Collection<Membership> memberships;


    @ManyToOne
    @JoinColumn(name = "id_country", referencedColumnName = "id_country", nullable = false)
    private Country country;

    @Column(name = "first_name", nullable = false, length = 256)
    private String firstName;

    @Column(name = "middle_name", length = 256)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 256)
    private String lastName;

    @Column(name = "identity_check", nullable = false)
    private boolean identityCheck = false;

    @ManyToOne
    @JoinColumn(name = "identity_check_by", referencedColumnName = "id_member")
    private Member identityCheckBy;

    @Column(name = "identity_check_at")
    private LocalDateTime identityCheckAt;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "active_email", nullable = false, length = 256)
    private String activeEmail;

    @Column(name = "note", nullable = false, length = 256)
    private String note;


    @Column(name = "birth_city", nullable = false, length = 256)
    private String birthCity;

    @Column(name = "birth_data", nullable = false)
    private Date birthDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deletion_request_at")
    private LocalDateTime deletionRequestAt;

    @ManyToOne
    @JoinColumn(name = "deletion_request_by", referencedColumnName = "id_member")
    private Member deletionRequestBy;

    @Column(name = "deletion_request_comment", length = 256)
    private String deletionRequestComment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "will_be_delete_at")
    private LocalDateTime willBeDeleteAt;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Election> electionsCreatedBy;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Commissar> commissarsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<OfflineVotes> offlineVotesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Candidate> candidatesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<BlockDevice> blockDevicesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Switch> switchesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Port> portsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Socket> socketsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Device> deviceCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Resource> resourceCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Transaction> transactionsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<PaymentAbstract> paymentAbstractsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<PaymentMapping> paymentMappingsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<PaymentMembershipForgiveness> paymentMembershipForgivenessesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<RoleAssignment> roleAssignmentsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<RoomAccommodation> roomAccommodationsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Address> addressesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<BlockAccess> blockMembershipsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<AssociateFaculty> associateFacultiesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<EmailSinisAlias> emailSinisAliasesCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<FitnessCard> fitnessCardsCreated;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<StuffAssignment> stuffAssignmentsCreated;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Election> electionsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Commissar> commissarsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<OfflineVotes> offlineVotesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Candidate> candidatesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<BlockDevice> blockDevicesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Switch> switchesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Port> portsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Socket> socketsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Device> deviceDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Resource> resourceDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Transaction> transactionsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<PaymentAbstract> paymentAbstractsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<PaymentMapping> paymentMappingsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<PaymentMembershipForgiveness> paymentMembershipForgivenessesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<RoleAssignment> roleAssignmentsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<RoomAccommodation> roomAccommodationsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<Address> addressesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<BlockAccess> blockMembershipsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<AssociateFaculty> associateFacultiesDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<EmailSinisAlias> emailSinisAliasDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<FitnessCard> fitnessCardsDeletedBy;

    @OneToMany(mappedBy = "deletedBy")
    private transient Collection<StuffAssignment> stuffAssignmentsDeletedBy;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Vote> voteOnlyCreatedBy;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<Session> sessionOnlyCreatedBy;

    @OneToMany(mappedBy = "createdBy")
    private transient Collection<GymAccessLog> gymAccessLogOnlyCreatedBy;

    @OneToMany(mappedBy = "identityCheckBy")
    private transient Collection<Member> membersIdentityCheck;

    @OneToMany(mappedBy = "deletionRequestBy")
    private transient Collection<Member> membersDeletionRequest;

    @OneToMany(mappedBy = "updatedBy")
    private transient Collection<Semester> membersUpdatedBy;


    public Member() {
        this.createdAt = LocalDateTime.now();
    }

    public Member(String firstName,
                  String middleName,
                  String lastName,
                  boolean identityCheck,
                  Member identityCheckBy,
                  String activeEmail,
                  String note
    ) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.identityCheck = identityCheck;
        this.identityCheckBy = identityCheckBy;
        this.activeEmail = activeEmail;
        this.note = note;

        this.createdAt = LocalDateTime.now();
    }

    public void deleteRequest(
            Member deletionRequestBy,
            String deletionRequestComment,
            LocalDateTime willBeDeleteAt
    ) {
        this.willBeDeleteAt = willBeDeleteAt;
        this.deletionRequestAt = LocalDateTime.now();
        this.deletionRequestBy = deletionRequestBy;
        this.deletionRequestComment = deletionRequestComment;
    }

    public boolean isDeleted() {
        return this.deletionRequestAt != null || this.willBeDeleteAt != null;
    }

/*
    public boolean hasRole(RoleTypeEnum roleTypeEnum) {

        return getAuthorities().parallelStream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(roleTypeEnum.getValue()));
    }
*/

    @Override
    public Long getId() {
        return this.idMember;
    }

    /*
        public Set<Role> getRoles() {

            if (getRoleAssignments() == null) {
                return new HashSet<>();
            }

            return getRoleAssignments().stream()
                    .filter(roleAssignment -> roleAssignment.getDeletedAt() == null)
                    .map(RoleAssignment::getRole)
                    .collect(Collectors.toSet());
        }
    */
    public PreferredLangEnum getPreferredLanguage() {

        if (getLanguages() == null) {
            return PreferredLangEnum.CZ;
        }

        Optional<Language> maxCreatedAtLanguage = getLanguages().stream()
                .filter(language -> language.getDeletedAt() == null)
                .max(Comparator.comparing(Language::getCreatedAt));

        return maxCreatedAtLanguage.map(Language::getPreferredLanguage).orElse(PreferredLangEnum.CZ);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> setSimpleGrantedAuthority = new HashSet<>();

        return setSimpleGrantedAuthority;

/*

        if (this.isDeleted()) {
            return setSimpleGrantedAuthority;
        }

        setSimpleGrantedAuthority = getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().getValue()))
                .collect(Collectors.toSet());

        if (getRoles().stream().anyMatch(role -> role.getType().equals(RoleTypeEnum.ROLE_GOD_ADMIN))) {
            setSimpleGrantedAuthority.addAll(Arrays.stream(RoleTypeEnum.values())
                    .map(role -> new SimpleGrantedAuthority(role.getValue()))
                    .collect(Collectors.toSet()));
        }

        if (getRoles().stream().anyMatch(role -> role.getType().equals(RoleTypeEnum.ROLE_HEAD_OF_NET_ADMINS))) {
            setSimpleGrantedAuthority.add(new SimpleGrantedAuthority(RoleTypeEnum.ROLE_NET_ADMIN.getValue()));
        }

        if (getRoles().stream().anyMatch(role -> role.getType().equals(RoleTypeEnum.ROLE_HEAD_OF_GYM_ADMINS))) {
            setSimpleGrantedAuthority.add(new SimpleGrantedAuthority(RoleTypeEnum.ROLE_GYM_ADMIN.getValue()));
        }

        if (getRoles().stream().anyMatch(role -> role.getType().equals(RoleTypeEnum.ROLE_NET_ADMIN))) {
            setSimpleGrantedAuthority.add(new SimpleGrantedAuthority(RoleTypeEnum.ROLE_REGISTRAR.getValue()));
        }

        return setSimpleGrantedAuthority;
        */

    }

    @Override
    public String getUsername() {
        return getActiveEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return deletionRequestAt == null && deletionRequestBy == null && deletionRequestComment == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void enforceDeleteConstraint() {
        if (!((deletionRequestAt == null && deletionRequestBy == null && deletionRequestComment == null && willBeDeleteAt == null) ||
                (deletionRequestAt != null && deletionRequestBy != null && deletionRequestComment != null && willBeDeleteAt == null) ||
                (deletionRequestAt != null && deletionRequestBy == null && deletionRequestComment != null && willBeDeleteAt == null))) {
            throw new IllegalStateException("Invalid delete state: Either all delete fields should be null or all should have values.");
        }

        if (!((!identityCheck && identityCheckBy == null && identityCheckAt == null) ||
                (identityCheck && identityCheckAt != null))) {
            throw new IllegalStateException("Invalid identity check state: Either all identityCheck fields should be null or all should have values.");
        }
    }
}