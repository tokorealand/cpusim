package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Gui extends Frame implements ActionListener{

    // instance variables - replace the example below with your own
    private Label     register_label;  // Declare a Label component
    private TextField register_value;  // Declare a TextField component
    private Button    time_step;       // Declare a Button component
    private Button    exit_button;     // Declare a Button component
    private JList mem = new JList();
    private JScrollPane myScrollPane = new JScrollPane();
    private JTextArea memMap;


    private int time = 0;              // Counter's value
    private  Translator trans;
    private  Cpu cpu;
    private TextField pc;
    private TextField ir;
    private TextField ma;
    private TextField md;
    private TextField c;
    private TextField a;
    private TextField instruction;
    private ArrayList<TextField> registers;
    private TextField o;
    private TextField s;
    private TextField z;
    private TextField q;


    public Gui(Translator aTrans )
    {
     setLayout(new FlowLayout(FlowLayout.CENTER));
     trans = aTrans;
     cpu=trans.sendCpu();
        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());

        time_step = new Button("Step");          // construct the Button component
        p1.add(time_step);                       // "super" Frame adds Button
        time_step.addActionListener(this);

        exit_button = new Button("Exit");        // construct the Button component
        p1.add(exit_button);                     // "super" Frame adds Button
        exit_button.addActionListener(this);

        Panel p2 = new Panel();
        p2.setLayout(new FlowLayout());

        register_label = new Label("PC: ");     // construct the Label component
        p2.add(register_label);                 // "super" Frame adds Label

        pc= new TextField("0", 10); // construct the TextField component
        pc.setEditable(false);       // set to read-only
        pc.setText(cpu.getCurrentLocation());
        p2.add(pc);                  // "super" Frame adds TextField

        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout());

        register_label = new Label("IR: ");     // construct the Label component
        p3.add(register_label);                 // "super" Frame adds Label

        ir = new TextField("0", 32); // construct the TextField component
        ir.setEditable(false);       // set to read-only

        p3.add(ir);                  // "super" Frame adds TextField





        Panel p8 = new Panel();
        p8.setLayout(new FlowLayout());

        register_label = new Label("Instruction: ");     // construct the Label component
        p8.add(register_label);                 // "super" Frame adds Label

        instruction = new TextField(" ", 20); // construct the TextField component
        instruction.setEditable(false);       // set to read-only
        p8.add(instruction);                  // "super" Frame adds TextField
        instruction.setText(cpu.getCurrentInstruction());
        ir.setText(cpu.getCurrentBinaryInstruction());



        memMap = new JTextArea(trans.retreiveMemImage());
        memMap.setColumns(50);
        memMap.setRows(30);
        myScrollPane.setViewportView(memMap);
        memMap.setEditable(false);

        setTitle("Simulator GUI");  // "super" Frame sets its title
        setSize(550, 500);             // "super" Frame sets its initial window size

        // add subpanels to the primary frame
        add(p8);
        add(p1);
        add(p2);
        add(p3);


        add(myScrollPane);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Step") ) {
            cpu.getNextInstruction();
            instruction.setText(cpu.getCurrentInstruction());
            ir.setText(cpu.getCurrentBinaryInstruction());
            pc.setText(cpu.getCurrentLocation());
        }

        }


}
