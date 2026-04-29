package EndoBuilder.src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EndoBuilder extends JFrame {

    private final DefaultTableModel endorsementModel =
        new DefaultTableModel(new String[]{"Code", "Title", "Body"}, 0);
    private final JCheckBox[] headerCheckBoxes = new JCheckBox[2];
    private JTextField charLengthField;
    private JTextField lineSpaceField;
    private JTextField paraSpaceField;
    private JTextField bottomMarginField;
    private JTextField pageHeightField;
    private JTextField totalHeightField;
    private JTextField currentHeightField;
    private JTextField boldValueField;
    private JTextField normalValueField;
    private JTextField totalPageCountField;
    private JCheckBox pageCounterCheckBox;
    private JTextField pageMacroField;
    private JTextField schemeCodeField;
    private JTextField policyTypeField;
    private JTextField brokerCode1Field;
    private JTextField brokerCode2Field;
    private JTextField affinityField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JRadioButton fsetRadio;
    private JRadioButton fontRadio;

    public EndoBuilder() {
        setTitle("EndoBuilder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = {
            "Character Length","Line Space", "Paragraph Space", "Bottom Margin", "Page Height Calc",
            "Current Height Calc", "Page Height", "Bold Value", "Normal Value",
            "Scheme Code", "Policy Type", "Broker Code 1",
            "Broker Code 2", "Affinity Code", "Start Date", "End Date"
        };

        String[] defaults = {
            "100", "0.12", "0.15", "0.20",
            "9", "8", "9", "56",
            "13", "XX", "PC", "ALL",
            "ALL", "ALL", "PAST", "FUTURE"
        };

        // Checkbox headers at the top of each column
        String[] headerLabels = {"Classic", "Precis"};
        for (int col = 0; col < 2; col++) {
            gbc.gridx = col * 2;
            gbc.gridy = 0;
            mainPanel.add(new JLabel(headerLabels[col]), gbc);

            gbc.gridx = col * 2 + 1;
            headerCheckBoxes[col] = new JCheckBox();
            mainPanel.add(headerCheckBoxes[col], gbc);
        }

        JTextField boldField = null;
        JTextField normalField = null;

        for (int i = 0; i < 16; i++) {
            int col = i < 9 ? 0 : 1;
            int row;
            if (col == 0) {
                if (i < 6) row = i + 1;       // rows 1-6: Char Length through Current Height Calc
                else if (i == 6) row = 9;      // row 9: Page Height (after checkbox at 7, Total Page Count at 8)
                else row = i + 5;              // rows 12-13: Bold Value, Normal Value
            } else {
                row = (i - 9) + 1;
            }

            gbc.gridx = col * 2;
            gbc.gridy = row;
            mainPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = col * 2 + 1;
            JTextField textField = new JTextField(defaults[i], 7);
            mainPanel.add(textField, gbc);

            if (i == 0) charLengthField = textField;
            if (i == 1) lineSpaceField = textField;
            if (i == 2) paraSpaceField = textField;
            if (i == 3) bottomMarginField = textField;
            if (i == 4) pageHeightField = textField;
            if (i == 5) currentHeightField = textField;
            if (i == 6) totalHeightField = textField;
            if (i == 7) { boldField = textField; boldValueField = textField; }
            if (i == 8) { normalField = textField; normalValueField = textField; }
            if (i == 9) schemeCodeField = textField;
            if (i == 10) policyTypeField = textField;
            if (i == 11) brokerCode1Field = textField;
            if (i == 12) brokerCode2Field = textField;
            if (i == 13) affinityField = textField;
            if (i == 14) startDateField = textField;
            if (i == 15) endDateField = textField;
        }

        boldField.setText("56");
        normalField.setText("13");

        // Create Page Counter checkbox
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Create Page Counter"), gbc);
        gbc.gridx = 1;
        pageCounterCheckBox = new JCheckBox();
        mainPanel.add(pageCounterCheckBox, gbc);

        // Total Page Count Calc field
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel totalPageCountLabel = new JLabel("Total Page Count Calc");
        mainPanel.add(totalPageCountLabel, gbc);
        gbc.gridx = 1;
        totalPageCountField = new JTextField("17", 7);
        mainPanel.add(totalPageCountField, gbc);

        // Show/hide Total Page Count Calc based on checkbox
        pageCounterCheckBox.addActionListener(e -> {
            totalPageCountLabel.setEnabled(pageCounterCheckBox.isSelected());
            totalPageCountField.setEnabled(pageCounterCheckBox.isSelected());
        });
        totalPageCountLabel.setEnabled(false);
        totalPageCountField.setEnabled(false);

        // Page Macro field above radio buttons
        gbc.gridx = 0;
        gbc.gridy = 10;
        mainPanel.add(new JLabel("Page Macro"), gbc);
        gbc.gridx = 1;
        pageMacroField = new JTextField("NEWP", 7);
        mainPanel.add(pageMacroField, gbc);

        fsetRadio = new JRadioButton("FSET");
        fontRadio = new JRadioButton("FONT", true);
        ButtonGroup fontGroup = new ButtonGroup();
        fontGroup.add(fsetRadio);
        fontGroup.add(fontRadio);

        final JTextField boldRef = boldField;
        final JTextField normalRef = normalField;
        fontRadio.addActionListener(e -> { boldRef.setText("56"); normalRef.setText("13"); });
        fsetRadio.addActionListener(e -> { boldRef.setText("03b"); normalRef.setText("00b"); });

        gbc.gridx = 0;
        gbc.gridy = 11;
        mainPanel.add(fsetRadio, gbc);
        gbc.gridx = 1;
        mainPanel.add(fontRadio, gbc);

        // Buttons under Precis-side inputs
        gbc.gridy = 11;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton endorsementsBtn = new JButton("Endorsements");
        endorsementsBtn.addActionListener(e -> openEndorsementsWindow());
        mainPanel.add(endorsementsBtn, gbc);

        gbc.gridy = 12;
        JButton generateBtn = new JButton("Generate");
        generateBtn.addActionListener(e -> {
            if (!headerCheckBoxes[0].isSelected() && !headerCheckBoxes[1].isSelected()) {
                JOptionPane.showMessageDialog(this,
                    "Please select at least one platform before generating.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (endorsementModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Please add at least one endorsement before generating.",
                    "No Endorsements", JOptionPane.WARNING_MESSAGE);
                return;
            }
            openGenerateWindow();
        });
        mainPanel.add(generateBtn, gbc);
        gbc.gridwidth = 1;

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void openGenerateWindow() {
        JDialog genFrame = new JDialog(this, "Generate", true);
        genFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea leftArea = new JTextArea();
        leftArea.setEditable(false);

        JTextArea rightArea = new JTextArea();
        rightArea.setEditable(false);

        JTextArea pageCounterArea = new JTextArea();
        pageCounterArea.setEditable(false);

        // Generate classic output in the left pane
        if (headerCheckBoxes[0].isSelected()) {
            int charLen = 80;
            try { charLen = Integer.parseInt(charLengthField.getText().trim()); } catch (NumberFormatException ignored) {}
            double lineSpaceVal = 0.12;
            try { lineSpaceVal = Double.parseDouble(lineSpaceField.getText().trim()); } catch (NumberFormatException ignored) {}
            double bottomMarginVal = 0.20;
            try { bottomMarginVal = Double.parseDouble(bottomMarginField.getText().trim()); } catch (NumberFormatException ignored) {}
            double paraSpaceVal = 0.15;
            try { paraSpaceVal = Double.parseDouble(paraSpaceField.getText().trim()); } catch (NumberFormatException ignored) {}
            String lineSpace = lineSpaceField.getText().trim();
            String paraSpace = paraSpaceField.getText().trim();
            String bottomMargin = bottomMarginField.getText().trim();
            String currentHeight = currentHeightField.getText().trim();
            String pageHeight = pageHeightField.getText().trim();
            String totalHeight = totalHeightField.getText().trim();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < endorsementModel.getRowCount(); i++) {
                String code = (String) endorsementModel.getValueAt(i, 0);
                String title = (String) endorsementModel.getValueAt(i, 1);
                String wording = (String) endorsementModel.getValueAt(i, 2);
                if (wording != null) {
                    double[] endoHeight = new double[]{0};
                    String wrappedText = wrapText(wording, charLen, lineSpace, bottomMargin, lineSpaceVal, bottomMarginVal, paraSpace, paraSpaceVal, endoHeight);
                    // Include the title line in the height
                    endoHeight[0] += lineSpaceVal;
                    String heightStr = String.format("%.2f", endoHeight[0]);

                    String radioLabel = fontRadio.isSelected() ? "FONT" : "FSET";
                    String boldVal = boldValueField.getText().trim();
                    String normalVal = normalValueField.getText().trim();

                    sb.append(".SCHLOOP #5,4\n");
                    sb.append(".Q S.ECODE=").append(code != null ? code : "").append("\n");
                    sb.append(".IFF\n");
                    sb.append(".GOTO NEXTEND\n");
                    sb.append(".IFTF\n");
                    sb.append("\n");
                    sb.append(".CALC #").append(currentHeight).append("=#").append(currentHeight).append("+").append(heightStr).append("\n");
                    sb.append(".Q #").append(currentHeight).append(">{#").append(pageHeight).append("}\n");
                    sb.append(".IFT\n");
                    sb.append("    CALL ").append(pageMacroField.getText().trim()).append(";\n");
                    sb.append(".CALC #").append(currentHeight).append("=").append(heightStr).append("\n");
                    sb.append(".CALC #").append(totalHeight).append("=").append(pageHeight).append("\n");
                    sb.append(".IFTF\n");
                    sb.append("\n");
                    // Title line - always bold
                    String titleDelim = (title != null && title.contains("\"")) ? "'" : "\"";
                    sb.append(radioLabel).append(" ").append(boldVal).append("; ");
                    sb.append("TEXT").append(titleDelim).append(title != null ? title : "").append(titleDelim).append("; ");
                    sb.append(radioLabel).append(" ").append(normalVal).append("; ");
                    sb.append("MRP 0.00, ").append(lineSpace).append(";\n");
                    sb.append(wrappedText).append("\n");
                    sb.append("\n");
                    sb.append(".LABEL NEXTEND");

                    if (i < endorsementModel.getRowCount() - 1) sb.append("\n\n");
                }
            }
            leftArea.setText(sb.toString());
        }

        // Generate Page Counter output
        if (headerCheckBoxes[0].isSelected() && pageCounterCheckBox.isSelected()) {
            String totalPageCount = totalPageCountField.getText().trim();
            String currentHeight = currentHeightField.getText().trim();
            String pageHeight = pageHeightField.getText().trim();

            StringBuilder pc = new StringBuilder();
            for (int i = 0; i < endorsementModel.getRowCount(); i++) {
                String code = (String) endorsementModel.getValueAt(i, 0);
                String wording = (String) endorsementModel.getValueAt(i, 2);
                if (code != null && !code.isEmpty() && wording != null) {
                    int charLen = 80;
                    try { charLen = Integer.parseInt(charLengthField.getText().trim()); } catch (NumberFormatException ignored) {}
                    double lineSpaceVal = 0.12;
                    try { lineSpaceVal = Double.parseDouble(lineSpaceField.getText().trim()); } catch (NumberFormatException ignored) {}
                    double bottomMarginVal = 0.20;
                    try { bottomMarginVal = Double.parseDouble(bottomMarginField.getText().trim()); } catch (NumberFormatException ignored) {}
                    double paraSpaceVal = 0.15;
                    try { paraSpaceVal = Double.parseDouble(paraSpaceField.getText().trim()); } catch (NumberFormatException ignored) {}
                    double[] endoHeight = new double[]{0};
                    wrapText(wording, charLen, lineSpaceField.getText().trim(),
                        bottomMarginField.getText().trim(), lineSpaceVal, bottomMarginVal,
                        paraSpaceField.getText().trim(), paraSpaceVal, endoHeight);
                    endoHeight[0] += lineSpaceVal;
                    String heightStr = String.format("%.2f", endoHeight[0]);

                    if (pc.length() > 0) pc.append("\n\n");
                    pc.append(".SCHLOOP #5,4\n");
                    pc.append(".Q S.ECODE=").append(code).append("\n");
                    pc.append(".IFF\n");
                    pc.append(".GOTO NEXTEND\n");
                    pc.append(".IFTF\n");
                    pc.append("\n");
                    pc.append(".CALC #").append(currentHeight).append("=#").append(currentHeight).append("+").append(heightStr).append("\n");
                    pc.append(".Q #").append(currentHeight).append(">{#").append(pageHeight).append("}\n");
                    pc.append(".IFT\n");
                    pc.append(".CALC #").append(totalPageCount).append("=#").append(totalPageCount).append("+1\n");
                    pc.append(".CALC #").append(currentHeight).append("=").append(heightStr).append("\n");
                    pc.append(".CALC #").append(pageHeight).append("=").append(pageHeight).append("\n");
                    pc.append(".IFTF\n");
                    pc.append("\n");
                    pc.append(".LABEL NEXTEND");
                }
            }
            pageCounterArea.setText(pc.toString());
        }

        // Generate Precis output in the right pane
        if (headerCheckBoxes[1].isSelected()) {
            String schemeCode = schemeCodeField.getText().trim();
            String policyType = policyTypeField.getText().trim();
            String brokerCode1 = brokerCode1Field.getText().trim();
            String brokerCode2 = brokerCode2Field.getText().trim();
            String affinity = affinityField.getText().trim();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();

            String[] bc1Values = brokerCode1.split(",");
            String[] bc2Values = brokerCode2.split(",");
            String[] affValues = affinity.split(",");

            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < endorsementModel.getRowCount(); i++) {
                String code = (String) endorsementModel.getValueAt(i, 0);
                String title = (String) endorsementModel.getValueAt(i, 1);
                String wording = (String) endorsementModel.getValueAt(i, 2);
                if (wording != null) {
                    // Convert wording HTML to XHTML content
                    String xhtmlContent = convertToXhtml(wording);

                    for (String bc1 : bc1Values) {
                        for (String bc2 : bc2Values) {
                            for (String aff : affValues) {
                                if (!first) sb.append("\n");
                                first = false;

                                sb.append("\t<endorsement>\n");
                                sb.append("\t    <policy-type>").append(policyType).append("</policy-type>\n");
                                sb.append("\t    <scheme-code>").append(schemeCode).append("</scheme-code>\n");
                                sb.append("\t    <endorsement-details>\n");
                                sb.append("\t        <endorsement-code>").append(code != null ? code : "").append("</endorsement-code>\n");
                                sb.append("\t        <broker-code-1>").append(bc1.trim()).append("</broker-code-1>\n");
                                sb.append("\t        <broker-code-2>").append(bc2.trim()).append("</broker-code-2>\n");
                                sb.append("\t        <affinity>").append(aff.trim()).append("</affinity>\n");
                                sb.append("\t        <start-date>").append(startDate).append("</start-date>\n");
                                sb.append("\t        <end-date>").append(endDate).append("</end-date>\n");
                                sb.append("\t        <wording><![CDATA[<div>\n");
                                sb.append("\t\t<h4>").append(title != null ? title : "").append("</h4>\n");
                                sb.append("\t\t").append(xhtmlContent).append("\n");
                                sb.append("\t\t</div>]]></wording>\n");
                                sb.append("\t    </endorsement-details>\n");
                                sb.append("\t</endorsement>");
                            }
                        }
                    }
                }
            }
            rightArea.setText(sb.toString());
        }

        JScrollPane leftScroll = new JScrollPane(leftArea);
        JScrollPane pageCounterScroll = new JScrollPane(pageCounterArea);
        JScrollPane rightScroll = new JScrollPane(rightArea);

        boolean showClassic = headerCheckBoxes[0].isSelected();
        boolean showPageCounter = showClassic && pageCounterCheckBox.isSelected();
        boolean showPrecis = headerCheckBoxes[1].isSelected();

        leftScroll.setVisible(showClassic);
        pageCounterScroll.setVisible(showPageCounter);
        rightScroll.setVisible(showPrecis);

        // Adjust layout columns based on what's visible
        int cols = (showClassic ? 1 : 0) + (showPageCounter ? 1 : 0) + (showPrecis ? 1 : 0);
        panel.setLayout(new GridLayout(1, Math.max(cols, 1), 10, 0));

        if (leftScroll.isVisible()) panel.add(leftScroll);
        if (pageCounterScroll.isVisible()) panel.add(pageCounterScroll);
        if (rightScroll.isVisible()) panel.add(rightScroll);

        genFrame.add(panel);
        genFrame.setSize(600, 400);
        genFrame.setLocationRelativeTo(this);
        genFrame.setVisible(true);
    }

    private String wrapText(String text, int maxLen, String lineSpace, String lastLineSpace,
                              double lineSpaceVal, double lastLineSpaceVal,
                              String paraSpace, double paraSpaceVal, double[] height) {
        String radioLabel = fontRadio.isSelected() ? "FONT" : "FSET";
        String boldVal = boldValueField.getText().trim();
        String normalVal = normalValueField.getText().trim();

        // Pre-process: convert <li> items into marked paragraphs with list type info
        String processed = text;

        // Parse list blocks to tag each <li> with type and index
        java.util.regex.Pattern listPat = java.util.regex.Pattern.compile(
            "(?i)(<[uo]l[^>]*>)(.*?)(</[uo]l>)", java.util.regex.Pattern.DOTALL);
        java.util.regex.Matcher lm = listPat.matcher(processed);
        StringBuffer sb2 = new StringBuffer();
        while (lm.find()) {
            String openTag = lm.group(1);
            String content = lm.group(2);
            boolean ordered = openTag.toLowerCase().contains("<ol");
            String olType = "1"; // default numeric
            if (ordered) {
                java.util.regex.Matcher tm = java.util.regex.Pattern.compile(
                    "(?i)type\\s*=\\s*[\"']([^\"']*)[\"']").matcher(openTag);
                if (tm.find()) olType = tm.group(1);
            }

            // Replace each <li> with a marker encoding the bullet text
            StringBuilder replacement = new StringBuilder();
            String[] items = content.split("(?i)<li[^>]*>");
            int itemIndex = 0;
            for (String item : items) {
                String trimmed = item.replaceAll("(?i)</li>", "").trim();
                if (trimmed.isEmpty()) continue;
                itemIndex++;
                String bulletText;
                if (!ordered) {
                    bulletText = "CALLBULL";
                } else {
                    bulletText = formatOlIndex(itemIndex, olType);
                }
                replacement.append("<p>XLISTART:").append(bulletText).append(":");
                replacement.append(trimmed).append(":XLIEND</p>");
            }
            lm.appendReplacement(sb2, java.util.regex.Matcher.quoteReplacement(replacement.toString()));
        }
        lm.appendTail(sb2);
        processed = sb2.toString();

        // Strip any remaining list container tags
        processed = processed.replaceAll("(?i)</?[uo]l[^>]*>", "");

        // Split the HTML into paragraph strings by <p> or <br> tags
        // Each <p>...</p> block or <br>-separated chunk becomes its own string
        String[] rawParagraphs = processed.split("(?i)(<p[^>]*>|<br\\s*/?>)");

        java.util.List<String> paragraphs = new java.util.ArrayList<>();
        java.util.List<Boolean> isListItem = new java.util.ArrayList<>();
        java.util.List<String> listBulletTexts = new java.util.ArrayList<>();

        // Patterns for fake bullet/numbered list items pasted from Word
        java.util.regex.Pattern fakeBulletPat = java.util.regex.Pattern.compile(
            "^[\\u00b7\\u2022\\u2013\\u2014\\-][\\s\\u00a0]+");
        java.util.regex.Pattern fakeNumberPat = java.util.regex.Pattern.compile(
            "^(\\d+|[a-zA-Z]|[ivxlcdmIVXLCDM]+)[.)][\\s\\u00a0]+");

        for (String raw : rawParagraphs) {
            // Clean each paragraph: replace &nbsp;, strip all tags except <b></b>, collapse spaces
            String cleaned = raw.replaceAll("&nbsp;", " ");
            cleaned = cleaned.replaceAll("&#160;", " ");
            cleaned = cleaned.replaceAll("&#xA0;", " ");
            cleaned = cleaned.replaceAll("&middot;", "\u00b7");
            cleaned = cleaned.replaceAll("<(?!/?b>)[^>]*>", "");
            cleaned = decodeHtmlEntities(cleaned);
            cleaned = cleaned.replaceAll("[\\s\\u00a0]+", " ").trim();
            if (!cleaned.isEmpty()) {
                boolean listItem = cleaned.startsWith("XLISTART:");
                String bulletText = "";
                if (listItem) {
                    cleaned = cleaned.substring("XLISTART:".length());
                    // Extract bullet text before the second colon
                    int colonIdx = cleaned.indexOf(":");
                    if (colonIdx >= 0) {
                        bulletText = cleaned.substring(0, colonIdx);
                        cleaned = cleaned.substring(colonIdx + 1);
                    }
                    if (cleaned.endsWith(":XLIEND")) {
                        cleaned = cleaned.substring(0, cleaned.length() - ":XLIEND".length());
                    }
                    cleaned = cleaned.trim();
                } else if (fakeBulletPat.matcher(cleaned).find()) {
                    // Fake bullet from Word paste
                    listItem = true;
                    bulletText = "CALLBULL";
                    cleaned = fakeBulletPat.matcher(cleaned).replaceFirst("").trim();
                } else {
                    java.util.regex.Matcher numMatcher = fakeNumberPat.matcher(cleaned);
                    if (numMatcher.find()) {
                        // Fake numbered item from Word paste
                        listItem = true;
                        String marker = numMatcher.group(1);
                        // Build the display text including the delimiter
                        String delimiter = cleaned.substring(marker.length(), marker.length() + 1);
                        bulletText = marker + delimiter;
                        cleaned = numMatcher.replaceFirst("").trim();
                    }
                }
                if (!cleaned.isEmpty()) {
                    cleaned = cleaned.replace("£", "#");
                    paragraphs.add(cleaned);
                    isListItem.add(listItem);
                    listBulletTexts.add(bulletText);
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int para = 0; para < paragraphs.size(); para++) {
            String paragraph = paragraphs.get(para);
            boolean isLastParagraph = (para == paragraphs.size() - 1);
            boolean listItem = isListItem.get(para);
            int effectiveMaxLen = listItem ? (maxLen - 10) : maxLen;

            // Extract bold and non-bold segments for this paragraph
            java.util.List<String[]> segments = new java.util.ArrayList<>();
            int pos = 0;
            while (pos < paragraph.length()) {
                int boldStart = paragraph.indexOf("<b>", pos);
                if (boldStart == -1) {
                    String remainder = paragraph.substring(pos).trim();
                    if (!remainder.isEmpty()) segments.add(new String[]{remainder, "normal"});
                    break;
                }
                if (boldStart > pos) {
                    String before = paragraph.substring(pos, boldStart).trim();
                    if (!before.isEmpty()) segments.add(new String[]{before, "normal"});
                }
                int boldEnd = paragraph.indexOf("</b>", boldStart);
                if (boldEnd == -1) boldEnd = paragraph.length();
                String rawBold = paragraph.substring(boldStart + 3, boldEnd);

                String leadingSpaces = rawBold.replaceAll("^(\\s*).*", "$1");
                String trailingSpaces = rawBold.replaceAll(".*?(\\s*)$", "$1");
                String boldText = rawBold.trim();

                if (!leadingSpaces.isEmpty()) segments.add(new String[]{" ", "normal"});
                if (!boldText.isEmpty()) segments.add(new String[]{boldText, "bold"});
                if (!trailingSpaces.isEmpty()) segments.add(new String[]{" ", "normal"});
                pos = boldEnd + 4;
            }

            // Build word tokens for this paragraph
            java.util.List<String[]> tokens = new java.util.ArrayList<>();
            for (String[] seg : segments) {
                for (String w : seg[0].split("\\s+")) {
                    if (!w.isEmpty()) tokens.add(new String[]{w, seg[1]});
                }
            }

            // Emit bullet/number and indent before the first line of a list item
            if (listItem) {
                String bulletText = listBulletTexts.get(para);
                if (bulletText.equals("CALLBULL")) {
                    result.append("MRP 0.10, 0.00; CALL BULL; MRP 0.15, 0.00;\n");
                } else {
                    result.append("MRP 0.10, 0.00; TEXT\"").append(bulletText).append("\"; MRP 0.15, 0.00;\n");
                }
                height[0] += lineSpaceVal;
            }

            // Word-wrap this paragraph: n character check per line
            StringBuilder currentLine = new StringBuilder();
            int lineLen = 0;
            boolean inBold = false;
            boolean lineHadBold = false;

            for (int t = 0; t < tokens.size(); t++) {
                String word = tokens.get(t)[0];
                boolean wordBold = tokens.get(t)[1].equals("bold");

                // Reached n characters - create new line with lineSpace
                if (lineLen > 0 && lineLen + 1 + word.length() > effectiveMaxLen) {
                    // Only close bold if the next word (current word on new line) is not bold
                    if (inBold && !wordBold) {
                        currentLine.append("\",E; ").append(radioLabel).append(" ").append(normalVal).append("; TEXT\"");
                        inBold = false;
                    }
                    if (lineHadBold) {
                        result.append("SCP; ");
                    }
                    result.append("TEXT\"").append(currentLine).append("\";");
                    if (lineHadBold) {
                        result.append(" RPP;");
                    }
                    result.append(" MRP 0.00, ").append(lineSpace).append(";\n");
                    height[0] += lineSpaceVal;
                    currentLine = new StringBuilder();
                    lineLen = 0;
                    lineHadBold = inBold; // carry over if still bold
                }

                if (wordBold && !inBold) {
                    if (lineLen > 0) { currentLine.append(" "); lineLen++; }
                    currentLine.append("\",E; ").append(radioLabel).append(" ").append(boldVal).append("; TEXT\"");
                    inBold = true;
                    lineHadBold = true;
                } else if (!wordBold && inBold) {
                    currentLine.append("\",E; ").append(radioLabel).append(" ").append(normalVal).append("; TEXT\"");
                    if (lineLen > 0) { currentLine.append(" "); lineLen++; }
                    inBold = false;
                } else if (lineLen > 0) {
                    currentLine.append(" ");
                    lineLen++;
                }
                if (wordBold) lineHadBold = true;

                currentLine.append(word);
                lineLen += word.length();
            }

            // End of this string - flush with paraSpace (or lastLineSpace if final paragraph)
            if (currentLine.length() > 0) {
                if (inBold) {
                    currentLine.append("\",E; ").append(radioLabel).append(" ").append(normalVal).append("; TEXT\"");
                }
                if (lineHadBold) {
                    result.append("SCP; ");
                }
                if (isLastParagraph) {
                    result.append("TEXT\"").append(currentLine).append("\";");
                    if (lineHadBold) {
                        result.append(" RPP;");
                    }
                    result.append(" MRP 0.00, ").append(lastLineSpace).append(";\n");
                } else {
                    result.append("TEXT\"").append(currentLine).append("\";");
                    if (lineHadBold) {
                        result.append(" RPP;");
                    }
                    result.append(" MRP 0.00, ").append(paraSpace).append(";\n");
                }
                if (isLastParagraph) {
                    height[0] += lastLineSpaceVal;
                } else {
                    height[0] += paraSpaceVal;
                }
            }

            // Reset indent after list item ends
            if (listItem) {
                result.append("MRP -0.25, 0.00;\n");
            }
        }

        // Clean up empty TEXT artifacts
        String output = result.toString();
        output = output.replace("TEXT\"\",E; ", "");
        output = output.replace("TEXT\"\"; ", "");

        // If any line contains a literal " in the text content, switch TEXT delimiters to single quotes
        StringBuilder swapped = new StringBuilder();
        for (String line : output.split("\n", -1)) {
            if (line.contains("TEXT\"")) {
                // Extract text content between TEXT delimiters to check for quotes
                String contentCheck = line;
                // Remove the TEXT"..." command wrappers to inspect actual content
                String stripped = contentCheck.replaceAll("TEXT\"", "").replaceAll("\",E;", "").replaceAll("\";", "");
                if (stripped.contains("\"")) {
                    line = line.replace("TEXT\"", "TEXT'").replace("\",E;", "',E;").replace("\";", "';");
                }
            }
            swapped.append(line).append("\n");
        }
        // Remove trailing extra newline
        output = swapped.length() > 0 ? swapped.substring(0, swapped.length() - 1) : "";

        return output;
    }

    private String formatOlIndex(int index, String type) {
        String value;
        switch (type) {
            case "i": value = toRoman(index).toLowerCase(); break;
            case "I": value = toRoman(index); break;
            case "a": value = toAlpha(index).toLowerCase(); break;
            case "A": value = toAlpha(index); break;
            default:  value = String.valueOf(index); break;
        }
        return value + ".";
    }

    private String toRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[num / 1000] + hundreds[(num % 1000) / 100] + tens[(num % 100) / 10] + ones[num % 10];
    }

    private String toAlpha(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            num--;
            sb.insert(0, (char) ('A' + (num % 26)));
            num /= 26;
        }
        return sb.toString();
    }

    private String decodeHtmlEntities(String text) {
        // Decode common HTML entities to their raw characters
        String result = text;
        result = result.replace("&amp;", "&");
        result = result.replace("&lt;", "<");
        result = result.replace("&gt;", ">");
        result = result.replace("&quot;", "\"");
        result = result.replace("&apos;", "'");
        result = result.replace("&#39;", "'");
        // Decode any remaining numeric character references (&#NNN;)
        java.util.regex.Pattern numericEntity = java.util.regex.Pattern.compile("&#(\\d+);");
        java.util.regex.Matcher matcher = numericEntity.matcher(result);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            int codePoint = Integer.parseInt(matcher.group(1));
            matcher.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(String.valueOf((char) codePoint)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String encodeXmlEntities(String text) {
        // First decode any HTML entities to raw characters, then re-encode for XML
        // Preserve <b> and </b> tags by encoding only text outside them
        String decoded = decodeHtmlEntities(text);
        StringBuilder result = new StringBuilder();
        int pos = 0;
        while (pos < decoded.length()) {
            int boldOpen = decoded.indexOf("<b>", pos);
            int boldClose = decoded.indexOf("</b>", pos);
            int nextTag = -1;
            String tag = null;
            if (boldOpen >= 0 && (boldClose < 0 || boldOpen <= boldClose)) {
                nextTag = boldOpen; tag = "<b>";
            } else if (boldClose >= 0) {
                nextTag = boldClose; tag = "</b>";
            }
            if (nextTag < 0) {
                result.append(encodeXmlText(decoded.substring(pos)));
                break;
            }
            result.append(encodeXmlText(decoded.substring(pos, nextTag)));
            result.append(tag);
            pos = nextTag + tag.length();
        }
        return result.toString();
    }

    private String encodeXmlText(String text) {
        String result = text;
        result = result.replace("&", "&amp;");
        result = result.replace("£", "&pound;");
        result = result.replace("<", "&lt;");
        result = result.replace(">", "&gt;");
        return result;
    }

    private String convertToXhtml(String html) {
        // Convert Swing HTML editor output to clean XHTML
        String processed = html.replaceAll("&nbsp;", " ");
        processed = processed.replaceAll("&#160;", " ");
        processed = processed.replaceAll("&#xA0;", " ");
        processed = processed.replaceAll("&middot;", "\u00b7");

        // Extract and convert list blocks before paragraph processing
        // Replace list blocks with a placeholder, process them separately
        java.util.List<String> listBlocks = new java.util.ArrayList<>();
        java.util.regex.Pattern listPattern = java.util.regex.Pattern.compile(
            "(?i)(<[uo]l[^>]*>)(.*?)(</[uo]l>)", java.util.regex.Pattern.DOTALL);
        java.util.regex.Matcher listMatcher = listPattern.matcher(processed);
        StringBuffer withPlaceholders = new StringBuffer();
        while (listMatcher.find()) {
            // Preserve only the 'type' attribute on <ol> tags, strip everything else
            String rawTag = listMatcher.group(1);
            String listTag;
            java.util.regex.Matcher typeAttr = java.util.regex.Pattern.compile(
                "(?i)type\\s*=\\s*[\"']([^\"']*)[\"']").matcher(rawTag);
            if (rawTag.toLowerCase().contains("<ol") && typeAttr.find()) {
                listTag = "<ol type=\"" + typeAttr.group(1) + "\">";
            } else if (rawTag.toLowerCase().contains("<ol")) {
                listTag = "<ol>";
            } else {
                listTag = "<ul>";
            }
            String closeTag = listMatcher.group(3).toLowerCase();
            String listContent = listMatcher.group(2);
            // Clean each <li> inside the list
            StringBuilder cleanList = new StringBuilder();
            cleanList.append(listTag).append("\n");
            String[] items = listContent.split("(?i)<li[^>]*>");
            for (String item : items) {
                String cleaned = item.replaceAll("(?i)</li>", "");
                cleaned = cleaned.replaceAll("<(?!/?b>)[^>]*>", "");
                cleaned = cleaned.replaceAll("[ \\t\\n\\r]+", " ").trim();
                if (!cleaned.isEmpty()) {
                    cleaned = encodeXmlEntities(cleaned);
                    cleanList.append("\t\t\t<li>").append(cleaned).append("</li>\n");
                }
            }
            cleanList.append("\t\t").append(closeTag);
            String placeholder = "XLISTBLOCK" + listBlocks.size() + "X";
            listMatcher.appendReplacement(withPlaceholders,
                java.util.regex.Matcher.quoteReplacement(placeholder));
            listBlocks.add(cleanList.toString());
        }
        listMatcher.appendTail(withPlaceholders);
        processed = withPlaceholders.toString();

        // Split on <p> tags to get paragraphs
        String[] rawParagraphs = processed.split("(?i)<p[^>]*>");

        // Regex to detect fake bullet/dash list items after tag stripping.
        // Matches bullet chars: · \u00b7, • \u2022, – \u2013, — \u2014, - hyphen
        // followed by any mix of spaces and non-breaking spaces (\u00a0).
        java.util.regex.Pattern fakeBulletPattern = java.util.regex.Pattern.compile(
            "^[\\u00b7\\u2022\\u2013\\u2014\\-][\\s\\u00a0]+");

        // Regex to detect fake numbered list items (e.g. "1.", "2)", "a.", "a)", "i.", "i)")
        java.util.regex.Pattern fakeNumberPattern = java.util.regex.Pattern.compile(
            "^(\\d+|[a-zA-Z]|[ivxlcdmIVXLCDM]+)[.)][\\s\\u00a0]+");

        // First pass: clean paragraphs and classify as bullet, numbered, or normal
        // 0 = normal, 1 = fake bullet, 2 = fake numbered
        java.util.List<String> cleanedParas = new java.util.ArrayList<>();
        java.util.List<Integer> paraType = new java.util.ArrayList<>();
        for (String raw : rawParagraphs) {
            String cleaned = raw.replaceAll("(?i)</p>", "");
            cleaned = cleaned.replaceAll("<(?!/?b>)[^>]*>", "");
            // Decode HTML entities before bullet detection so &#183; becomes ·
            cleaned = decodeHtmlEntities(cleaned);
            // Collapse all whitespace including non-breaking spaces
            cleaned = cleaned.replaceAll("[\\s\\u00a0]+", " ").trim();
            if (!cleaned.isEmpty()) {
                cleanedParas.add(cleaned);
                if (fakeBulletPattern.matcher(cleaned).find()) {
                    paraType.add(1);
                } else if (fakeNumberPattern.matcher(cleaned).find()) {
                    paraType.add(2);
                } else {
                    paraType.add(0);
                }
            }
        }

        // Second pass: group consecutive fake list items into <ul>/<ol> blocks
        StringBuilder xhtml = new StringBuilder();
        int idx = 0;
        while (idx < cleanedParas.size()) {
            String cleaned = cleanedParas.get(idx);
            int type = paraType.get(idx);
            if (type == 1) {
                // Consecutive bullet items -> <ul>
                StringBuilder fakeList = new StringBuilder();
                fakeList.append("<ul>\n");
                while (idx < cleanedParas.size() && paraType.get(idx) == 1) {
                    String item = cleanedParas.get(idx);
                    item = fakeBulletPattern.matcher(item).replaceFirst("");
                    item = item.trim();
                    if (!item.isEmpty()) {
                        item = encodeXmlEntities(item);
                        fakeList.append("\t\t\t<li>").append(item).append("</li>\n");
                    }
                    idx++;
                }
                fakeList.append("\t\t</ul>");
                if (xhtml.length() > 0) xhtml.append("\n\t\t");
                xhtml.append(fakeList);
            } else if (type == 2) {
                // Consecutive numbered items -> <ol>
                // Detect the list type from the first item's marker
                String firstItem = cleanedParas.get(idx);
                java.util.regex.Matcher firstMatch = fakeNumberPattern.matcher(firstItem);
                firstMatch.find();
                String firstMarker = firstMatch.group(1);
                String olType = "1"; // default numeric
                if (firstMarker.matches("[ivxlcdm]+")) {
                    olType = "i";
                } else if (firstMarker.matches("[IVXLCDM]+")) {
                    olType = "I";
                } else if (firstMarker.matches("[a-z]")) {
                    olType = "a";
                } else if (firstMarker.matches("[A-Z]")) {
                    olType = "A";
                }
                StringBuilder fakeList = new StringBuilder();
                if (olType.equals("1")) {
                    fakeList.append("<ol>\n");
                } else {
                    fakeList.append("<ol type=\"").append(olType).append("\">\n");
                }
                while (idx < cleanedParas.size() && paraType.get(idx) == 2) {
                    String item = cleanedParas.get(idx);
                    item = fakeNumberPattern.matcher(item).replaceFirst("");
                    item = item.trim();
                    if (!item.isEmpty()) {
                        item = encodeXmlEntities(item);
                        fakeList.append("\t\t\t<li>").append(item).append("</li>\n");
                    }
                    idx++;
                }
                fakeList.append("\t\t</ol>");
                if (xhtml.length() > 0) xhtml.append("\n\t\t");
                xhtml.append(fakeList);
            } else {
                cleaned = encodeXmlEntities(cleaned);
                // Check if this chunk contains a list placeholder
                java.util.regex.Pattern phPattern = java.util.regex.Pattern.compile("XLISTBLOCK(\\d+)X");
                java.util.regex.Matcher phMatcher = phPattern.matcher(cleaned);
                if (phMatcher.find()) {
                    String before = cleaned.substring(0, phMatcher.start()).trim();
                    if (!before.isEmpty()) {
                        if (xhtml.length() > 0) xhtml.append("\n\t\t");
                        xhtml.append("<p>").append(before).append("</p>");
                    }
                    int blockIdx = Integer.parseInt(phMatcher.group(1));
                    if (xhtml.length() > 0) xhtml.append("\n\t\t");
                    xhtml.append(listBlocks.get(blockIdx));
                    String after = cleaned.substring(phMatcher.end()).trim();
                    if (!after.isEmpty()) {
                        xhtml.append("\n\t\t");
                        xhtml.append("<p>").append(after).append("</p>");
                    }
                } else {
                    if (xhtml.length() > 0) xhtml.append("\n\t\t");
                    xhtml.append("<p>").append(cleaned).append("</p>");
                }
                idx++;
            }
        }

        return xhtml.toString();
    }


    private void openEndorsementsWindow() {
        JDialog endoFrame = new JDialog(this, "Endorsements", true);
        endoFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        endoFrame.setLayout(new BorderLayout(10, 10));
        ((JPanel) endoFrame.getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(endorsementModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(45);   // 5%
        table.getColumnModel().getColumn(1).setPreferredWidth(315);  // 35%
        table.getColumnModel().getColumn(2).setPreferredWidth(540);  // 60%
        // Top-align cell content
        javax.swing.table.DefaultTableCellRenderer topRenderer = new javax.swing.table.DefaultTableCellRenderer();
        topRenderer.setVerticalAlignment(JLabel.TOP);
        for (int col = 0; col < table.getColumnCount(); col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(topRenderer);
        }
        endoFrame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        JButton addBtn = new JButton("Add");
        JButton removeBtn = new JButton("Remove");
        JButton editBtn = new JButton("Edit");

        Dimension btnSize = removeBtn.getPreferredSize();
        addBtn.setPreferredSize(btnSize);
        addBtn.setMaximumSize(btnSize);
        editBtn.setPreferredSize(btnSize);
        editBtn.setMaximumSize(btnSize);
        removeBtn.setMaximumSize(btnSize);

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        editBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addBtn.addActionListener(e -> openEndorsementDialog(endoFrame, -1));
        removeBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) endorsementModel.removeRow(selected);
        });

        editBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                if (table.isEditing()) table.getCellEditor().stopCellEditing();
                openEndorsementDialog(endoFrame, selected);
            }
        });

        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(editBtn);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(removeBtn);

        endoFrame.add(buttonPanel, BorderLayout.EAST);
        endoFrame.setSize(900, 400);
        endoFrame.setLocationRelativeTo(this);
        endoFrame.setVisible(true);
    }

    private void openEndorsementDialog(JDialog parent, int editRow) {
        boolean isEdit = editRow >= 0;
        JDialog dialogFrame = new JDialog(parent, isEdit ? "Edit Endorsement" : "Add Endorsement", true);
        dialogFrame.setLayout(new GridBagLayout());
        ((JPanel) dialogFrame.getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.NORTHWEST;

        // Endorsement Code
        g.gridx = 0; g.gridy = 0;
        dialogFrame.add(new JLabel("Endorsement Code:"), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;
        JTextField codeField = new JTextField(15);
        dialogFrame.add(codeField, g);

        // Endorsement Title
        g.gridx = 0; g.gridy = 1; g.fill = GridBagConstraints.NONE; g.weightx = 0;
        dialogFrame.add(new JLabel("Endorsement Title:"), g);
        g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;
        JTextField titleField = new JTextField(15);
        dialogFrame.add(titleField, g);

        // Endorsement Wording
        g.gridx = 0; g.gridy = 2; g.fill = GridBagConstraints.NONE; g.weightx = 0;
        dialogFrame.add(new JLabel("Endorsement Wording:"), g);

        g.gridx = 1; g.gridy = 2; g.gridheight = 2;
        g.fill = GridBagConstraints.BOTH; g.weightx = 1; g.weighty = 1;
        JTextPane wordingPane = new JTextPane();
        wordingPane.setContentType("text/html");
        wordingPane.setPreferredSize(new Dimension(200, 100));
        dialogFrame.add(new JScrollPane(wordingPane), g);
        g.gridheight = 1;

        // Pre-fill fields when editing
        if (isEdit) {
            codeField.setText((String) endorsementModel.getValueAt(editRow, 0));
            titleField.setText((String) endorsementModel.getValueAt(editRow, 1));
            wordingPane.setText((String) endorsementModel.getValueAt(editRow, 2));
        }

        // OK button
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE; g.weightx = 0; g.weighty = 0;
        g.anchor = GridBagConstraints.CENTER;
        JButton okBtn = new JButton("OK");

        // Shared save logic for OK button and window close
        Runnable saveAction = () -> {
            String code = codeField.getText().trim();
            String title = titleField.getText().trim();
            String wordingText = wordingPane.getDocument().getLength() > 0 ?
                wordingPane.getText().trim() : "";

            if (code.isEmpty() || title.isEmpty() || wordingText.isEmpty()) {
                JOptionPane.showMessageDialog(dialogFrame,
                    "All fields must be filled in.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String wording;
            try {
                javax.swing.text.Document doc = wordingPane.getDocument();
                javax.swing.text.EditorKit kit = wordingPane.getEditorKit();
                java.io.StringWriter sw = new java.io.StringWriter();
                kit.write(sw, doc, 0, doc.getLength());
                wording = sw.toString();
            } catch (Exception ex) {
                wording = wordingPane.getText();
            }
            if (isEdit) {
                endorsementModel.setValueAt(code, editRow, 0);
                endorsementModel.setValueAt(title, editRow, 1);
                endorsementModel.setValueAt(wording, editRow, 2);
            } else {
                endorsementModel.addRow(new Object[]{code, title, wording});
            }
            dialogFrame.dispose();
        };

        okBtn.addActionListener(ev -> saveAction.run());

        dialogFrame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveAction.run();
            }
        });

        dialogFrame.add(okBtn, g);

        dialogFrame.setSize(600, 400);
        dialogFrame.setLocationRelativeTo(this);
        dialogFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EndoBuilder().setVisible(true));
    }
}
