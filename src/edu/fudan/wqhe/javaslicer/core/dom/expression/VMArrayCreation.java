package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.ASTNode;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMArrayCreation extends VMExpression {
	private VMArrayInitializer initializer;
	private Collection<VMExpression> dimensions;
	public VMArrayCreation(ASTNode expression, VMArrayInitializer initializer) {
		super(expression);
		this.initializer=initializer;
		this.dimensions=new ArrayList<VMExpression>();
	}

	@Override
	public Collection<IVariable> getDefs() {
		return Collections.emptySet();
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		if (initializer != null) {
			result.addAll(initializer.getUses());
		}
		for(VMExpression exp:dimensions){
			result.addAll(exp.getUses());
		}
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		if (initializer != null) {
			initializer.accept(visitor);
		}
		for(VMExpression exp:dimensions){
			exp.accept(visitor);
		}
	}

	public VMExpression getInitializer() {
		return initializer;
	}

	public Collection<VMExpression> getDimensions() {
		return dimensions;
	}
	
	

}
