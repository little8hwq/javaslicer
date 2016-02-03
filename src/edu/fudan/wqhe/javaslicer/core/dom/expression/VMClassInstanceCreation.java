package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMClassInstanceCreation extends VMExpression {
	Type type;

	Collection<VMExpression> arguments; 
	
	public VMClassInstanceCreation(ASTNode expression, Type type) {
		super(expression);
		this.type=type;
		this.arguments = new ArrayList<VMExpression>();
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
		for(VMExpression ex:arguments){
			result.addAll(ex.getUses());
		}
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		for(VMExpression ex:arguments){
			ex.accept(visitor);
		}
	}

	public Type getType() {
		return type;
	}

	public Collection<VMExpression> getArguments() {
		return arguments;
	}

}
