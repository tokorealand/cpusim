
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.Timer;


/**
 * The gui class gives a frame for the simulator to be visualized.
 */
public class Gui extends Frame implements ActionListener{



    private Label     register_label;
    private Label     step_label;

    private TextField register_value;
    private Label     z_label;
    private Label     n_label;
    private Label     c_label;
    private Label     v_label;
    private Label     sp_label;
    private Label     lr_label;

    private Timer speedTimer;



    private Button    time_step;
    private Button    fast;
    private Button    slow;
    private Button    exit_button;
    private Button    reset;
    private Button    save;


    private JScrollPane myScrollPane = new JScrollPane();
    private JScrollPane myScrollPaneReg = new JScrollPane();

    private JTextArea memMap;
    private JTextArea registers;


    private int stepC = 0;              // Counter's value
    private TextField step;             // Counter

    private  Translator trans;          // Main Translator
    private  Cpu cpu;                   // Cpu


    //Memory Addresses
    private TextField pc;
    private TextField lr;
    private TextField sp;


    // Flags
    private TextField z;
    private TextField n;
    private TextField c;
    private TextField v;


    // Instructions
    private TextField instruction;
    private TextField ir;




    public Gui(Translator aTrans )
    {
     setLayout(new FlowLayout(FlowLayout.CENTER));
     setBackground(Color.red);

     trans = aTrans;
     cpu=trans.sendCpu();                           // Grabs required cpu for simulation



        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout());

