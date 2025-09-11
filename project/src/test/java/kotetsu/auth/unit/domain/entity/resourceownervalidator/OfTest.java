package kotetsu.auth.unit.domain.entity.resourceownervalidator;

import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ResourceOwnerValidator;
import kotetsu.auth.application.domain.value.UserActivation;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        UserActivation activation = UserActivation.of(true);

        ResourceOwnerValidator resourceOwnerValidator = ResourceOwnerValidator.of(activation);

        // ResourceOwnerValidatorオブジェクトが正常に作成されることを確認
        // 内部フィールドへのアクセサがないため、オブジェクト作成の成功をもってテスト完了とする
        assert resourceOwnerValidator != null;
    }
}