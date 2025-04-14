package tech.falabella.qa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.adapter.FileStorageAdapter;
import tech.falabella.qa.dto.Report;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.service.ValidationServiceImpl;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.*;
import java.util.List;

@Slf4j
public class UIMain extends JFrame {

    private JPanel contentPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<Report> reportsBox;
    private JButton buttonBrowse;
    private JTable tableParameters;

    private JLabel picLabel;
    private JPanel mainPanel;
    private JPanel footerPanel;
    private JPanel headerPanel;
    private JPanel configPanel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField msUrlField;
    private JTextField rsUrlField;
    private JTextField outPathExportField;
    private JTextField outFileResultField;

    public UIMain() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        
        }

        setTitle("SSRS Validator");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(Boolean.TRUE);

        setContentPane(contentPanel);
        getRootPane().setDefaultButton(buttonOK);

        var iconURL = getClass().getClassLoader().getResource("images/banco-falabella.ico");

        if (iconURL != null) {
            ImageIcon imgIcon = new ImageIcon(iconURL);
            Image image = imgIcon.getImage();
            setIconImage(image);
        } else
            log.warn("Icono 'images/banco-falabella.ico' no encontrado en los recursos.");

        tableParameters.setModel(ParamsTableModel.newInstance());
        tableParameters.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (tableParameters.isEditing())
                    tableParameters.getCellEditor().stopCellEditing();
            }
        });

        Arrays.stream(Report.values()).filter(it -> it.enabled).forEach(reportsBox::addItem);
        printParameters();

        reportsBox.addActionListener(e -> printParameters());
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
            buttonBrowse.addActionListener(e -> onOpenWeb());
        else
            buttonBrowse.setVisible(Boolean.FALSE);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPanel.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(Boolean.TRUE);
    }

    private void onOK() {
        List<String> args = new ArrayList<>();
        if (reportsBox.getSelectedItem() instanceof Report reportSelected) {
            args.add("--report-name=" + reportSelected.name());
            args.add("--print");
            args.add("--execute-export-report");
            args.add("--skip-header");
            args.add("--separator=comma");
            args.add("--username=" + usernameField.getText());
            args.add("--password=" + String.valueOf(passwordField.getPassword()));
            args.add("--mssql-url=" + msUrlField.getText());
            args.add("--ssrs-url=" + rsUrlField.getText());
            args.add("--out-path-export=" + outPathExportField.getText());
            args.add("--out-file-result=" + outFileResultField.getText());

            Optional.of(tableParameters)
                    .map(it -> (ParamsTableModel) it.getModel())
                    .map(ParamsTableModel::getData)
                    .stream().flatMap(Collection::stream)
                    .forEach(row -> args.add("-D" + row[0] + "=" + row[1]));

        }

        var commandLine = new CommandLine(CommandArgs.newInstance());
        commandLine.setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE);

        commandLine.parseArgs(args.toArray(String[]::new));

        commandLine.execute(args.toArray(String[]::new));

        var command = commandLine.<CommandArgs>getCommand();

        var reportValidation = ValidationServiceImpl.newInstance(command.generateInput(),
                command.generatePersistence());

        final var result = reportValidation.processElements();

        var fileOutput = FileStorageAdapter.newInstance();
        fileOutput.generate(result, command);

    }

    private void onCancel() {
        dispose();
    }

    private void onOpenWeb() {
        try {
            if (reportsBox.getSelectedItem() instanceof Report reportSelected) {
                var reportConfig = reportSelected.config.get();
                var uri = URI.create(this.rsUrlField.getText().concat(reportConfig.getRoute()));
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

            Optional.of(tableParameters)
                    .map(it -> (ParamsTableModel) it.getModel())
                    .ifPresent(it -> it.reload(reportConfig.getParameters()));

            tableParameters.repaint();
        }
    }

    @Getter
    @NoArgsConstructor(staticName = "newInstance")
    static class ParamsTableModel extends AbstractTableModel {
        private final List<String[]> data = new ArrayList<>();
        private final String[] columnNames = {"Name", "Value"};

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (!isCellEditable(rowIndex, columnIndex))
                return;

            data.get(rowIndex)[columnIndex] = (String) aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public void reload(Parameters parameters) {
            data.clear();
            fireTableDataChanged();

            parameters.forEach((key, value) -> {
                data.add(new String[]{key, ""});
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            });
        }

    }

}
