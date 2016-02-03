package edu.fudan.wqhe.javaslicer.core.jdt;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

import edu.fudan.wqhe.javaslicer.core.VM;
import edu.fudan.wqhe.javaslicer.core.dom.VMBlock;
import edu.fudan.wqhe.javaslicer.core.dom.VMExpressionStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMForStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMIfStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMNode;
import edu.fudan.wqhe.javaslicer.core.dom.VMStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMTryStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMVariableDeclarationStatement;
import edu.fudan.wqhe.javaslicer.core.dom.VMWhileStatement;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMArrayAccess;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMArrayCreation;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMArrayInitializer;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMAssignment;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMBooleanLiteral;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMClassInstanceCreation;
import edu.fudan.wqhe.javaslicer.core.dom.expression.VMExpression;
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
import edu.fudan.wqhe.javaslicer.core.model.FieldVariable;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;
import edu.fudan.wqhe.javaslicer.core.model.LocalVariable;

public class MethodVisitor extends ASTVisitor {
	private Stack<VMNode> expressions;//including expressions and variableDeclarations
	private Stack<VMBlock> blocks;
	private VMBlock topBlock;

	public MethodVisitor(VMBlock topBlock) {
		expressions = new Stack<VMNode>();
		blocks = new Stack<VMBlock>();
		this.topBlock = topBlock;
		blocks.push(topBlock);
	}

	@Override
	public void preVisit(ASTNode node) {
//		System.out.println(node.getClass());
//		System.out.println(node.toString());
	}
	
	@Override
	public void endVisit(SimpleName node) {
		IBinding binding = node.resolveBinding();
		if (binding instanceof IVariableBinding) {
			IVariableBinding vbinding=(IVariableBinding)binding;
			IJavaElement element=vbinding.getJavaElement();
			IVariable variable=null;
			if(element instanceof ILocalVariable){
				variable=new LocalVariable((ILocalVariable)element);
			}else if(element instanceof IField){
				variable=new FieldVariable((IField)element);
			}
			if(variable!=null){
				VMVariableReference variableReference=new VMVariableReference(variable);
				expressions.push(variableReference);
			}
		} 
		
	}
	
	@Override
	public void endVisit(Assignment node) {
		VMExpression right = (VMExpression)(expressions.pop());
		VMExpression left =(VMExpression)(expressions.pop());
		VMAssignment.Operator operator = VMAssignment.Operator.toOperator(node
				.getOperator().toString());
		expressions
				.push(new VMAssignment(node,left,right,operator));
	}
	
	@Override
	public void endVisit(NumberLiteral node) {
		expressions.push(new VMNumberLiteral(node,node.getToken()));
	}
	
	@Override
	public void endVisit(StringLiteral node) {
		expressions.push(new VMStringLiteral(node, node.getLiteralValue()));
	}
	
	@Override
	public void endVisit(CharacterLiteral node) {
		expressions.push(new VMStringLiteral(node, node.getEscapedValue()));
	}

	public void endVisit(TypeLiteral node) {
		expressions.pop();
		expressions.push(new VMTypeLiteral(node, node.getType()));
	}

	@Override
	public void endVisit(BooleanLiteral node) {
		expressions.push(new VMBooleanLiteral(node,node.booleanValue()
				));
	}

	@Override
	public void endVisit(NullLiteral node) {
		expressions.push(new VMNullLiteral(node));
	}
	
	@Override
	public void endVisit(MethodInvocation node) {
		//TODO need more complex cases
		Stack<VMExpression> arguments = new Stack<VMExpression>();
		for (int i = 0; i < node.arguments().size(); i++) {
			arguments.push((VMExpression) expressions.pop());
		}
		VMExpression role =null;
		if(!Modifier.isStatic(node.resolveMethodBinding().getModifiers())){
			role = (node.getExpression() == null) ? null
				: (VMExpression)(expressions.pop());
		}
		VMMethodInvocation methodInvo = new VMMethodInvocation(node,node.getName().toString(), role);
		while (!arguments.empty()){
			methodInvo.getArguments().add(arguments.pop());
		}
		expressions.push(methodInvo);
	}
	
	@Override
	public void endVisit(ClassInstanceCreation node) {
		Stack<VMExpression> arguments = new Stack<VMExpression>();
		for (int i = 0; i < node.arguments().size(); i++) {
			arguments.push((VMExpression) expressions.pop());
		}
		VMClassInstanceCreation classInstanceCreation = VM
				.createClassInstanceCreation(node.getType(), node);
		while (!arguments.empty())
			classInstanceCreation.getArguments().add(arguments.pop());
		expressions.push(classInstanceCreation);

	};
	
	public void endVisit(ArrayCreation node) {
		VMArrayInitializer role = (node.getInitializer() == null) ? null
				: (VMArrayInitializer)(expressions.pop());
		Stack<VMExpression> arguments = new Stack<VMExpression>();
		for (int i = 0; i < node.dimensions().size(); i++) {
			arguments.push((VMExpression) expressions.pop());
		}
		VMArrayCreation arrayCreation = new VMArrayCreation(node, role);
		while (!arguments.empty()){
			arrayCreation.getDimensions().add(arguments.pop());
		}
		expressions.push(arrayCreation);
	}
	
	public void endVisit(ArrayInitializer node) {
		Stack<VMExpression> arguments = new Stack<VMExpression>();
		for (int i = 0; i < node.expressions().size(); i++) {
			arguments.push((VMExpression) expressions.pop());
		}
		VMArrayInitializer initializer =new VMArrayInitializer(node);
		while (!arguments.empty()){
			initializer.getExpressions().add(arguments.pop());
		}
		expressions.push(initializer);
	}
	
