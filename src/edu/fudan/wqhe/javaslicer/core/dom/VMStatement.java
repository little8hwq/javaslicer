package edu.fudan.wqhe.javaslicer.core.dom;

import org.eclipse.jdt.core.dom.Statement;


public abstract class VMStatement implements VMNode{
	protected Statement statement;
	
	public VMStatement(Statement statement){
		this.statement=statement;
	}
	
	public Statement getASTNode() {
		return statement;
	}
	
	public abstract String toString();

	@Override
	public boolean equals(Object o){
		if(o.getClass()!=this.getClass())
			return false;
		VMStatement s=(VMStatement)o;
		if(s.statement!=this.statement){
			return false;
		}
		return true;
	}
}
