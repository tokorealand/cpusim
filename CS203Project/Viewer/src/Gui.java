

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The gui class gives a frame for the simulator to be visualized.
 */
public class Gui extends Frame implements ActionListener{

    private Button    exit_button;
    private JScrollPane myScrollPane = new JScrollPane();
    private JTextArea memMap;








    public Gui(String memory )
    {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.red);

        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());

        exit_button = new Button("Exit");
        p1.add(exit_button);
        exit_button.addActionListener(this);



        memMap = new JTextArea(memory);
        memMap.setColumns(15);
        memMap.setRows(10);
        myScrollPane.setViewportView(memMap);
        memMap.setEditable(false);
        memMap.setFont(memMap.getFont().deriveFont(30f));



        setTitle("Simulator GUI");
        setSize(550, 500);




        add(p1);

        add(myScrollPane);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Exit")) {

            System.exit(0);  // Terminate the program

        }


    }


}
