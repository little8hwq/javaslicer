package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.LinkedHashSet;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMVariableReference extends VMExpression {
	private IVariable variable;
	
	public VMVariableReference(IVariable variable) {
		super(null);
		this.variable=variable;
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result=new LinkedHashSet<IVariable>();
		result.add(variable);
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result=new LinkedHashSet<IVariable>();
		result.add(variable);
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
	}

	public IVariable getVariable() {
		return variable;
	}

}
