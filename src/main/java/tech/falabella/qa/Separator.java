package tech.falabella.qa;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Separator {
    COMMA(','), SEMICOLON(';'), TABULATOR('\t');

    public char value;
}
