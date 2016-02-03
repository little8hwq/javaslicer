package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.ForStatement;

import edu.fudan.wqhe.javaslicer.core.dom.expression.VMExpression;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;


public class VMForStatement extends VMBlock {
	Collection<VMExpression> initializers;
	Collection<VMExpression> updaters;
	VMExpression expression;

	public VMForStatement(ForStatement statement) {
		super(statement);
		initializers = new ArrayList<VMExpression>();
		updaters = new ArrayList<VMExpression>();
	}
		
	public VMExpression getExpression() {
		return expression;
	}

	public void setExpression(VMExpression expression) {
		this.expression = expression;
	}

	public Collection<VMExpression> getInitializers() {
		return initializers;
	}

	public Collection<VMExpression> getUpdaters() {
		return updaters;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		if (expression != null)
			result.addAll(expression.getUses());
		for (VMExpression exp : initializers) {
			result.addAll(exp.getUses());
		}
		for (VMExpression exp : updaters) {
			result.addAll(exp.getUses());
		}
		result.addAll(super.getUses());
		return result;
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		if (expression != null)
			result.addAll(expression.getDefs());
		for (VMExpression exp : initializers) {
			result.addAll(exp.getDefs());
		}
		for (VMExpression exp : updaters) {
			result.addAll(exp.getDefs());
		}
		result.addAll(super.getDefs());
		return result;
	}
	
	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		for(VMExpression e : initializers){
			e.accept(visitor);
		}
		for(VMExpression e : updaters){
			e.accept(visitor);
		}
		if(expression!=null)
			expression.accept(visitor);
		for(VMStatement statement : statements){
			statement.accept(visitor);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("for (");
		for (VMExpression e:initializers){
			sb.append(e).append(",");
		}
		sb.deleteCharAt(sb.length()-1).append(";");
		sb.append(expression.toString()).append(";");
		for (VMExpression e:updaters) {
			sb.append(e).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(") {\n");
		for (VMStatement s : statements) {
			sb.append("  " + s.toString());
		}
		sb.append("}\n");
		return sb.toString();
	}
}
