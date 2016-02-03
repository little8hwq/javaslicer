package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jdt.core.dom.Expression;

import edu.fudan.wqhe.javaslicer.core.dom.VMNode;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMNumberLiteral extends VMExpression implements VMNode {
	String value;
	
	public VMNumberLiteral(Expression expression, String value) {
		super(expression);
		this.value=value;
	}

	@Override
	public Collection<IVariable> getDefs() {
		return Collections.emptySet();
	}

	@Override
	public Collection<IVariable> getUses() {
		return Collections.emptySet();
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
	}

}
