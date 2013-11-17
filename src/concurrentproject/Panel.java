package concurrentproject;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Panel extends JPanel {

    private JLabel label;
    private JTextArea textArea;
    private JScrollPane scroll;

    public Panel(String description) {
        this.setLayout(new BorderLayout());

        label = new JLabel(description);
        textArea = new JTextArea();

        textArea.setEditable(false);
        textArea.setAutoscrolls(true);

        textArea.setRows(10);
        textArea.setColumns(1);

        textArea.setWrapStyleWord(true);

        scroll = new JScrollPane(textArea);
        textArea.setBackground(Color.WHITE);

        add(label, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void addText(String text) {
        textArea.append(text);
    }
}
