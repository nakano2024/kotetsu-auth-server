package kotetsu.auth.util;

import kotetsu.auth.application.domain.util.IFetchServerUrlPort;

public class ServerUrlFetcher implements IFetchServerUrlPort {
    @Override
    public String fetch() {
        final String url = System.getenv("SERVER_URL");
        return url;
    }
}
