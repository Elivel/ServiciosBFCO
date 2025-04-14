package tech.falabella.qa;

import javax.swing.*;

public class GUIApplication {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) { }

        SwingUtilities.invokeLater(UIMain::new);
    }

}
