package edu.fudan.wqhe.javaslicer.core.jdt;

import edu.fudan.wqhe.javaslicer.core.dom.VMBlock;
import edu.fudan.wqhe.javaslicer.core.dom.VMExpressionStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMForStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMIfStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMTryStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMVariableDeclarationStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMWhileStatement;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMArrayAccess;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMArrayCreation;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMArrayInitializer;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMAssignment;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMBooleanLiteral;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMClassInstanceCreation;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMInfixExpression;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMMethodInvocation;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMNullLiteral;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMNumberLiteral;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMPostfixExpression;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMPrefixExpression;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMStringLiteral;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMThisExpression;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMTypeLiteral;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMVariableDeclarationFragment;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMVariableReference;

public interface IVMNodeVisitor {
	public void visit(VMBlock node);
	public void visit(VMExpressionStatement node);
	public void visit(VMForStatement node);
	public void visit(VMIfStatement node);
	public void visit(VMVariableDeclarationFragment node);
	public void visit(VMVariableDeclarationStatement node);
	public void visit(VMWhileStatement node);
	public void visit(VMTryStatement node);
	public void visit(VMAssignment node);
	public void visit(VMInfixExpression node);
	public void visit(VMMethodInvocation node);
	public void visit(VMNumberLiteral node);
	public void visit(VMPostfixExpression node);
	public void visit(VMPrefixExpression node);
	public void visit(VMStringLiteral node);
	public void visit(VMVariableReference node);
	public void visit(VMTypeLiteral node);
	public void visit(VMBooleanLiteral node);
	public void visit(VMNullLiteral node);
	public void visit(VMClassInstanceCreation node);
	public void visit(VMThisExpression node);
	public void visit(VMArrayCreation node);
	public void visit(VMArrayInitializer node);
	public void visit(VMArrayAccess node);


	
}
