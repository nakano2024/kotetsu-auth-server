package kotetsu.auth.unit.domain.entity.requestedrelatedaudiencelist;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequestedRelatedAudienceList;

public class ToStringListTest {
    @Test
    public void returnAllArgumentAudiences() {
        final RequestedRelatedAudienceList requestedRelatedAudienceList = RequestedRelatedAudienceList.of(List.of("https://api1.example.com", "https://api2.example.com"));

        
        final List<String> expectedAudienceList = List.of(
            "https://api1.example.com",
            "https://api2.example.com"
        );

        assertEquals(expectedAudienceList, requestedRelatedAudienceList.toStringList());
    }
}