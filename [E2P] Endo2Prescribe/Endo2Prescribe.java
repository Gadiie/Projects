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
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Endo2Prescribe implements ActionListener
{
    private JPanel panel;
    
    // Creating Panels for input elements
    private JPanel boldPanel;
    private JPanel clPanel;
    private JPanel lsPanel;
    private JPanel wbPanel;
    private JFrame frame;

    // Creating Panels for wording input and output
    private JScrollPane textInputScroll;
    private JScrollPane textOutputScroll;
    private JTextArea textInput;
    private JTextArea textOutput;
    
    // Creating items for Line Spacing
    private JTextField lsValue;
    private JLabel lsLabel;

    // Creating items for String Length
    private JTextField slValue;
    private JLabel slLabel;

    // Creating items for Word Bolding
    private JTextField wbList;
    private JLabel wbLabel;

    // Creating items for font variations
    private JRadioButton rbFONT;
    private JRadioButton rbFSET;
    
    private JTextField upperValue;
    private JLabel upperLabel;

    private JTextField lowerValue;
    private JLabel lowerLabel;
    
    // Creating calculating variables
    private int stringLength = 100;
    private double yChange = 0.12;
    private double xChange = 0.00;

    // Creating String Variables
    private String outputString;
    private String amendedString;

    private String paramType;
    private String paramUpper;
    private String paramLower;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public Endo2Prescribe()
    {
        frame = new JFrame();

        // Creating Generate Button

        JButton button = new JButton("Generate Text");
        button.addActionListener(this);

        // Creating Character Length Input [ clPanel ]

        slLabel = new JLabel();
        slLabel.setText("Character Length: ");
        slValue = new JTextField();
        slValue.setColumns(5);
        slValue.setText(Integer.toString(stringLength));

        clPanel = new JPanel();
        clPanel.add(slLabel);
        clPanel.add(slValue);

        // Creating line spacing input [ lsPanel ]

        lsLabel = new JLabel();
        lsLabel.setText("Line Spacing: ");
        lsValue = new JTextField();
        lsValue.setColumns(5);
        lsValue.setText(Double.toString(yChange));

        lsPanel = new JPanel();
        lsPanel.add(lsLabel);
        lsPanel.add(lsValue);

        // Creating Bolding Options [ boldPanel ]

            // Creating radio buttons 

        rbFONT = new JRadioButton("FONT");
        rbFSET = new JRadioButton("FSET");

        ButtonGroup bgBold = new ButtonGroup();
        bgBold.add(rbFONT);
        bgBold.add(rbFSET);

        rbFONT.setSelected(true);

        boldPanel = new JPanel();
        boldPanel.add(rbFONT);
        boldPanel.add(rbFSET);

                // Getting bolding output
        upperLabel = new JLabel();
        upperLabel.setText("Bold Number:");
        upperValue = new JTextField();
        upperValue.setColumns(5);
        upperValue.setText("56");

        boldPanel.add(upperLabel);
        boldPanel.add(upperValue);

                // Getting no bolding output
        lowerLabel = new JLabel();
        lowerLabel.setText("Non-Bold Number:");
        lowerValue = new JTextField();
        lowerValue.setColumns(5);
        lowerValue.setText("13");

        boldPanel.add(lowerLabel);
        boldPanel.add(lowerValue);

        // Getting Words to Bold [ wbPanel ]

        wbLabel = new JLabel();
        wbLabel.setText("Words to bold (comma separated):");
        wbList = new JTextField();
        wbList.setColumns(30);
        wbList.setText("");

        wbPanel = new JPanel();
        wbPanel.add(wbLabel);
        wbPanel.add(wbList);

        
        // PROGRAM OUTPUTS

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

        // String Length Label ( r1, c1 )
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(clPanel, gbc);

        // String Line Spacing ( r1, c2 )
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(lsPanel, gbc);

        // String Bold Radio Buttons (r2, c1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panel.add(boldPanel, gbc);

        // String Bold Label (r3, c1)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        panel.add(wbPanel, gbc);

        // Button (r3, c4)
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        panel.add(button, gbc);

        // Input Text area (r4)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.weighty = 1;
        panel.add(textInputScroll, gbc);

        // Output conveted text (r5)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
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
        amendedString = cutString();
        boldString();

        textOutput.setText(amendedString);
    }

    public void setVariables()
    {
        if(rbFONT.isSelected())
            paramType = "FONT";
        else
            paramType = "FSET";

        paramUpper = paramType + " " + upperValue.getText();
        paramLower = paramType + " " + lowerValue.getText();

        try
        {
            stringLength = Integer.parseInt(slValue.getText());
        } catch (NumberFormatException e)
        {
            stringLength = 100;
        }
        
        try
        {
            yChange = Double.parseDouble(lsValue.getText());
        } catch (NumberFormatException e)
        {
            yChange = 0.12;
        }
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

    public void boldString()
    {
        String wordList = wbList.getText();
        String[] toBold = wordList.split(",");

        for(int i=0;i<toBold.length;i++)
        {
            String boldWord = toBold[i];
            String toReplace = "(?<![a-zA-Z0-9])"+ boldWord +"(?![a-zA-Z0-9])";

            amendedString = amendedString.replaceAll(toReplace, "\",E; "+ paramUpper + "; TEXT\"" + boldWord + "\",E; "+ paramLower + "; TEXT\"");
        }

        // Clean up output by removing starting and ending possibilities

        amendedString = amendedString.replaceAll("\",E; " + paramLower + "; TEXT\"\";", "\",E; " + paramLower);

        amendedString = amendedString.replaceAll("TEXT\"\",E; " + paramUpper + "; TEXT\"", paramUpper + "; TEXT\"");
    }
}