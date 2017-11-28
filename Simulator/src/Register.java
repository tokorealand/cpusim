

/**
 * Registers are the non memory storage medium that the Cpu has available for working.
 */
public class Register {
    String[] registers; //array containing the values of the registers pos 0 = register X0...


    /**
     * Initializes all registers to full 32 bit binary zero.
     * @param numberRegister how many registers are being created
     */
    public Register(int numberRegister)
    {
        registers=new String[numberRegister];
        for(int i=0; i<registers.length; i++)
        {
            registers[i]="00000000000000000000000000000000";
        }

    }

    public  void print()
    {
        for (String reg:registers) {
        System.out.println(reg);
        }
    }

    /**
     *
     * @param registerNumber  the index of register
     * @return requested register
     */
    public String getRegister(int registerNumber) {


        return registers[registerNumber];

    }

    /**
     *
     * @param registerNumber requested register to be changed
     * @param value to be inserted into register
     */
    public void setRegisters(int registerNumber, String value)
    {
        if(Translator.noisy.equals("true"))
        {
            System.out.println("X"+registerNumber+" Being changed to " + value );
        }
        registers[registerNumber] = value;
    }

    /**
     *
     * @return all the registers.
     */
    public String[] getRegisters() {
        return registers;
    }
}
