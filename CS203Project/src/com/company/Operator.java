package com.company;
import java.math.BigInteger;


/**
 * The operator class is the calculator and execute manager of the project.
 * It carries out all the instructions and has all the required helper methods
 * to carry out each assembly instruction.
 */
public class Operator {

    Register register;
    Memory mem;
    int[] flags; //pos 0-zero 1-negative 2-carry 3-overflow




    public Operator()
    {

    }

    /**
     * This executes instructions of type R such as ADD, SUB, ADD, etc.
     * @param name the name of the instruction
     * @param rd   the destination register
     * @param rm   the second argument register
     * @param shamt number value
     * @param rn   the first argument register
     */

    public void executeOperationR(String name, String rd, String rm, String shamt, String rn)
    {


        if(name.equals("ADD"))
        {

            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),register.getRegister(Integer.parseInt(rm,2))));
        }

        else if(name.equals("SUB"))
        {
            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(register.getRegister(Integer.parseInt(rm,2)))));
        }

        else if(name.equals("AND"))
        {
            register.setRegisters(Integer.parseInt(rd,2),bitAnd(
                    register.getRegister(Integer.parseInt(rn,2)),
                    register.getRegister(Integer.parseInt(rm,2))));
        }
        else if(name.equals("OR"))
        {
            register.setRegisters(Integer.parseInt(rd,2),bitOr(rn,rm));
        }
        else if(name.equals("EOR"))
        {
            register.setRegisters(Integer.parseInt(rd,2),bitEor(rn,rm));
        }
        else if(name.equals("LSL"))
        {
            register.setRegisters(Integer.parseInt(rd,2),
                    logicalLShift(register.getRegister(Integer.parseInt(rn,2)),shamt));
        }
        else if(name.equals("LSR"))
        {
            register.setRegisters(Integer.parseInt(rd,2),
                    arithRShift(register.getRegister(Integer.parseInt(rn,2)),shamt));
        }
    }



    /**
     * This executes instructions of type I such as ADDI, SUBI, etc.
     * @param name the name of the instruction
     * @param rd   the destination register
     * @param alu  the immediate value
     * @param rn   the first argument register
     */
    public void executeOperationI(String name, String alu, String rd, String rn)
    {


        if(name.equals("ADDI"))
        {
            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),alu));



        }
        else  if(name.equals("SUBI"))
        {

            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(alu)));



        }

        else  if(name.equals("SUBIS"))
        {

            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(alu)));


        }

        else  if(name.equals("ADDIS"))
        {

            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(alu)));


        }
    }



    /**
     * This executes instructions of type D such as LDUR and STUR.
     * @param name    the name of the instruction
     * @param rt      the destination register or the base register
     * @param dtAdds  the memory immediate
     * @param rns     the base register or destination register
     */
    public void executeOperationD(String name, String rt, String rns, String dtAdds) {
        if (name.equals("LDUR")) {


            int memLocation= Integer.parseInt(register.getRegister(Integer.parseInt(rns,2)),2) + Integer.parseInt(dtAdds,2);
       //     String g=mem.retriveWord(memLocation);




            register.setRegisters(Integer.parseInt(rt),mem.retriveWord(memLocation));
        }
        else if (name.equals("STUR")) {
            int memLocation= Integer.parseInt(register.getRegister(Integer.parseInt(rns,2)),2) + Integer.parseInt(dtAdds,2);
            String beingStored = register.getRegister(Integer.parseInt(rt,2));



            mem.storeWord(memLocation,beingStored);
        }
    }


    /**
     * This executes instructions of type B such as B and BL.
     * @param name    the name of the instruction
     * @param addr    the address to jump to
     */
    public void executeOperationB(String name, String addr)
    {
        if(name.equals("B"))
        {
            mem.setCurrentIndex(Integer.parseInt(addr,2));
        }

       else if(name.equals("BL"))
        {
            mem.setLinkPointer("0x"+Integer.toHexString(mem.getCurrentIndex()));
            mem.setCurrentIndex(Integer.parseInt(addr,2));
        }

    }


    /**
     * This executes instructions of type B such as CBZ and CBNZ.
     * @param name    the name of the instruction
     * @param addr    the address to jump to
     * @param rt      the register that is being checked for conditional
     */
    public void executeOperationCB(String name, String addr, String rt)
    {
        if(name.equals("CBZ"))
        {
            if(Integer.parseInt(register.getRegister(
                    Integer.parseInt(rt,2)),2) == 0)
            {
                mem.setCurrentIndex(Integer.parseInt(addr,2));
            }

        }

        else if(name.equals("CBNZ"))
        {
            if(Integer.parseInt(register.getRegister(
                    Integer.parseInt(rt,2)),2) != 0)
            {
                mem.setCurrentIndex(Integer.parseInt(addr,2));
            }
        }

    }


    /**
     * This executes instructions of type CB such as CBZ and CBNZ.
     * @param name    the name of the instruction
     * @param rn      the register that is being pushed to stack or is being set with the pop value.
     */
    public void executeOperationS(String name, String rn){
        if(name.equals("PUSH"))
        {
            mem.pushStack(register.getRegister(Integer.parseInt(rn,2)));
        }
        else if(name.equals("POP"))
        {
            String p=mem.popStack();

            register.setRegisters(Integer.parseInt(rn,2),p);
        }
    }


    /**
     * Sets the required components from outside the class(pass through).
     * @param reg  registers
     * @param flag flags
     * @param mems memory
     */
    public void setComponents(Register reg, int[] flag,Memory mems){
        register=reg;
        flags=flag;
        mem=mems;
    }


    /**
     * Gives the two's complement of a number
     * @param binString the binary string
     * @return the binary strings two's complement
     */
    private String twoComplement(String binString)
    {
        char[] workingString = binString.toCharArray();
        for(int i=0; i<workingString.length; i++) {
            if (workingString[i] == ('1')) {
                workingString[i] = '0';
            } else if (workingString[i] == ('0')) {
                workingString[i] = '1';
            }
        }

          return  binaryAddition(String.valueOf(workingString),"00000000000000000000000000000001");



    }


    //Referenced from https://stackoverflow.com/questions/8548586/adding-binary-numbers/8548599
    /**
     * This adds to binary strings.
     * @param s1    the first string argument
     * @param s2    the second string argument
     */
    private  String binaryAddition(String s1, String s2) {

        if (s1 == null || s2 == null) return "";
        int first = s1.length() - 1;
        int second = s2.length() - 1;
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        while (first >= 0 || second >= 0) {
            int sum = carry;
            if (first >= 0) {
                sum += s1.charAt(first) - '0';
                first--;
            }
            if (second >= 0) {
                sum += s2.charAt(second) - '0';
                second--;
            }
            carry = sum >> 1;
            sum = sum & 1;
            sb.append(sum == 0 ? '0' : '1');
        }

        if (carry > 0)
            sb.append('1');

        sb.reverse();

        int flagChecker = new BigInteger(String.valueOf(sb),2).intValue();

        if (flagChecker==0) flags[0]=1;
        if (flagChecker<0) flags[1]=1;
        if (carry > 0) flags[2]=1;
        if(String.valueOf(sb).length()>32) flags[3]=1;



        return padBinary(String.valueOf(sb));
    }

    /**
     * Pads the binary string to 32 bits.
     * @param binrep the binary string
     * @return the padded binary string
     */
    private  String padBinary(String binrep)
    {


         if(binrep.length()<32 && binrep.substring(1,2).equals("0"))
        {
            int difference = (32-binrep.length());
            String sigFiller = new String(new char[difference]).replace("\0", "0");
            binrep= sigFiller+binrep;
        }

      else  if(binrep.length()<32 && binrep.substring(1,2).equals("1"))
        {


            int difference = (32-binrep.length());
            String sigFiller = new String(new char[difference]).replace("\0", "1");
            binrep= sigFiller+binrep;
        }





        return binrep;
    }


    /**
     * Logical left shift the inputted string numLog times.
     * @param s1 the string argument
     * @param numLog the number of shifts
     * @return the string left shifted numLog times
     */
    private String logicalLShift(String s1, String numLog)
    {

        s1=padBinary(s1);
        for(int i=0; i<Integer.parseInt(numLog); i++)
        {
            s1=s1.substring(2)+"0";
        }
        return s1;

    }

    /**
     * Arithmetical right shift the inputted string numLog times.
     * @param s1 the string argument
     * @param numLog the number of shifts
     * @return the string right shifted numLog times
     */
    private String arithRShift(String s1, String numLog)
    {
        s1=padBinary(s1);
        String arthBit="";
        if(s1.substring(1,2).equals("0")) arthBit="0";
        else arthBit="1";
        for(int i=0; i<Integer.parseInt(numLog); i++)
        {
            s1=arthBit+s1.substring(1,s1.length()-1);
        }
        return s1;

    }

    /**
     * Finds the bitOr value
     * @param s1    the first string argument
     * @param s2    the second string argument
     * @return the bitOR value
     */
    private String bitOr(String s1, String s2)
    {
        s1=padBinary(s1);
        s2=padBinary(s2);
        String or="";
        for(int i=0; i<32; i++)
        {

                if(s1.substring(i,i+1).equals("1") || s2.substring(i,i+1).equals("1")) or+="1";
                else or+=0;

        }

        return or;
    }

    /**
     * Finds the bitEor value
     * @param s1    the first string argument
     * @param s2    the second string argument
     * @return the bitEor value
     */
    private String bitEor(String s1, String s2)
    {
        s1=padBinary(s1);
        s2=padBinary(s2);
        String eor="";
        for(int i=0; i<32; i++)
        {

            if(s1.substring(i,i+1).equals("1") || s2.substring(i,i+1).equals("1") &&
            !(s1.substring(i,i+1).equals("1") && s2.substring(i,i+1).equals("1") )  ) eor+="1";
            else eor+=0;

        }
        return eor;
    }


    /**
     * Finds the bitAnd value
     * @param s1    the first string argument
     * @param s2    the second string argument
     * @return the bitAnd value
     */
    private String bitAnd(String s1, String s2)
    {
        s1=padBinary(s1);
        s2=padBinary(s2);
        String and="";
        for(int i=1; i<32; i++)
        {
                if (s1.substring(i, i+1).equals("1") && s2.substring(i, i+1).equals("1")) and += "1";
                else and+="0";




        }

        return and;
    }
}
