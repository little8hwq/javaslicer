package edu.fudan.wqhe.javaslicer.core;

import java.util.Collection;

import edu.fudan.wqhe.javaslicer.core.dom.VMBlock;
import edu.fudan.wqhe.javaslicer.core.dom.VMStatement;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class Slice {
	private VMBlock block;

	private Collection<VMStatement> relevantStatements;

	private Collection<IVariable> relevantVariables;

	public VMBlock getBlock() {
		return block;
	}

	public Collection<VMStatement> getRelevantStatements() {
		return relevantStatements;
	}

	public Collection<IVariable> getRelevantVariables() {
		return relevantVariables;
	}

	public Slice(VMBlock block, Collection<VMStatement> relevantStatements,
			Collection<IVariable> relevantVariables) {
		this.block = block;
		this.relevantStatements = relevantStatements;
		this.relevantVariables = relevantVariables;
	}
}
