package edu.fudan.wqhe.javaslicer.core;

import java.util.Collection;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import edu.fudan.wqhe.javaslicer.core.dom.VMBlock;
import edu.fudan.wqhe.javaslicer.core.dom.VMExpressionStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMForStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMIfStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMTryStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMVariableDeclarationStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMWhileStatement;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMClassInstanceCreation;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMExpression;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMVariableDeclarationFragment;
import edu.fudan.wqhe.javaslicer.core.jdt.MethodVisitor;

public class VM {

	public static VMBlock asVMBlock(Block block) {
		long start=System.currentTimeMillis();
		VMBlock result=new VMBlock(block);
		block.accept(new MethodVisitor(result));
		System.out.println("construct: "+(double)(System.currentTimeMillis()-start)/1000000);
		return result;
	}

	public static VMVariableDeclarationStatement asVariableDeclarationStatement(VariableDeclarationStatement statement, Collection<VMVariableDeclarationFragment> fragments) {
		VMVariableDeclarationStatement result = new VMVariableDeclarationStatement(
				statement, fragments);
		return result;
	}

	public static VMExpressionStatement asVMExpressionStatement(
			ExpressionStatement statement, VMExpression expression) {
		VMExpressionStatement result=new VMExpressionStatement(statement,expression);
		return result;
	}

	public static VMIfStatement createIfStatement(IfStatement node) {
		VMIfStatement result = new VMIfStatement(node);
		return result;
	}

	public static VMWhileStatement createWhileStatement(WhileStatement node) {
		VMWhileStatement result = new VMWhileStatement(node);
		return result;
	}

	public static VMForStatement createForStatement(ForStatement node) {
		VMForStatement result = new VMForStatement(node);
		return result;
	}

	public static VMClassInstanceCreation createClassInstanceCreation(
			Type type, ClassInstanceCreation node) {
		VMClassInstanceCreation result = new VMClassInstanceCreation(node, type);
		return result;
	}

	public static VMTryStatement createTryStatement(TryStatement node) {
		VMTryStatement result = new VMTryStatement(node);
		return result;
	}
	
}
