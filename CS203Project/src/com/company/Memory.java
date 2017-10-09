package com.company;

public class Memory {

  String[] memMap;
  int currentIndex=0;
  int usedMem=0;

        public Memory(int maxSize)
        {
            memMap = new String[maxSize];
            for (int i=0; i<memMap.length; i++)
            {
                memMap[i]="0";
            }
        }

         void addToMap(String memoryLine){
             int byteSize = memoryLine.length()/8;
                if(byteSize+usedMem<=memMap.length) {
                    memMap[currentIndex] = memoryLine;
                    for (int i=1; i<byteSize; i++)
                    {
                        memMap[currentIndex+i]="-";
                    }

                        currentIndex+=byteSize;
                        usedMem += byteSize;
                    return;
                }
                System.out.println("MAX MEM EXCEEDED");

        }

        void printMap()
        {
            for (int i=0; i<memMap.length; i++)
            {
                if(i%10==0)
                {
                    System.out.println(" ");
                    System.out.println("--------------------------------------------------------------------------------------------");
                    System.out.print("|");
                }
                if(!(memMap[i].equals("-"))) {
                    System.out.print(memMap[i]);
                    System.out.print("|");
                }

            }
            System.out.println(" ");
            System.out.println("--------------------------------------------------------------------------------------------");

        }


}
