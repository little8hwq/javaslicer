package edu.fudan.wqhe.javaslicer.core;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class RelevantVariableCollection {

	Set<IVariable> relevantVariables = new LinkedHashSet<IVariable>();

	public RelevantVariableCollection() {

	}

	public RelevantVariableCollection(RelevantVariableCollection initialVars) {
		relevantVariables.addAll(initialVars.relevantVariables);
	}

	public void addRelevants(Collection<IVariable> variables) {
		if (variables != null) {
			relevantVariables.addAll(variables);
		}
	}

	public void addRelevant(IVariable variable) {
		relevantVariables.add(variable);
	}

	public void removeRelevants(Collection<IVariable> uses) {
		if (uses != null) {
			for (IVariable var : uses) {
				relevantVariables.remove(var);
			}
		}
	}

	public boolean containRelevantVariables() {
		return relevantVariables.size() != 0;
	}

	public boolean containOneofVariable(Collection<IVariable> variables) {
		if (variables == null) {
			return false;
		}
		for (IVariable var : variables) {
			if (relevantVariables.contains(var))
				return true;
		}
		return false;
	}

	// public boolean containAllVariable(Collection<IVariable> variables) {
	// return relevantVariables.containsAll(variables);
	// }

	public Collection<IVariable> getRelevantVariables() {
		return relevantVariables;
	}


	public boolean containAllVariable(Collection<IVariable> variables) {
		return relevantVariables.containsAll(variables);
	}

	public void removeAll() {
		relevantVariables.clear();
	}
	
	@Override
	public boolean equals(Object object) {
		RelevantVariableCollection target = (RelevantVariableCollection)
		 object;
		 return target.getRelevantVariables().size() == getRelevantVariables()
		 .size()
		 && target.containAllVariable(this.getRelevantVariables());
	}

	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for(IVariable v:relevantVariables){
			sb.append(v.getName()).append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}
