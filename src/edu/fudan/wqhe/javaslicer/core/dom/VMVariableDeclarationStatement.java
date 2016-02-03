package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import edu.fudan.wqhe.javaslicer.core.dom.expression.VMVariableDeclarationFragment;
import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

/**
 *  VariableDeclarationStatement:
    { Modifier } Type VariableDeclarationFragment
        { , VariableDeclarationFragment } ;
        
 * @author wenqihe
 *
 */
public class VMVariableDeclarationStatement extends VMStatement {
	private Collection<VMVariableDeclarationFragment> fragments;
	
	public VMVariableDeclarationStatement(VariableDeclarationStatement statement) {
		this(statement,new ArrayList<VMVariableDeclarationFragment>());
	}
	
	public VMVariableDeclarationStatement(VariableDeclarationStatement statement,Collection<VMVariableDeclarationFragment> fragments) {
		super(statement);
		this.fragments=fragments;
	}
	
	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> defs=new HashSet<IVariable>();
		for(VMVariableDeclarationFragment s:fragments){
			defs.addAll(s.getDefs());
		}
		return defs;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> uses=new HashSet<IVariable>();
		for(VMVariableDeclarationFragment s:fragments){
			uses.addAll(s.getUses());
		}
		return uses;
	}


	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
	}
	
	public Collection<VMVariableDeclarationFragment> getFragments() {
		return fragments;
	}

	@Override
	public String toString() {
		return statement.toString();
	}


}
