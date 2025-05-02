package tech.falabella.qa.report.ms_Token_Metris;

import com.google.gson.JsonObject;
import lombok.*;
import tech.falabella.qa.report.Tuple;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
public class TokenMetrisTuple extends Tuple {
    private String descriptiontoken;
    private Number debit;
    private Number credit;
    private Number prepaid;
    private Number total;
    @Override
    public JsonObject getId() {
        return toJsonIds.apply(
                Map.of("Descripción Token", descriptiontoken)

        );
    }


}
