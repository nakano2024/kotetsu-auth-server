package kotetsu.auth.application.persistence;

public interface IDeleteAuthorizationCodePort {
    void deleteByCode(final String code);
}