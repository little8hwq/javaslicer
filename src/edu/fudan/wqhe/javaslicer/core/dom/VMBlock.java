package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.Statement;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMBlock extends VMStatement{
	protected Collection<VMStatement> statements;
	
	public VMBlock(Statement block){
		super(block);
		this.statements=new ArrayList<VMStatement>();
	}
	
	public Collection<VMStatement> getStatements() {
		return statements;
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> defs=new HashSet<IVariable>();
		for(VMStatement s:statements){
			defs.addAll(s.getDefs());
		}
		return defs;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> uses=new HashSet<IVariable>();
		for(VMStatement s:statements){
			uses.addAll(s.getUses());
		}
		return uses;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		for(VMStatement s:statements){
			s.accept(visitor);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\n\r");
		for (VMStatement s : this.getStatements()) {
			sb.append("  "+s.toString());
			sb.append("\n");
		}
		sb.append("}\n\r");
		return sb.toString();
	}
	
}
