package edu.fudan.wqhe.javaslicer.core.dom;

import java.util.Collection;

import org.eclipse.jdt.core.dom.ASTNode;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public interface VMNode {
	ASTNode getASTNode();
	public Collection<IVariable> getDefs();
	public Collection<IVariable> getUses(); 
	void accept(IVMNodeVisitor visitor);
}
