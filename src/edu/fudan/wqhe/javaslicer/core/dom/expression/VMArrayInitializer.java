package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.ASTNode;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMArrayInitializer extends VMExpression {
	private Collection<VMExpression>expressions;
	
	public VMArrayInitializer(ASTNode expression) {
		super(expression);
		this.expressions=new ArrayList<VMExpression>();
	}

	@Override
	public Collection<IVariable> getDefs() {
		return Collections.emptySet();
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		for(VMExpression exp:expressions){
			result.addAll(exp.getUses());
		}
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		for(VMExpression exp:expressions){
			exp.accept(visitor);
		}
	}

	public Collection<VMExpression> getExpressions() {
		return expressions;
	}


}