        time_step = new Button("Step");
        p1.add(time_step);
        time_step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                            stepThrough(e);
                                        }
                                    });


        slow = new Button("Slow");
        p1.add(slow);
        slow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slow(e);
            }
        });


        fast = new Button("Fast");
        p1.add(fast);
        fast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fast(e);
            }
        });


        reset = new Button("Reset");
        p1.add(reset);
        reset.addActionListener(this);

        save = new Button("Save");
        p1.add(save);
        save.addActionListener(this);
        exit_button = new Button("Exit");
        p1.add(exit_button);
        exit_button.addActionListener(this);

      //-------------------------------------------------------------------------



        Panel p2 = new Panel();
        p2.setLayout(new FlowLayout());

        step_label = new Label("Step:");
        register_label = new Label("PC: ");
        p2.add(step_label);
        step= new TextField("0", 3);
        step.setEditable(false);
        p2.add(step);
        p2.add(register_label);
        pc= new TextField("0", 10);
        pc.setEditable(false);
        p2.add(pc);


        //-------------------------------------------------------------------------


        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout());

        register_label = new Label("IR: ");
        p3.add(register_label);

        ir = new TextField("", 32);
        ir.setEditable(false);

        p3.add(ir);



        //-------------------------------------------------------------------------

        Panel p4 = new Panel();
        p4.setLayout(new FlowLayout());

        register_label = new Label("Instruction: ");
        p4.add(register_label);

        instruction = new TextField(" ", 20);
        instruction.setEditable(false);
        p4.add(instruction);



        //-------------------------------------------------------------------------



        Panel p5 = new Panel();
        p5.setLayout(new FlowLayout());
        z_label = new Label("Z: ");
        p5.add(z_label);

        z = new TextField("0", 1);
        z.setEditable(false);

        p5.add(z);


        //-------------------------------------------------------------------------

        Panel p6 = new Panel();
        p6.setLayout(new FlowLayout());
        n_label = new Label("N: ");
        p6.add(n_label);

        n = new TextField("0", 1);
        n.setEditable(false);

        p6.add(n);


        //-------------------------------------------------------------------------


        Panel p7 = new Panel();
        p7.setLayout(new FlowLayout());
        c_label = new Label("C: ");
        p7.add(c_label);

        c = new TextField("0", 1);
        c.setEditable(false);

        p7.add(c);



        //-------------------------------------------------------------------------

        Panel p8 = new Panel();
        p8.setLayout(new FlowLayout());
        v_label = new Label("V: ");
        p8.add(v_label);

        v = new TextField("0", 1);
        v.setEditable(false);

        p8.add(v);



        //-------------------------------------------------------------------------

        Panel p9 = new Panel();
        p9.setLayout(new FlowLayout());

        sp_label = new Label("SP: ");
        p9.add(sp_label);

        sp= new TextField("0", 10);
        sp.setEditable(false);
        sp.setText( cpu.getStack());
        p9.add(sp);


        lr_label = new Label("LR: ");
        p9.add(lr_label);
        lr=new TextField("0",10);
        lr.setEditable(false);
        lr.setText(cpu.getLinkRegister());
        p9.add(lr);

        //-------------------------------------------------------------------------



        //Set up memory image area
        memMap = new JTextArea(trans.retreiveMemImage());
        memMap.setColumns(33);
        memMap.setRows(20);
        memMap.setEditable(false);
        myScrollPane.setViewportView(memMap);

        //Set up register area
        registers = new JTextArea(cpu.getRegisters());
        myScrollPaneReg.setViewportView(registers);
        registers.setColumns(6);
        registers.setEditable(false);

        setTitle("Simulator GUI");  //Sets title
        setSize(550, 600); //Sets size

        // add subpanels and panes
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);
        add(p6);
        add(p7);
        add(p8);
        add(p9);
        add(myScrollPaneReg);
        add(myScrollPane);

        setVisible(true);
    }

    /**
     * Steps through the program using fetch and execute at a slow(2000ms delay) speed.
     * @param e button clicked
     */
    private void slow(ActionEvent e)
    {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            stepThrough(e);

            }




    };
        speedTimer = new Timer(2000, al);
        if(cpu.getCurrentInstruction().contains("HALT") ) speedTimer.stop();

            speedTimer.start();
    }


    /**
     * Steps through the program using fetch and execute at a fast(500ms delay) speed.
     * @param e button clicked
     */
    private void fast(ActionEvent e)
    {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stepThrough(e);

            }




        };
        speedTimer = new Timer(500, al);

        speedTimer.start();
    }


    /**
     * Steps through the program once and update panels and panes
     * @param e button clicked
     */
    private void stepThrough(ActionEvent e)
    {
        if(cpu.getCurrentInstruction().contains("HALT") ) return;

            cpu.fetchAndExecute();
        instruction.setText(cpu.getCurrentInstruction());
        ir.setText(cpu.getCurrentBinaryInstruction());
        pc.setText(cpu.getCurrentLocation());
        sp.setText(cpu.getStack());
        lr.setText(cpu.getLinkRegister());
        stepC++;
        step.setText(String.valueOf(stepC));
        registers = new JTextArea(cpu.getRegisters());
        memMap = new JTextArea(trans.retreiveMemImage());
        z.setText(cpu.getRequestedFlag(0));
        n.setText(cpu.getRequestedFlag(1));
        c.setText(cpu.getRequestedFlag(2));
        v.setText(cpu.getRequestedFlag(3));
        myScrollPaneReg.setViewportView(registers);
        myScrollPane.setViewportView(memMap);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Step") && !cpu.getCurrentInstruction().contains("HALT")) {
            cpu.fetchAndExecute();
            instruction.setText(cpu.getCurrentInstruction());
            ir.setText(cpu.getCurrentBinaryInstruction());
            pc.setText(cpu.getCurrentLocation());
            sp.setText(cpu.getStack());
            lr.setText(cpu.getLinkRegister());
            stepC++;
            step.setText(String.valueOf(stepC));
            registers = new JTextArea(cpu.getRegisters());
            memMap = new JTextArea(trans.retreiveMemImage());
            z.setText(cpu.getRequestedFlag(0));
            n.setText(cpu.getRequestedFlag(1));
            c.setText(cpu.getRequestedFlag(2));
            v.setText(cpu.getRequestedFlag(3));
            myScrollPaneReg.setViewportView(registers);
            myScrollPane.setViewportView(memMap);


        } else if (evt.getActionCommand().equals("Exit")) {

            System.exit(0);  // Terminate the program

        }
        else if (evt.getActionCommand().equals("Reset"))
        {
            trans.reset();
            memMap = new JTextArea(trans.retreiveMemImage());
            myScrollPane.setViewportView(memMap);
            cpu.reset();
            cpu.setCurrentByte(0);
            speedTimer.stop();
            instruction.setText("");
            pc.setText("");
            stepC=0;


        }
        else if (evt.getActionCommand().equals("Save"))
        {
            try {
                trans.save();

            }
            catch (IOException e)
            {
                System.out.println("Saving Failed");
            }
        }



    }







}
