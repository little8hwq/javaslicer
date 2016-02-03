package edu.fudan.wqhe.javaslicer.core.model;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;

public class FieldVariable extends IVariable {
	private IField field;
	
	public FieldVariable(IField field) {
		this.field=field;
	}

	@Override
	public String getName() {
		return field.getElementName();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if(object instanceof FieldVariable){
			FieldVariable v1=(FieldVariable)object;
			if(v1.field.getElementName().equals(field.getElementName())){
				return true;
			}
		}
		return false;
	}

	@Override
	public IJavaElement getContainer() {
		return field.getParent();
	}

}
