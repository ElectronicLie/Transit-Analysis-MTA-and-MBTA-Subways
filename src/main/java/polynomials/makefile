run: compile
	java Tester.java

compile: *.java ../malo/*.java ../fractions/*.java
	javac ../malo/*.java
	javac ../fractions/*.java
	javac *.java

polynomial: Tester.java Polynomial.java ../malo/*.java ../fractions/*.java
	javac ../malo/*.java
	javac ../fractions/*.java
	javac Polynomial.java
	javac Tester.java
	java Tester.java
