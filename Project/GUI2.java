package Project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.awt.Color.WHITE;

public class GUI2 implements ActionListener {
    JLabel label;
    JLabel label2;
    JButton button, button2, button3, button4, button5;
    JFileChooser fileChooser;
    File filename;
    String con;

    public GUI2() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(900, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Border border = BorderFactory.createLineBorder(Color.black, 3);

        panel.setLayout(null);

        //OPEN FILE BUTTON
        button = new JButton("Open File");
        button.setFont(new Font("MV Boli", Font.PLAIN, 23));
        button.setBackground(new Color(0xFF26333A, true));
        button.setForeground(Color.WHITE);
        button.setBounds(35, 50, 200, 40);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBorder(border);

        //RESULT LABEL
        label = new JLabel(" RESULT: ");
        label.setBounds(265, 35, 585, 250);
        label.setBackground(new Color(0x1B262C));
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        label.setForeground(WHITE);
        label.setVerticalAlignment(JLabel.TOP);
        label.setOpaque(true);
        label.setBorder(border);
        JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //FILE CONTENT LABEL
        label2 = new JLabel();
        label2.setBounds(265, 300, 585, 50);
        label2.setBackground(new Color(0x1B262C));
        label2.setFont(new Font("MV Boli", Font.PLAIN, 30));
        label2.setForeground(new Color(0xF5E8C7));
        label2.setVerticalAlignment(SwingConstants.TOP);
        label2.setOpaque(true);
        label2.setBorder(border);

        //LEXICAL ANALYSIS BUTTON
        button2 = new JButton("Lexical Analysis");
        button2.setFont(new Font("MV Boli", Font.PLAIN, 20));
        button2.setBackground(new Color(0xFF26333A, true));
        button2.setForeground(Color.WHITE);
        button2.addActionListener(this);
        button2.setFocusable(false);
        button2.setEnabled(false);
        button2.setBounds(35, 110, 200, 40);
        button2.setBorder(border);

        //SYNTAX ANALYSIS BUTTON
        button3 = new JButton("Syntax Analysis");
        button3.setFont(new Font("MV Boli", Font.PLAIN, 20));
        button3.setBackground(new Color(0xFF26333A, true));
        button3.setForeground(Color.WHITE);
        button3.addActionListener(this);
        button3.setFocusable(false);
        button3.setEnabled(false);
        button3.setBounds(35, 170, 200, 40);
        button3.setBorder(border);

        //SEMANTIC ANALYSIS BUTTON
        button4 = new JButton("Semantic Analysis");
        button4.setFont(new Font("MV Boli", Font.PLAIN, 20));
        button4.setBackground(new Color(0xFF26333A, true));
        button4.setForeground(Color.WHITE);
        button4.addActionListener(this);
        button4.setFocusable(false);
        button4.setEnabled(false);
        button4.setBounds(35, 230, 200, 40);
        button4.setBorder(border);

        //CLEAR BUTTON
        button5 = new JButton("Clear");
        button5.setFont(new Font("MV Boli", Font.PLAIN, 23));
        button5.setBackground(new Color(0xFF26333A, true));
        button5.setForeground(Color.WHITE);
        button5.addActionListener(this);
        button5.setFocusable(false);
        button5.setBounds(35, 290, 200, 40);
        button5.setBorder(border);

        panel.setBackground(new Color(0x29394B));

        //ADD THE BUTTONS AND LABEL TO DISPLAY
        frame.add(panel);
        panel.add(button);
        panel.add(label);
        panel.add(label2);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);

        frame.setTitle("TPL Final Project");
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new GUI2();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //OPEN FILE BUTTON
        if (e.getSource() == button) {
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            label.setText(" RESULT:\n");
            label2.setText("");
            fileChooser = new JFileChooser();
            // fileChooser.showOpenDialog(null);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                filename = new File(fileChooser.getSelectedFile().toURI());
                try (FileReader fileReader = new FileReader(filename)) {
                    int character;

                    StringBuilder contentOfFile = new StringBuilder();
                    while ((character = fileReader.read()) != -1) {
                        contentOfFile.append((char) character);
                        System.out.print((char) character);
                    }

                    con = contentOfFile.toString();
                } catch (IOException b) {
                    b.printStackTrace();
                }
            }
            if (con != null) {
                label2.setText("<html>&nbsp;<font size='6'>" + con + "</font><html>");
                button2.setEnabled(true);
            }
        }

        //LEXICAL ANALYSIS BUTTON
        else if (e.getSource() == button2) {
            Lexical lexical;

            lexical = new Lexical(con);
            label.setText("<html>&nbsp;RESULT:\n"
                    + lexical.lexi.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
                            .replaceAll(" ", "&nbsp;")
                    + "</html>");

            button3.setEnabled(true);
            button2.setEnabled(false);
        }

        //SYNTAX ANALYSIS BUTTON
        else if (e.getSource() == button3) {
            Syntax syntax = new Syntax(con);
            System.out.println("s"+syntax.syntax+"s");
            label.setText("<html>&nbsp;RESULT:\n"
                    + syntax.syntax.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
                            .replaceAll(" ", "&nbsp;")
                    + "</html>");
            if(syntax.marker==true){
                button4.setEnabled(true);
            }
            button3.setEnabled(false);
        }

        //SEMANTIC ANALYSIS BUTTON
        else if (e.getSource() == button4) {
            Semantics semantics = new Semantics(con);
            label.setText("<html>&nbsp;RESULT:\n"
                    + semantics.sem.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
                            .replaceAll(" ", "&nbsp;")
                    + "</html>");
            // label.setText(" RESULT:\n" + semantics.sem);
            button4.setEnabled(false);
        }

        //CLEAR BUTTON
        else if (e.getSource() == button5) {
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            label.setText(" RESULT:\n");
            label2.setText("");
        }
    }
}
