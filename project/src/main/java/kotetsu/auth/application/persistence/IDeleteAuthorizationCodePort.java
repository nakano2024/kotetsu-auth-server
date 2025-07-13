package kotetsu.auth.application.persistence;

public interface IDeleteAuthorizationCodePort {
    void deleteByValue(final String value);
}