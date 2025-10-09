package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.ScopeInformationList;
import kotetsu.auth.application.domain.repository.IFetchScopeInformationListPort;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.dto.input.GetScopeDescriptionsInput;
import kotetsu.auth.application.dto.output.ScopeDescriptionsOutput;
import kotetsu.auth.application.exception.InvalidScopeNameListTokenException;
import kotetsu.auth.application.exception.ScopeInformationListNullRuntimeException;

@Component
public class GetScopeDescriptionsUsecase {
    private final IFetchScopeInformationListPort fetchScopeDescriptionListPort;

    public GetScopeDescriptionsUsecase(final IFetchScopeInformationListPort fetchScopeDescriptionListPort) {
        this.fetchScopeDescriptionListPort = fetchScopeDescriptionListPort;
    }

    public ScopeDescriptionsOutput execute(final GetScopeDescriptionsInput input)
        throws InvalidScopeNameListTokenException
    {
        final RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(RequestedScopeNameListToken.of(input.getScopeListToken()));
        final ScopeInformationList fetchedScopeDescriptionList = fetchScopeDescriptionListPort.fetch(requestedScopeNameList)
            .orElseThrow(() -> new ScopeInformationListNullRuntimeException());

        if(!fetchedScopeDescriptionList.matchesRequestedScopeNameList(requestedScopeNameList)) {
            throw new InvalidScopeNameListTokenException();
        }

        return ScopeDescriptionsOutput.of(fetchedScopeDescriptionList.toStringDescriptionList());
    }
}
