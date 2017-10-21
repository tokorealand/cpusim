package com.company;
import java.math.BigInteger;

public class Operator {

    Register register;
    Memory mem;
    int[] flags; //pos 0-zero 1-negative 2-carry 3-overflow
    public Operator()
    {

    }

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
            register.setRegisters(Integer.parseInt(rd,2),bitAnd(rn,rm));
        }
        else if(name.equals("ORR"))
        {
            register.setRegisters(Integer.parseInt(rd,2),bitAnd(rn,rm));
        }
    }

    public void executeOperationI(String name, String alu, String rd, String rn)
    {


        if(name.equals("ADDI"))
        {
            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),alu));



        }
        else  if(name.equals("SUBI"))
        {
            System.out.println(alu);
            System.out.println("ALUHERE");
            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(alu)));



        }

        else  if(name.equals("SUBIS"))
        {
            System.out.println(alu);
            System.out.println("ALUHERE");
            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(alu)));


        }

        else  if(name.equals("ADDIS"))
        {
            System.out.println(alu);
            System.out.println("ALUHERE");
            register.setRegisters(Integer.parseInt(rd,2),binaryAddition(register.getRegister(Integer.parseInt(rn,2)),twoComplement(alu)));


        }
    }


    public void executeOperationD(String name, String rt, String rns, String dtAdds) {
        if (name.equals("LDUR")) {
            System.out.println("SAASAS");

            System.out.println(rt);
            System.out.println(rns);
            System.out.println(dtAdds);
            System.out.println(Integer.parseInt(register.getRegister(Integer.parseInt(rns,2)),2));
            System.out.println(Integer.parseInt(dtAdds,2));

            int memLocation= Integer.parseInt(register.getRegister(Integer.parseInt(rns,2)),2) + Integer.parseInt(dtAdds,2);
            System.out.println(memLocation);
            String g=mem.retriveWord(memLocation);
            System.out.println("mem1");
            System.out.println(g);



            register.setRegisters(Integer.parseInt(rt),mem.retriveWord(memLocation));
        }
        else if (name.equals("STUR")) {
            int memLocation= Integer.parseInt(register.getRegister(Integer.parseInt(rns,2)),2) + Integer.parseInt(dtAdds,2);
            String beingStored = register.getRegister(Integer.parseInt(rt,2));
            System.out.println(memLocation);
            System.out.println("pppp");
            System.out.println(beingStored);


            mem.storeWord(memLocation,beingStored);
        }
    }

    public void setComponents(Register reg, int[] flag,Memory mems){
        register=reg;
        flags=flag;
        mem=mems;
    }



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
            System.out.println("twos");
            System.out.println(binString);
          return  binaryAddition(String.valueOf(workingString),"00000000000000000000000000000001");



    }


    //Referenced from https://stackoverflow.com/questions/8548586/adding-binary-numbers/8548599
    private  String binaryAddition(String s1, String s2) {
        System.out.println("S1!");
        System.out.println(s1);
        System.out.println(s2);
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

    private  String padBinary(String binrep)
    {


         if(binrep.length()<32 && binrep.substring(1,2).equals("0"))
        {
            System.out.println("YES");
            int difference = (32-binrep.length());
            String sigFiller = new String(new char[difference]).replace("\0", "0");
            binrep= sigFiller+binrep;
        }

      else  if(binrep.length()<32 && binrep.substring(1,2).equals("1"))
        {
            System.out.println("NO");


            int difference = (32-binrep.length());
            String sigFiller = new String(new char[difference]).replace("\0", "1");
            binrep= sigFiller+binrep;
        }





        return binrep;
    }

    private String logicalLShift(String s1, String numLog)
    {
        s1=padBinary(s1);
        for(int i=1; i<Integer.parseInt(numLog); i++)
        {
            s1=s1.substring(2)+"0";
        }
        return s1;

    }

    private String arithRShift(String s1, String numLog)
    {
        s1=padBinary(s1);
        String arthBit="";
        if(s1.substring(1).equals("0")) arthBit="0";
        else arthBit="1";
        for(int i=1; i<Integer.parseInt(numLog); i++)
        {
            s1=arthBit+s1.substring(1,s1.length()-1);
        }
        return s1;

    }

    private String bitOr(String s1, String s2)
    {
        s1=padBinary(s1);
        s2=padBinary(s2);
        String or="";
        for(int i=0; i<32; i++)
        {

                if(s1.substring(i,i).equals("1") || s2.substring(i,i).equals("1")) or+="1";
                else or+=0;

        }
        return or;
    }

    private String bitEor(String s1, String s2)
    {
        s1=padBinary(s1);
        s2=padBinary(s2);
        String eor="";
        for(int i=0; i<32; i++)
        {

            if(s1.substring(i,i).equals("1") || s2.substring(i,i).equals("1") &&
            !(s1.substring(i,i).equals("1") && s2.substring(i,i).equals("1") )  ) eor+="1";
            else eor+=0;

        }
        return eor;
    }

    private String bitAnd(String s1, String s2)
    {
        s1=padBinary(s1);
        s2=padBinary(s2);
        String and="";
        for(int i=0; i<32; i++)
        {

            if(s1.substring(i,i).equals("1") && s2.substring(i,i).equals("1")) and+="1";
            else and+=0;

        }
        return and;
    }
}
