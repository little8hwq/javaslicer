package edu.fudan.wqhe.javaslicer;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Test {

	public static void main(String[] args) {
		// Initialize ASTParser
				ASTParser parser = ASTParser.newParser(AST.JLS4); //initialize	
				parser.setKind(ASTParser.K_COMPILATION_UNIT);	  //to parse compilation unit
				parser.setSource("public class Test{ public void method(int a){;}}".toCharArray()); 		  //content is a string which stores the java source
				parser.setResolveBindings(true);
				CompilationUnit result = (CompilationUnit) parser.createAST(null);
				result.accept(new ASTVisitor(){
					public void preVisit(ASTNode node) {
						System.out.println(node.getClass());
						System.out.println(node.toString());
					}
				});
	}

}
