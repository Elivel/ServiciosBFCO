package tech.falabella.qa;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import tech.falabella.qa.adapter.FileStorageAdapter;
import tech.falabella.qa.dto.Report;
import tech.falabella.qa.service.ValidationServiceImpl;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class UIMain extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox reportsBox;
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
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Cargar el icono desde los recursos
        var iconURL = getClass().getClassLoader().getResource("images/banco-falabella.ico");

        if (iconURL != null) {
            ImageIcon imgIcon = new ImageIcon(iconURL);
            Image image = imgIcon.getImage();
            setIconImage(image); // Establecer el icono para el JDialog
        } else {
            log.warn("Icono 'images/banco-falabella.ico' no encontrado en los recursos.");
        }
        tableParameters.setModel(new ParamsTableModel());

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

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        List<String> args = new ArrayList<>();
        if (reportsBox.getSelectedItem() instanceof Report reportSelected) {
            var reportConfig = reportSelected.config.get();

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

            var params = tableParameters.getModel();
            var rows = params.getRowCount();

            for (int i = 0; i < rows; i++) {
                args.add("-D" + params.getValueAt(i, 0) + "=" + params.getValueAt(i, 1));
            }

        }

        var commandLine = new CommandLine(CommandArgs.newInstance());
        commandLine.setCaseInsensitiveEnumValuesAllowed(Boolean.TRUE);

        commandLine.parseArgs(args.toArray(String[]::new));

        int exitCode = commandLine.execute(args.toArray(String[]::new));

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

            ParamsTableModel model = (ParamsTableModel) tableParameters.getModel();
            model.datos.clear();
            model.fireTableDataChanged();

            reportConfig.getParameters().forEach((name, index) ->
                    model.addRow(new String[]{name, ""}));

            tableParameters.repaint();
        }
    }

    @NoArgsConstructor
    class ParamsTableModel extends AbstractTableModel {
        private final List<String[]> datos = new ArrayList<>();
        private final String[] columnas = {"Name", "Value"};

        @Override
        public int getRowCount() {
            return datos.size();
        }

        @Override
        public int getColumnCount() {
            return columnas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return datos.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnas[column];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1; // Solo la columna de "Value" es editable
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 1) { // Solo actualizamos la columna de "Value"
                datos.get(rowIndex)[columnIndex] = (String) aValue;
                fireTableCellUpdated(rowIndex, columnIndex); // Notificamos a la tabla que el dato ha cambiado
            }
        }

        public void addRow(String[] nuevaFila) {
            datos.add(nuevaFila);
            fireTableRowsInserted(datos.size() - 1, datos.size() - 1);
        }

    }

}