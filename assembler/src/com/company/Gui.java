package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends Frame implements ActionListener{

    private Button    exit_button;     // Declare a Button component
    private JScrollPane myScrollPane = new JScrollPane();


    private JTextArea memMap;








    public Gui(String memory )
    {
     setLayout(new FlowLayout(FlowLayout.CENTER));

        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());

        exit_button = new Button("Exit");        // construct the Button component
        p1.add(exit_button);                     // "super" Frame adds Button
        exit_button.addActionListener(this);



        memMap = new JTextArea(memory);
       // registers = new JList(cpu.getRegisters());
        memMap.setColumns(30);
        memMap.setRows(20);
        myScrollPane.setViewportView(memMap);
        memMap.setEditable(false);


        setTitle("Simulator GUI");  // "super" Frame sets its title
        setSize(550, 500);             // "super" Frame sets its initial window size

        // add subpanels to the primary frame



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
