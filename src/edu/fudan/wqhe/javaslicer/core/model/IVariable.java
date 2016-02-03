package edu.fudan.wqhe.javaslicer.core.model;

import org.eclipse.jdt.core.IJavaElement;

public abstract class IVariable {
	
	public abstract String getName();
	public abstract boolean equals(Object object);
	public abstract IJavaElement getContainer();
	
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		IJavaElement container=getContainer();
		String name=getName();
		result = prime * result
				+ ((container == null) ? 0 : container.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
