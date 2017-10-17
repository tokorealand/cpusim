package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Gui extends Frame implements ActionListener{

    // instance variables - replace the example below with your own
    private Label     register_label;  // Declare a Label component
    private TextField register_value;  // Declare a TextField component
    private Label     z_label;  // Declare a Label component
    private Label     n_label;  // Declare a Label component
    private Label     c_label;  // Declare a Label component
    private Label     v_label;  // Declare a Label component
    private Label     sp_label;  // Declare a Label component




    private Button    time_step;       // Declare a Button component
    private Button    exit_button;     // Declare a Button component
   // private JList registers;
    private JScrollPane myScrollPane = new JScrollPane();
    private JScrollPane myScrollPaneReg = new JScrollPane();
    private JScrollPane myScrollPaneFlag = new JScrollPane();

    private JTextArea memMap;
    private JTextArea registers;


    private int time = 0;              // Counter's value
    private  Translator trans;
    private  Cpu cpu;
    private TextField pc;
    private TextField sp;

    private TextField ir;
    private TextField z;
    private TextField n;
    private TextField c;
    private TextField v;



    private TextField instruction;



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





        Panel p4 = new Panel();
        p4.setLayout(new FlowLayout());

        register_label = new Label("Instruction: ");     // construct the Label component
        p4.add(register_label);                 // "super" Frame adds Label

        instruction = new TextField(" ", 20); // construct the TextField component
        instruction.setEditable(false);       // set to read-only
        p4.add(instruction);                  // "super" Frame adds TextField
        instruction.setText(cpu.getCurrentInstruction());
        ir.setText(cpu.getCurrentBinaryInstruction());



        Panel p5 = new Panel();
        p5.setLayout(new FlowLayout());
        z_label = new Label("Z: ");     // construct the Label component
        p5.add(z_label);                 // "super" Frame adds Label

        z = new TextField("0", 1); // construct the TextField component
        z.setEditable(false);       // set to read-only

        p5.add(z);

        Panel p6 = new Panel();
        p6.setLayout(new FlowLayout());
        n_label = new Label("N: ");     // construct the Label component
        p6.add(n_label);                 // "super" Frame adds Label

        n = new TextField("0", 1); // construct the TextField component
        n.setEditable(false);       // set to read-only

        p6.add(n);


        Panel p7 = new Panel();
        p7.setLayout(new FlowLayout());
        c_label = new Label("C: ");     // construct the Label component
        p7.add(c_label);                 // "super" Frame adds Label

        c = new TextField("0", 1); // construct the TextField component
        c.setEditable(false);       // set to read-only

        p7.add(c);

        Panel p8 = new Panel();
        p8.setLayout(new FlowLayout());
        v_label = new Label("V: ");     // construct the Label component
        p8.add(v_label);                 // "super" Frame adds Label

        v = new TextField("0", 1); // construct the TextField component
        v.setEditable(false);       // set to read-only

        p8.add(v);

        Panel p9 = new Panel();
        p9.setLayout(new FlowLayout());

        sp_label = new Label("SP: ");     // construct the Label component
        p9.add(sp_label);                 // "super" Frame adds Label

        sp= new TextField("0", 10); // construct the TextField component
        sp.setEditable(false);       // set to read-only
        sp.setText("0x0");
        p9.add(sp);

        memMap = new JTextArea(trans.retreiveMemImage());
        registers = new JTextArea(cpu.getRegisters());
       // registers = new JList(cpu.getRegisters());
        memMap.setColumns(30);
        memMap.setRows(20);
        myScrollPane.setViewportView(memMap);
        myScrollPaneReg.setViewportView(registers);
        memMap.setEditable(false);
        registers.setEditable(false);

        setTitle("Simulator GUI");  // "super" Frame sets its title
        setSize(550, 500);             // "super" Frame sets its initial window size

        // add subpanels to the primary frame
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);
        add(p6);
        add(p7);
        add(p8);
        add(p9);



        add(myScrollPane);
        add(myScrollPaneReg);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Step") && !cpu.getCurrentInstruction().contains("HALT") ) {
            cpu.getNextInstruction();
            instruction.setText(cpu.getCurrentInstruction());
            ir.setText(cpu.getCurrentBinaryInstruction());
            pc.setText(cpu.getCurrentLocation());
        }

        else if (evt.getActionCommand().equals("Exit")) {

            System.exit(0);  // Terminate the program

        }
        else if(cpu.getCurrentInstruction().contains("HALT"))
        {
            System.out.println();
        }

        }


}
