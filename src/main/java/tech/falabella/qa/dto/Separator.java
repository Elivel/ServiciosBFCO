package tech.falabella.qa.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Separator {
    COMMA(','), SEMICOLON(';'), TABULATOR('\t');

    public final char value;
}
