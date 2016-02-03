package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.ASTNode;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMArrayAccess extends VMExpression {
   private VMExpression array;
   private VMExpression index;
	public VMArrayAccess(ASTNode expression, VMExpression array, VMExpression index) {
		super(expression);
		this.array=array;
		this.index=index;
	}

	@Override
	public Collection<IVariable> getDefs() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.addAll(array.getUses());
		result.addAll(index.getUses());
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		array.accept(visitor);
		index.accept(visitor);
	}

	public VMExpression getArray() {
		return array;
	}

	public VMExpression getIndex() {
		return index;
	}

}
