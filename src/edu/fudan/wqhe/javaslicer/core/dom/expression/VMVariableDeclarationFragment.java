package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

/**
 * VariableDeclarationFragment: Identifier { [] } [ = Expression ]
 * 
 * @author wenqihe
 *
 */
public class VMVariableDeclarationFragment extends VMExpression {
	private VariableDeclarationFragment fragment;
	private IVariable def;
	private VMExpression initializer;

	public VMVariableDeclarationFragment(VariableDeclarationFragment fragment,
			IVariable def) {
		this(fragment, def, null);

	}

	public VMVariableDeclarationFragment(VariableDeclarationFragment fragment,
			IVariable def, VMExpression initializer) {
		super(fragment);
		this.fragment = fragment;
		this.def = def;
		this.initializer = initializer;
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.add(def);
		if (initializer != null) {
			result.addAll(initializer.getDefs());
		}
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		if (initializer != null) {
			result.addAll(initializer.getUses());
		}
		return result;
	}

	@Override
	public ASTNode getASTNode() {
		return fragment;
	}


	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
	}
}
