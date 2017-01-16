CC = javac
OBJ = EulerCircuitTester.java EulerGraph.java Graph.java
EXE = EulerCircuitTester

$(EXE) : 
		@$(CC) $(OBJ)

clean : 
		@rm -rf *.class

rebuild : clean $(EXE)

run : rebuild
	  @java $(EXE)