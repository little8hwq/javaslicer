package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.IfStatement;

import edu.fudan.wqhe.javaslicer.core.dom.expression.VMExpression;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMVariableReference;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMIfStatement extends VMBlock {
	VMExpression expression;
	Collection<VMStatement> thenBlock;
	Collection<VMStatement> elseBlock;

	public VMIfStatement(IfStatement statement) {
		super(statement);
		thenBlock = new ArrayList<VMStatement>();
		elseBlock = new ArrayList<VMStatement>();
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		if (!(expression instanceof VMVariableReference)) {
			result.addAll(expression.getDefs());
		}
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
		if (expression != null)
			expression.accept(visitor);
		for (VMStatement s : getStatements()) {
			s.accept(visitor);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("if(").append(expression.toString()).append(") {\n");
		for (VMStatement s : this.getThenBlock()) {
			sb.append("  " + s.toString());
		}
		sb.append("}\n");
		if (this.getElseBlock().size() > 0) {
			sb.append("else\n").append("{");
			for (VMStatement s : this.getElseBlock()) {
				sb.append("  " + s.toString());
			}
			sb.append("}\n");
		}
		return sb.toString();
	}

	public VMExpression getExpression() {
		return expression;
	}

	public void setExpression(VMExpression expression) {
		this.expression = expression;
	}

	public Collection<VMStatement> getThenBlock() {
		return thenBlock;
	}

	public void setThenBlock(Collection<VMStatement> thenBlock) {
		this.thenBlock = thenBlock;
	}

	public Collection<VMStatement> getElseBlock() {
		return elseBlock;
	}

	public void setElseBlock(Collection<VMStatement> elseBlock) {
		this.elseBlock = elseBlock;
	}

}
