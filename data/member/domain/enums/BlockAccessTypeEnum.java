package cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums;

import lombok.Getter;

@Getter
public enum BlockAccessTypeEnum {
    NETWORK_ACCESS("NETWORK_ACCESS"),
    GYM_ACCESS("GYM_ACCESS");

    private final String value;

    BlockAccessTypeEnum(String value) {
        this.value = value;
    }

}
