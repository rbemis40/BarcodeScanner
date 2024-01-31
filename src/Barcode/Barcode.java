package Barcode;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;
import java.util.Locale;

public class Barcode {
    private JPanel upcPanel;
    private JTextField upcField;
    private JButton submitUPCButton;
    private JLabel upcLabel;
    private JPanel pastUPCs;
    private JLabel quantityLabel;
    private JTextField quantityField;
    private LinkedList<Product> products; // Stores all the newly scanned products

    public Barcode() {
        products = new LinkedList<Product>();

        $$$setupUI$$$();
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
            products.add(scannedProd);

            final String labelStr = String.format("%-3dx  %d", scannedProd.quantity, scannedProd.upc);

            // Update the UI
            JLabel newUPC = new JLabel(labelStr);
            // newUPC.setFont(upcField.getFont());
            newUPC.setFont(new Font("Monospaced", Font.PLAIN, 36));
            pastUPCs.add(newUPC);
            pastUPCs.revalidate();
        } catch (NumberFormatException e) {
            System.out.println("Invalid Barcode Scanned");
        } finally {
            upcField.setText("");
            quantityField.setText("1");
        }

    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("Ecomdash Barcode Scanner");
        frame.setMinimumSize(new Dimension(1366, 768));
        frame.setContentPane(new Barcode().upcPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
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
        upcPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(10, 10, 10, 10), -1, -1));
        upcField = new JTextField();
        Font upcFieldFont = this.$$$getFont$$$(null, -1, 36, upcField.getFont());
        if (upcFieldFont != null) upcField.setFont(upcFieldFont);
        upcPanel.add(upcField, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        upcLabel = new JLabel();
        Font upcLabelFont = this.$$$getFont$$$(null, -1, 36, upcLabel.getFont());
        if (upcLabelFont != null) upcLabel.setFont(upcLabelFont);
        upcLabel.setText("UPC");
        upcPanel.add(upcLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        upcPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(pastUPCs);
        quantityLabel = new JLabel();
        Font quantityLabelFont = this.$$$getFont$$$(null, -1, 36, quantityLabel.getFont());
        if (quantityLabelFont != null) quantityLabel.setFont(quantityLabelFont);
        quantityLabel.setText("Quantity");
        upcPanel.add(quantityLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        submitUPCButton = new JButton();
        submitUPCButton.setText("Submit");
        upcPanel.add(submitUPCButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quantityField = new JTextField();
        Font quantityFieldFont = this.$$$getFont$$$(null, -1, 36, quantityField.getFont());
        if (quantityFieldFont != null) quantityField.setFont(quantityFieldFont);
        quantityField.setText("1");
        upcPanel.add(quantityField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

    private void createUIComponents() {
        pastUPCs = new JPanel();
        pastUPCs.setLayout(new BoxLayout(pastUPCs, BoxLayout.Y_AXIS));
        // HOW TO ADD TO PAST UPCS
//        JLabel testLabel = new JLabel();
//        testLabel.setFont(new Font(null, Font.PLAIN, 3006));
//        testLabel.setText("Hello!!");
//        pastUPCs.add(testLabel);
    }
}
