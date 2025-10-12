package kotetsu.auth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IFetchServerUrlPort;

@Component
public class ServerUrlFetcher implements IFetchServerUrlPort {

    @Value("${app.server.url}")
    private String serverUrl;

    @Override
    public String fetch() {
        return serverUrl;

    }
}
