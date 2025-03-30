package cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums;

import lombok.Getter;

@Getter
public enum DormitoryEnum {

    DEJVICKA("Dejvická Dormitory"),
    SINKULE("Sinkule Dormitory");

    private final String value;

    DormitoryEnum(String value) {
        this.value = value;
    }

}
