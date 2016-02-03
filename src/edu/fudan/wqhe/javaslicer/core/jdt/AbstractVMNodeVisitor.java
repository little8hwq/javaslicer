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

public class AbstractVMNodeVisitor implements IVMNodeVisitor {

	@Override
	public void visit(VMBlock node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VMExpressionStatement node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VMMethodInvocation node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VMVariableDeclarationFragment node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VMVariableDeclarationStatement node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VMInfixExpression node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMAssignment vmAssignment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMNumberLiteral vmNumberLiteral) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMIfStatement node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMForStatement node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMWhileStatement node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMVariableReference node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMPostfixExpression node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMPrefixExpression node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMStringLiteral node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMTypeLiteral node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMBooleanLiteral node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMNullLiteral node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMClassInstanceCreation node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMThisExpression node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMArrayCreation node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMArrayInitializer node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMArrayAccess node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VMTryStatement node) {
		// TODO Auto-generated method stub
		
	}

}
