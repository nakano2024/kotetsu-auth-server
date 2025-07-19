package kotetsu.auth.application.util;

import java.util.Date;
import java.util.Map;

public interface IGenerateIInternalAuthTokenPort {
    String generate(
        final String subject,
        final Date issuedAt,
        final Date expiredAt,
        final Map<String, String> profile
    );
}
