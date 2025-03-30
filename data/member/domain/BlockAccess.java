package cz.cvut.fit.kvasvojt.sinis.modules.member.domain;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.domain.RecordableMember;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.BlockAccessTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "block_access")
public class BlockAccess extends RecordableMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_block_access")
    private Long idBlockAccess;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blocked_end", nullable = false)
    private LocalDateTime blockedEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "block_access_type", nullable = false)
    private BlockAccessTypeEnum blockAccessType;

    @Column(name = "message_for_block_member", nullable = false, length = 100)
    private String messageForBlockMember;

    public BlockAccess() {
        super();
    }

    @Override
    public Long getId() {
        return idBlockAccess;
    }
}
