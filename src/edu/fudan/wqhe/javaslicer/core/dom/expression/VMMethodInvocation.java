package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.Expression;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMMethodInvocation extends VMExpression {
	private VMExpression role;
	private Collection<VMExpression> arguments;
	private String name;
	
	public VMMethodInvocation(Expression expression, String name, VMExpression role) {
		super(expression);
		this.name=name;
		this.role=role;
		this.arguments=new ArrayList<VMExpression>();
	}
	
	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result=new LinkedHashSet<IVariable>();
		for(VMExpression ex:arguments){
			if(!(ex instanceof VMVariableReference)){
				result.addAll(ex.getDefs());
			}
		}
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result=new LinkedHashSet<IVariable>();
		if(role!=null){
			result.addAll(role.getUses());
		}
		for(VMExpression ex:arguments){
			result.addAll(ex.getUses());
		}
		return result;
	}


	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		if(role!=null){
			role.accept(visitor);
		}
		for(VMExpression ex:arguments){
			ex.accept(visitor);
		}
	}

	public String getName() {
		return name;
	}

	public Collection<VMExpression> getArguments() {
		return arguments;
	}
}
