import java.text.DecimalFormat;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Endo2Prescribe implements ActionListener
{
    private JPanel panel;
    private JFrame frame;

    private JScrollPane textInputScroll;
    private JScrollPane textOutputScroll;
    private JTextArea textInput;
    private JTextArea textOutput;
    
    private JTextArea yHeightChange;
    private JLabel yHeightLabel;

    private JTextArea inputStringLength;
    private JLabel stringLengthLabel;

    private int stringLength = 100;

    private double yChange = 0.15;
    private double xChange = 0.00;

    private String outputString;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Endo2Prescribe()
    {
        frame = new JFrame();

        // Creating Generate Button

        JButton button = new JButton("Generate Text");
        button.addActionListener(this);

        // Creating Character Limiting Input

        stringLengthLabel = new JLabel();
        stringLengthLabel.setText("Character Length: ");
        inputStringLength = new JTextArea();
        inputStringLength.setText(Integer.toString(stringLength));

        // Creating y change input

        yHeightLabel = new JLabel();
        yHeightLabel.setText("Line Spacing: ");
        yHeightChange = new JTextArea();
        yHeightChange.setText(Double.toString(yChange));

        // Creating Text Input

        textInput = new JTextArea();
        textInput.setLineWrap(true);

        textInputScroll = new JScrollPane(textInput);
        textInputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Creating Text Output

        textOutput = new JTextArea();
        textOutput.setEditable(false);
        textOutput.setLineWrap(true);

        textOutputScroll = new JScrollPane(textOutput);
        textOutputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Creating Window

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // String Length Label (r1, c1)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(stringLengthLabel, gbc);

        // String Length Text (r1, c2)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(inputStringLength, gbc);

        // Button (r1, c3)
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(button, gbc);

        // String yHeight Label (r2, c1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(yHeightLabel, gbc);

        // String yHeight input (r2, c2)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(yHeightChange, gbc);

        // Input Text area (r3)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        panel.add(textInputScroll, gbc);

        // Output conveted text (r4)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        panel.add(textOutputScroll, gbc);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Endo2Prescribe");
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Endo2Prescribe();
    }

    public void actionPerformed(ActionEvent e)
    {
        setVariables();
        String amendedString = cutString();

        amendedString = amendedString.replaceAll(" quis ", " \",E; FONT 56; TEXT\"quis\",E; FONT 13 TEXT\" ");
        textOutput.setText(amendedString);
        
    }

    public void setVariables()
    {

        // Create Try Catch statements for these conversions

        stringLength = Integer.parseInt(inputStringLength.getText());
        yChange = Double.parseDouble(yHeightChange.getText());
    }

    public String cutString()
    {
        Boolean firstTime = true;
        outputString = textInput.getText();
        String amendedString = "";

        int i = 0;
        String chunk;
        while (i < outputString.length())
        {
            int lineEnd = Math.min(i + stringLength, outputString.length());

            // Checks for end of string or in the middle of a word
            if (lineEnd < outputString.length() && outputString.charAt(lineEnd) != ' ')
            {
                int lastSpace = outputString.lastIndexOf(' ', lineEnd);
                
                // Only move back if there's a space after i
                if (lastSpace > i)
                {
                    lineEnd = lastSpace;
                }
            }

            chunk = outputString.substring(i, lineEnd);

            if (firstTime)
            {
                amendedString = "TEXT\"" + chunk + "\"; MRP " + df.format(xChange) + ", "+ df.format(yChange) + ";";
                firstTime = false;
            } else
            {
                amendedString = amendedString + "\n" + "TEXT\"" + chunk + "\"; MRP " + df.format(xChange) + ", "+ df.format(yChange) + ";";
            }
            // increment i to skip the space
            i = lineEnd + 1;
        }
        return amendedString;
    }
}