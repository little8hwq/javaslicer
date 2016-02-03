package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.WhileStatement;

import edu.fudan.wqhe.javaslicer.core.dom.expression.VMExpression;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMWhileStatement extends VMBlock {
	private VMExpression expression;
	
	public VMWhileStatement(WhileStatement block) {
		super(block);
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.addAll(expression.getDefs());
		result.addAll(super.getDefs());
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.addAll(expression.getUses());
		result.addAll(super.getUses());
		return result;
	}
	
	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		if(expression!=null)
			expression.accept(visitor);
		for(VMStatement statement : statements){
			statement.accept(visitor);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("while(").append(expression.toString()).append(") {\n");
		for (VMStatement s : statements) {
			sb.append("  " + s.toString());
		}
		sb.append("}\n");
		return sb.toString();
	}
	
	public VMExpression getExpression() {
		return expression;
	}

	public void setExpression(VMExpression expression) {
		this.expression = expression;
	}
	
	

}
