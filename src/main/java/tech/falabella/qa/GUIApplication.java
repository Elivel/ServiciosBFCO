package tech.falabella.qa;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class GUIApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UIMain::new);
    }

}
