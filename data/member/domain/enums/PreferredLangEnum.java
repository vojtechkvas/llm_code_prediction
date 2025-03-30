package cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums;

public enum PreferredLangEnum {
    CZ("cz"),
    ENG("eng");

    private final String value;

    PreferredLangEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
