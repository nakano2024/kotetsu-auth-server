package kotetsu.auth.application.exception;

public class ClientNotPermittedScopesContainedException extends Exception {
    public ClientNotPermittedScopesContainedException() {
        super("RequesterClientに許可されていないスコープが含まれています。");
    }
}
