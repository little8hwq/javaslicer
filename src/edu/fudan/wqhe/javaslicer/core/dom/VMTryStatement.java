package edu.fudan.wqhe.javaslicer.core.dom;

import org.eclipse.jdt.core.dom.Statement;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;

public class VMTryStatement extends VMBlock {

	public VMTryStatement(Statement statement) {
		super(statement);
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		for (VMStatement statement : statements) {
			statement.accept(visitor);
		}
	}
	
}
