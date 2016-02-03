package edu.fudan.wqhe.javaslicer.core.model;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ILocalVariable;

public class LocalVariable extends IVariable {
	private ILocalVariable variable;
	
	public LocalVariable(ILocalVariable variable){
		this.variable=variable;
	}

	@Override
	public String getName() {
		return variable.getElementName();
	}
	
	@Override
	public IJavaElement getContainer() {
		return variable.getParent();
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if(object instanceof LocalVariable){
			LocalVariable v1=(LocalVariable)object;
			if(v1.variable.getElementName().equals(variable.getElementName())){
				return true;
			}
		}
		return false;
	}
	
}
