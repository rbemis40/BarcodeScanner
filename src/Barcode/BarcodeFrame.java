package Barcode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class BarcodeFrame {
    private JFrame frame;
    private JPanel upcPanel;
    private JTextField upcField;
    private JButton submitUPCButton;
    private JLabel upcLabel;
    private JScrollPane upcPane;
    private JLabel quantityLabel;
    private JTextField quantityField;
    private JSplitPane dataPane;
    private JScrollPane invPane;
    private JPanel pastUpcs;
    private JPanel invPanel;

    private ProductManager prodManager;
    private final int PROD_FONT_SIZE = 24;

    public BarcodeFrame(ProductManager prodManager) throws Exception {
        this.prodManager = prodManager;

        $$$setupUI$$$();

        addListeners();
        createFrame();
    }

    private void createFrame() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        frame = new JFrame("Ecomdash Barcode Scanner");
        frame.setMinimumSize(new Dimension(1366, 768));
        frame.setContentPane(upcPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }

    private void addListeners() {
        upcField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upcEntered();
            }
        });

        submitUPCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upcField.requestFocusInWindow();
                upcEntered();
            }
        });

        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When a quantity is entered, we want to return to the upc scanning field
                upcField.requestFocusInWindow();
            }
        });

        // Clear the quantity field when clicked
        quantityField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                quantityField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (quantityField.getText().isEmpty()) { // Default back to just quantity 1 if its empty
                    quantityField.setText("1");
                }
            }
        });
    }

    private void upcEntered() {
        try {
            final String upcFieldText = upcField.getText();
            final String quantityText = quantityField.getText();

            // Create the new product and set the quantity and upc >>> EVENTUALLY THE SKU ALSO
            long upcNum = Long.parseLong(upcFieldText);

            // Try to determine the quantity, if invalid, just assume 1
            int quantityNum;
            try {
                quantityNum = Integer.parseInt(quantityText);
            } catch (NumberFormatException e) {
                quantityNum = 1;
            }

            Product scannedProd = new Product(upcNum, quantityNum);
            try {
                // Try to add the product and automatically determine the SKU
                prodManager.searchFileForSku(scannedProd);
            } catch (ProductList.ProductNotFoundException e) {
                // The UPC is not already known, and needs a custom SKU
                // TODO: Update font size by passing a JLabel instead
                // TODO: Ensure that the user actually enters a value
                scannedProd.sku = (String) JOptionPane.showInputDialog(frame, "UPC not on file, enter new SKU:", "Enter new SKU", JOptionPane.PLAIN_MESSAGE, null, null, "");
                prodManager.addReferenceProduct(scannedProd);
            }

            prodManager.addScannedProduct(scannedProd);

            final String labelStr = String.format("%-3dx  %-13d - %s", scannedProd.quantity, scannedProd.upc, scannedProd.sku);

            // Update the UI
            JLabel newUPC = new JLabel(labelStr);
            // newUPC.setFont(upcField.getFont());
            newUPC.setFont(new Font("Monospaced", Font.PLAIN, PROD_FONT_SIZE));
            pastUpcs.add(newUPC);
            pastUpcs.revalidate();
        } catch (NumberFormatException e) {
            System.out.println("Invalid Barcode Scanned");
        } finally {
            upcField.setText("");
            quantityField.setText("1");
        }

    }

    public void updateInventoryPanel() {
        ProductList inv = prodManager.getFileProducts();
        for (Product curProd : inv) {
            final String labelStr = String.format("%-3dx  %-13d - %s", curProd.quantity, curProd.upc, curProd.sku); // TODO: Extract this into a simple function

            JLabel invLabel = new JLabel();
            invLabel.setFont(new Font("Monospaced", Font.PLAIN, PROD_FONT_SIZE));
            invLabel.setText(labelStr);

            invPanel.add(invLabel);
        }

        invPanel.revalidate();
    }

    private void createUIComponents() {
        pastUpcs = new JPanel();
        pastUpcs.setLayout(new BoxLayout(pastUpcs, BoxLayout.Y_AXIS));

        invPanel = new JPanel();
        invPanel.setLayout(new BoxLayout(invPanel, BoxLayout.Y_AXIS));
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        upcPanel = new JPanel();
        upcPanel.setLayout(new GridBagLayout());
        upcPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        upcLabel = new JLabel();
        Font upcLabelFont = this.$$$getFont$$$(null, -1, 36, upcLabel.getFont());
        if (upcLabelFont != null) upcLabel.setFont(upcLabelFont);
        upcLabel.setText("UPC");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        upcPanel.add(upcLabel, gbc);
        quantityLabel = new JLabel();
        Font quantityLabelFont = this.$$$getFont$$$(null, -1, 36, quantityLabel.getFont());
        if (quantityLabelFont != null) quantityLabel.setFont(quantityLabelFont);
        quantityLabel.setText("Quantity");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        upcPanel.add(quantityLabel, gbc);
        submitUPCButton = new JButton();
        submitUPCButton.setText("Submit");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        upcPanel.add(submitUPCButton, gbc);
        quantityField = new JTextField();
        Font quantityFieldFont = this.$$$getFont$$$(null, -1, 36, quantityField.getFont());
        if (quantityFieldFont != null) quantityField.setFont(quantityFieldFont);
        quantityField.setText("1");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        upcPanel.add(quantityField, gbc);
        upcField = new JTextField();
        Font upcFieldFont = this.$$$getFont$$$(null, -1, 36, upcField.getFont());
        if (upcFieldFont != null) upcField.setFont(upcFieldFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        upcPanel.add(upcField, gbc);
        dataPane = new JSplitPane();
        dataPane.setDividerLocation(-1);
        dataPane.setLastDividerLocation(441);
        dataPane.setResizeWeight(0.5);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        upcPanel.add(dataPane, gbc);
        upcPane = new JScrollPane();
        dataPane.setLeftComponent(upcPane);
        upcPane.setViewportView(pastUpcs);
        invPane = new JScrollPane();
        dataPane.setRightComponent(invPane);
        invPane.setViewportView(invPanel);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return upcPanel;
    }
}
