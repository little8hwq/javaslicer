package edu.fudan.wqhe.javaslicer.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import edu.fudan.wqhe.javaslicer.core.dom.VMBlock;
import edu.fudan.wqhe.javaslicer.core.dom.VMForStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMIfStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMTryStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMWhileStatement;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMMethodInvocation;
import edu.fudan.wqhe.javaslicer.core.jdt.AbstractVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class Slicer {

	public static Slice calcSlice(VMBlock block, IMethod targetMethod) {
		Collection<VMStatement> relevantStatements = new ArrayList<VMStatement>();
		RelevantVariableCollection relevantVariables = new RelevantVariableCollection();
		slice(block.getStatements(), targetMethod, relevantStatements,
				relevantVariables);
		return new Slice(block, relevantStatements,
				relevantVariables.getRelevantVariables());
	}

	private static void slice(Collection<VMStatement> statements,
			IMethod method, Collection<VMStatement> relevantStatements,
			RelevantVariableCollection relevantVariables) {
		VMStatement[] s = statements.toArray(new VMStatement[] {});
		for (int i = 0; i < s.length; i++) {
			VMStatement curStmt = s[s.length - i - 1];
			relevantVariables
					.addRelevants(getRelevantVariables(curStmt, method));
			if (!relevantVariables.containRelevantVariables())
				continue;
			else if (curStmt instanceof VMIfStatement) {
				sliceIfStatement(method, relevantStatements, relevantVariables,
						curStmt);
			} else if (curStmt instanceof VMWhileStatement) {
				sliceWhileStatement(method, relevantStatements,
						relevantVariables, curStmt);
			} else if (curStmt instanceof VMForStatement){
				sliceForStatement(method,relevantStatements,relevantVariables, curStmt);
			} else if (curStmt instanceof VMTryStatement) {
				sliceTryStatement(method, relevantStatements,
						relevantVariables, (VMTryStatement) curStmt);
			}else {
				if (relevantVariables.containOneofVariable(curStmt.getDefs())) {
					relevantVariables.removeRelevants(curStmt.getDefs());
					relevantVariables.addRelevants(curStmt.getUses());
					relevantStatements.add(curStmt);
				}
			}
		}

	}

	
	private static void sliceTryStatement(IMethod method,
			Collection<VMStatement> relevantStatements,
			RelevantVariableCollection relevantVariables, VMTryStatement curStmt) {
		RelevantVariableCollection bodyReleVars = new RelevantVariableCollection(
				relevantVariables);
		Collection<VMStatement> bodyStmts = new ArrayList<VMStatement>();
		slice(curStmt.getStatements(), method, bodyStmts,
				bodyReleVars);
		
		if (bodyStmts.size() > 0 ) {
			relevantVariables.removeAll();
			relevantVariables.addRelevants(bodyReleVars
						.getRelevantVariables());

			VMTryStatement newTryStatement = new VMTryStatement(
					curStmt.getASTNode());
			newTryStatement.getStatements().addAll(bodyStmts);
			relevantStatements.add(newTryStatement);
		}
	}

	private static void sliceForStatement(IMethod method,
			Collection<VMStatement> relevantStatements,
			RelevantVariableCollection relevantVariables, VMStatement curStmt) {
		RelevantVariableCollection lastRelVars = relevantVariables;
		RelevantVariableCollection bodyReleVars = new RelevantVariableCollection(
				relevantVariables);
		Collection<VMStatement> bodyReleStmts = new ArrayList<VMStatement>();
		VMForStatement forStmt = (VMForStatement) curStmt;
		slice(forStmt.getStatements(), method, bodyReleStmts, bodyReleVars);

		while (!lastRelVars.equals(bodyReleVars)) {
			lastRelVars = bodyReleVars;
			bodyReleVars = new RelevantVariableCollection(lastRelVars);
			slice(forStmt.getStatements(), method, bodyReleStmts,
					bodyReleVars);
		}

		if (bodyReleStmts.size() > 0) {
			relevantVariables.removeAll();
			relevantVariables.addRelevants(bodyReleVars.getRelevantVariables());
			relevantVariables.addRelevants(forStmt.getExpression().getUses());

			Collection<VMStatement> newBodyReleStmts = new ArrayList<VMStatement>(
					bodyReleStmts.size());
			for (VMStatement s : forStmt.getStatements()) {
				for (VMStatement b: bodyReleStmts) {
					if(b.equals(s)){
						newBodyReleStmts.add(b);
						break;
					}
					
				}
			}
			VMForStatement newForStmt = new VMForStatement(
					(ForStatement) forStmt.getASTNode());
			newForStmt.getInitializers().addAll(forStmt.getInitializers());
			newForStmt.getUpdaters().addAll(forStmt.getUpdaters());
			newForStmt.setExpression(forStmt.getExpression());
			newForStmt.getStatements().addAll(newBodyReleStmts);
			relevantStatements.add(newForStmt);
		}
	}

	private static void sliceWhileStatement(IMethod method,
			Collection<VMStatement> relevantStatements,
			RelevantVariableCollection relevantVariables, VMStatement curStmt) {
		RelevantVariableCollection lastRelVars = relevantVariables;
		RelevantVariableCollection bodyReleVars = new RelevantVariableCollection(
				relevantVariables);
		Collection<VMStatement> bodyReleStmts = new ArrayList<VMStatement>();
		VMWhileStatement whileStmt = (VMWhileStatement) curStmt;
		slice(whileStmt.getStatements(), method, bodyReleStmts, bodyReleVars);

		//TODO
		while (!lastRelVars.equals(bodyReleVars)) {
			lastRelVars = bodyReleVars;
			bodyReleVars = new RelevantVariableCollection(lastRelVars);
			slice(whileStmt.getStatements(), method, bodyReleStmts,
					bodyReleVars);
		}

		if (bodyReleStmts.size() > 0) {
			relevantVariables.removeAll();
			relevantVariables.addRelevants(bodyReleVars.getRelevantVariables());
			relevantVariables.addRelevants(whileStmt.getExpression().getUses());

			Collection<VMStatement> newBodyReleStmts = new ArrayList<VMStatement>(
					bodyReleStmts.size());
			for (VMStatement s : whileStmt.getStatements()) {
				for (VMStatement b: bodyReleStmts) {
					if(b.equals(s)){
						newBodyReleStmts.add(b);
						break;
					}
					
				}
			}
			VMWhileStatement newWhileStmt = new VMWhileStatement(
					(WhileStatement) whileStmt.getASTNode());
			newWhileStmt.setExpression(whileStmt.getExpression());
			newWhileStmt.getStatements().addAll(newBodyReleStmts);
			relevantStatements.add(newWhileStmt);
		}
	}

	private static void sliceIfStatement(IMethod method,
			Collection<VMStatement> relevantStatements,
			RelevantVariableCollection relevantVariables, VMStatement curStmt) {
		VMIfStatement ifStatement = (VMIfStatement) curStmt;

		RelevantVariableCollection thenBlockReleVars = new RelevantVariableCollection(
				relevantVariables);
		Collection<VMStatement> thenBlockStmts = new ArrayList<VMStatement>();
		slice(ifStatement.getThenBlock(), method, thenBlockStmts,
				thenBlockReleVars);

		RelevantVariableCollection elseBlockReleVars = new RelevantVariableCollection(
				relevantVariables);
		Collection<VMStatement> elseBlockStmts = new ArrayList<VMStatement>();
		slice(ifStatement.getElseBlock(), method, elseBlockStmts,
				elseBlockReleVars);

		if (thenBlockStmts.size() > 0 || elseBlockStmts.size() > 0) {
			relevantVariables.removeAll();
			if (thenBlockStmts.size() > 0) {
				relevantVariables.addRelevants(thenBlockReleVars
						.getRelevantVariables());
			}
			if (elseBlockStmts.size() > 0) {
				relevantVariables.addRelevants(elseBlockReleVars
						.getRelevantVariables());
			}
			relevantVariables.addRelevants(ifStatement.getExpression()
					.getUses());
			VMIfStatement newIfStatement = new VMIfStatement(
					(IfStatement) ifStatement.getASTNode());
			newIfStatement.setExpression(ifStatement.getExpression());
			newIfStatement.setThenBlock(thenBlockStmts);
			newIfStatement.setElseBlock(elseBlockStmts);
			relevantStatements.add(newIfStatement);
		}

	}

	private static Collection<IVariable> getRelevantVariables(
			VMStatement curStmt, final IMethod method) {
		final Collection<IVariable> variables = new LinkedHashSet<IVariable>();
		curStmt.accept(new AbstractVMNodeVisitor() {
			@Override
			public void visit(VMMethodInvocation node) {
				String name = node.getName();
				if (name.equals(method.getElementName())) {
					variables.addAll(node.getUses());
				}
			}
		});
		return variables;
	}

}
