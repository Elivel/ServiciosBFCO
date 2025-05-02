package tech.falabella.qa.report.ms_Token_Metris;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.falabella.qa.exception.MalformedTupleException;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.ReportConfig;
import tech.falabella.qa.type.Money;
import tech.falabella.qa.type.Number;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Getter
@NoArgsConstructor(staticName = "newInstance")
public class TokenMetrisConfig implements ReportConfig<TokenMetrisTuple> {
    private final Parameters parameters = Parameters.of(Map.of(
            "FechaInicial", Parameters.Value.builder()
                    .position(1)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    })
                    .build(),
            "FechaFinal", Parameters.Value.builder()
                    .position(2)
                    .sqlFormat(dVal -> {
                        if (null == dVal || dVal.isBlank())
                            return "";
                        DateTimeFormatter formatterWeb = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter formatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dVal, formatterWeb);
                        return date.format(formatterSql);
                    })
                    .build(),
            "IdWallet", Parameters.Value.builder().position(3).type("select")
                    .action(dVal -> Optional.ofNullable(TokenMetrisConfig.IdWallet.fromValue(dVal)).map(it -> it.description).orElse(""))
                    .sqlFormat(dVal -> Optional.ofNullable(TokenMetrisConfig.IdWallet.fromValue(dVal)).map(it -> it.internalId).orElse(""))
                    .build()
    ));
    private final String route = "Mastercard/TOKEN/Reporte Token Metrics";
    private final String query = """
            USE [CONCILIACION]
            
            DECLARE	@return_value int
            EXEC	@return_value = [nuevaconciliacion].[USP_Reporte_Metrics_Token]
            		@FechaInicial = ?,
            		@FechaFinal = ?,
            		@IdWallet = ?
            """;

    @Override
    public TokenMetrisTuple sqlMap(ResultSet resultSet){
        try {
            return TokenMetrisTuple.builder()
                    .descriptiontoken(resultSet.getString(2))
                    .debit(Number.from(resultSet.getString(3)))
                    .credit(Number.from(resultSet.getString(4)))
                    .prepaid(Money.from(resultSet.getString(5)))
                    .total(Number.from(resultSet.getString(6)))
                    .build();
        }
        catch (SQLException e) {
            throw new MalformedTupleException(e);
        }

    }

    @Override
    public TokenMetrisTuple csvMap(String[] result) {
        return TokenMetrisTuple.builder()
                .descriptiontoken(result[0])
                .debit(Number.from(result[1]))
                .credit(Number.from(result[2]))
                .prepaid(Money.from(result[3]))
                .total(Number.from(result[4]))
                .build();
    }

    @AllArgsConstructor
    public enum IdWallet {
        TODOS("1", "1", "TODOS"),
        Wallet_Remote("2", "101", "Wallet Remote"),
        Wallet_Remote_NFC_Payment("3", "102", "Wallet Remote NFC Payment"),
        Apple_Pay("4", "103", " Apple Pay"),
        Android_Pay("5", "216", "Android Pay"),
        Samsung_Pay("6", "217", "Samsung Pay"),
        Merchant_tokenization_program("7", "327", "Merchant tokenization program"),
        SIN_DESCRIPCION_337("8", "337", "SIN DESCRIPCION 337"),
        SIN_DESCRIPCION_455("9", "455", "SIN DESCRIPCION"),
        SIN_DESCRIPCION_A01("10", "A01", "SIN DESCRIPCION A01"),
        SIN_DESCRIPCIONA06("11", "A06", "SIN DESCRIPCIONA06"),
        SIN_DESCRIPCION_A85("12", "A85", "SIN DESCRIPCION A85"),
        SIN_DESCRIPCION_A95("13", "A95", "SIN DESCRIPCION A95"),
        SIN_DESCRIPCION_B20("14","B20","SIN DESCRIPCION B20"),
        SIN_DESCRIPCION_B87("15","B87","SIN DESCRIPCION B87"),
        SIN_DESCRIPCION_B90("16","B90","SIN DESCRIPCION B90"),
        SIN_DESCRIPCION_B91("17","B91","SIN DESCRIPCION B91"),
        SIN_DESCRIPCION_C25("18","C25","SIN DESCRIPCION C25"),
        SIN_DESCRIPCION_C43("19","C43","SIN DESCRIPCION C43"),
        SIN_DESCRIPCION_MRJ("20","MJR"," SIN DESCRIPCION MRJ");

        public final String value;
        public final String internalId;
        public final String description;

        public static IdWallet fromValue(String value) {
            for (TokenMetrisConfig.IdWallet it : TokenMetrisConfig.IdWallet.values()) {
                if (it.name().equalsIgnoreCase(value) || it.value.equalsIgnoreCase(value) || it.description.equalsIgnoreCase(value))
                    return it;
            }
            return null; //throw new EnumConstantNotPresentException(Evento.class, value);
        }

        }

}