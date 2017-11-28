compile:
	javac Simulator/src/*.java
	javac Assembler/src/*.java
	javac Viewer/src/*.java

run:
	java -cp Assembler/src Main ./Testfiles/ test1.as
	java -cp Viewer/src Main ./Testfiles/ test1.o	true 5 20
	java -cp Simulator/src Main ./Testfiles/ test1.o false false 


	java -cp Assembler/src Main ./Testfiles/ test2.as
	java -cp Viewer/src Main ./Testfiles/ test2.o	true 5 20
	java -cp Simulator/src Main ./Testfiles/ test2.o true true


	java -cp Assembler/src Main ./Testfiles/ test3.as
	java -cp Viewer/src Main ./Testfiles/ test3.o	false 5 20
	java -cp Simulator/src Main ./Testfiles/ test3.o true false

	
	


	
