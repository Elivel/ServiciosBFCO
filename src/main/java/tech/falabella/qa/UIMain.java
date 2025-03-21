package tech.falabella.qa;

import lombok.extern.slf4j.Slf4j;
import tech.falabella.qa.dto.Report;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.Arrays;

@Slf4j
public class UIMain extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox reportsBox;
    private JButton buttonBrowse;
    private JTable tableParameters;
    private JLabel picLabel;

    public UIMain() {
        //FlatLightLaf.setup();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Arrays.stream(Report.values()).forEach(reportsBox::addItem);
        printParameters();
        reportsBox.addActionListener(e -> {
            printParameters();
        });

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            buttonBrowse.addActionListener(e -> onOpenWeb());
        else
            buttonBrowse.setVisible(Boolean.FALSE);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        UIMain dialog = new UIMain();

        dialog.setTitle("SSRS Validator");
        dialog.setLocationRelativeTo(null);

        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(Boolean.TRUE);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onOpenWeb() {
        try {
            if (reportsBox.getSelectedItem() instanceof Report reportSelected) {
                var reportConfig = reportSelected.config.get();
                var uri = URI.create("http://f8sc00008/Reports/report/".concat(reportConfig.getRoute()));
                Desktop.getDesktop().browse(uri);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void printParameters() {
        if (reportsBox.getSelectedItem() instanceof Report reportSelected) {
            log.info(reportSelected.name());
            var reportConfig = reportSelected.config.get();

            DefaultTableModel model = new DefaultTableModel();
            tableParameters = new JTable(model);

            // Create a couple of columns
            model.addColumn("name");
            model.addColumn("value");

            // Append a row
            reportConfig.getParameters().forEach((name, index) -> {
                model.addRow(new Object[]{name, ""});
            });

            repaint();
        }
    }
}
