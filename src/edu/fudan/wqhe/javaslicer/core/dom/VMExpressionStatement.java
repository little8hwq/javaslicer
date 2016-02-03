package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.Collection;

import org.eclipse.jdt.core.dom.ExpressionStatement;

import edu.fudan.wqhe.javaslicer.core.dom.expression.VMExpression;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMExpressionStatement extends VMStatement {
	private VMExpression expression;
	
	
	public VMExpressionStatement(ExpressionStatement statement, VMExpression expression) {
		super(statement);
		this.expression=expression;
	}

	@Override
	public Collection<IVariable> getDefs() {
		return expression.getDefs();
	}

	@Override
	public Collection<IVariable> getUses() {
		return expression.getUses();
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		expression.accept(visitor);
	}

	@Override
	public String toString() {
		return statement.toString();
	}

}
