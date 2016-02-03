package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;

import edu.fudan.wqhe.javaslicer.core.dom.VMNode;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMTypeLiteral extends VMExpression implements VMNode {
    Type value;
    
	public VMTypeLiteral(ASTNode expression, Type type) {
		super(expression);
		this.value=type;
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