	public void endVisit(ArrayAccess node) {
		VMExpression index=(VMExpression) expressions.pop();
		VMExpression array=(VMExpression) expressions.pop();
		VMArrayAccess arrayAccess=new VMArrayAccess(node,array,index);
		expressions.push(arrayAccess);
	}
	
	public boolean visit(CatchClause node) {
		return false;
	}

	public void endVisit(CatchClause node) {
	}
	
	@Override
	public void endVisit(InfixExpression node) {
		Stack<VMExpression> exps = new Stack<VMExpression>();
		exps.push((VMExpression) expressions.pop());
		exps.push((VMExpression) expressions.pop());
		if (node.hasExtendedOperands()) {
			for (int i=0;i<node.extendedOperands().size();i++) {
				exps.push((VMExpression) expressions.pop());
			}
		}
		VMInfixExpression.Operator operator = VMInfixExpression.Operator
				.toOperator(node.getOperator().toString());

		VMInfixExpression infixExp =new VMInfixExpression(
				node,operator);
		while (!exps.empty()) {
			infixExp.getExpressions().add(exps.pop());
		}
		expressions.push(infixExp);
	};
	
	public void endVisit(PostfixExpression node) {
		VMExpression expression = (VMExpression) expressions.pop();
		PostfixExpression.Operator rawOperator = node.getOperator();
		VMPostfixExpression.Operator operator = VMPostfixExpression.Operator
				.toOperator(rawOperator.toString());
		expressions.push(new VMPostfixExpression(node, operator,expression));
	};

	@Override
	public void endVisit(PrefixExpression node) {
		VMExpression expression = (VMExpression) expressions.pop();
		PrefixExpression.Operator rawOperator = node.getOperator();
		VMPrefixExpression.Operator operator = VMPrefixExpression.Operator
				.toOperator(rawOperator.toString());
		expressions.push(new VMPrefixExpression(node, operator, expression));
	};
	
	@Override
	public void endVisit(ThisExpression node) {
		expressions.push(new VMThisExpression(node));
	};
	
	@Override
	public void endVisit(VariableDeclarationFragment node) {
		VMExpression initializer = (node.getInitializer() == null) ? null
				: (VMExpression)(expressions.pop());
		VMVariableReference reference=(VMVariableReference)(expressions.pop());
		VMVariableDeclarationFragment vardecl = new VMVariableDeclarationFragment(node,reference.getVariable(), initializer);
		expressions.push(vardecl);
	}
	
	@Override
	public void endVisit(VariableDeclarationStatement node){
		Collection<VMVariableDeclarationFragment> vars = new ArrayList<VMVariableDeclarationFragment>();
		for (int i = 0; i < node.fragments().size(); i++) {
			vars.add((VMVariableDeclarationFragment) (expressions.pop()));
		}
		VMVariableDeclarationStatement statement=VM.asVariableDeclarationStatement(node,vars);
		topBlock.getStatements().add(statement);
	}
	
	@Override
	public void endVisit(ExpressionStatement node){
		VMExpressionStatement statement=VM.asVMExpressionStatement(node,(VMExpression)(expressions.pop()));
		topBlock.getStatements().add(statement);
	}
	
	public boolean visit(IfStatement node) {
		VMIfStatement ifStatement = VM.createIfStatement(node);
		topBlock = ifStatement;
		blocks.push(ifStatement);
		return true;
	}

	public void endVisit(IfStatement node) {
		((VMIfStatement) topBlock).setExpression((VMExpression)(expressions.pop()));

		
		int index = 0;
		int thenStatementSize = getStatementSize(node.getThenStatement());
		for (VMStatement statement : topBlock.getStatements()) {
			if (index < thenStatementSize)
				((VMIfStatement) topBlock).getThenBlock().add(statement);
			else
				((VMIfStatement) topBlock).getElseBlock().add(statement);
			index++;
		}
		VMStatement statement=blocks.pop();
		topBlock = blocks.peek();
		topBlock.getStatements().add(statement);
	}
	
	public boolean visit(WhileStatement node) {
		VMWhileStatement whileStatement = VM.createWhileStatement(node);
		topBlock = whileStatement;
		blocks.push(whileStatement);
		return true;
	}

	public void endVisit(WhileStatement node) {
		((VMWhileStatement) topBlock).setExpression((VMExpression)expressions.pop());
		VMStatement statement=blocks.pop();
		topBlock = blocks.peek();
		topBlock.getStatements().add(statement);
	}
	
	public boolean visit(ForStatement node) {
		VMForStatement forStatement = VM.createForStatement(node);
		topBlock = forStatement;
		blocks.push(forStatement);
		return true;
	}

	public void endVisit(ForStatement node) {
		VMForStatement forStatement = (VMForStatement) topBlock;
		for (int i=0;i<node.updaters().size();i++) {
			forStatement.getUpdaters().add((VMExpression) expressions.pop());
		}
		if (node.getExpression() != null)
			forStatement.setExpression((VMExpression) expressions.pop());
		for (int i=0;i<node.initializers().size();i++) {
			forStatement.getInitializers().add((VMExpression) expressions.pop());
		}
		VMStatement statement=blocks.pop();
		topBlock = blocks.peek();
		topBlock.getStatements().add(statement);
	}
	
	public boolean visit(TryStatement node) {
		VMTryStatement tryStatement = VM.createTryStatement(node);
		topBlock = tryStatement;
		blocks.push(tryStatement);
		return true;
	}

	public void endVisit(TryStatement node) {
		VMStatement statement=blocks.pop();
		topBlock = blocks.peek();
		topBlock.getStatements().add(statement);
	};
	
	private int getStatementSize(Statement statement) {
		if (statement instanceof Block) {
			return ((Block) statement).statements().size();
		} else
			return 1;
	}
	
}
