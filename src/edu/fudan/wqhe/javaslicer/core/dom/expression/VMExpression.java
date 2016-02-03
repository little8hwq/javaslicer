package edu.fudan.wqhe.javaslicer.core.dom.expression;

import org.eclipse.jdt.core.dom.ASTNode;

import edu.fudan.wqhe.javaslicer.core.dom.VMNode;

public abstract class VMExpression implements VMNode{
	private ASTNode expression;
	
	public VMExpression(ASTNode expression){
		this.expression=expression;
	}
	
	public ASTNode getASTNode(){
		return expression;
	}
	
	@Override
	public String toString(){
		return expression.toString();
	}
}
