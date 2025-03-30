package cz.cvut.fit.kvasvojt.sinis.modules.member.business.exception;

public class MemberCannotCheckIdentityException extends RuntimeException {
    public MemberCannotCheckIdentityException(String message) {
        super(message);
    }
}
