package kotetsu.auth.util;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IFetchServerUrlPort;

@Component
public class ServerUrlFetcher implements IFetchServerUrlPort {
    @Override
    public String fetch() {
        final String url = System.getenv("SERVER_URL");
        return url;
    }
}
