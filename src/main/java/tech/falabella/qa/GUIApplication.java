package tech.falabella.qa;

import javax.swing.*;

public class GUIApplication {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        UIMain dialog = new UIMain();
        dialog.setTitle("SSRS Validator");
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(Boolean.TRUE);
    }

}
