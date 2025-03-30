package cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums;

import lombok.Getter;

@Getter
public enum RoleTypeEnum {

    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_MULTI_MEDIA("ROLE_MULTI_MEDIA"),
    ROLE_CHAIR_MAN("ROLE_CHAIR_MAN"),
    ROLE_NET_ADMIN("ROLE_NET_ADMIN"),
    ROLE_GYM_ADMIN("ROLE_GYM_ADMIN"),
    ROLE_HEAD_OF_NET_ADMINS("ROLE_HEAD_OF_NET_ADMINS"),
    ROLE_HEAD_OF_GYM_ADMINS("ROLE_HEAD_OF_GYM_ADMINS"),
    ROLE_GOD_ADMIN("ROLE_GOD_ADMIN"),
    ROLE_ACCOUNTANT("ROLE_ACCOUNTANT"),
    ROLE_STUFF_ADMIN("ROLE_STUFF_ADMIN"),
    ROLE_REGISTRAR("ROLE_REGISTRAR");

    private final String value;

    RoleTypeEnum(String value) {
        this.value = value;
    }
}
