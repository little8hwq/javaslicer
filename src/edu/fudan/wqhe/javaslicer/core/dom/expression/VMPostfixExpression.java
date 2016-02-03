package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMPostfixExpression extends VMExpression {
	private VMExpression expression;
	private Operator operator;
	
	public VMPostfixExpression(Expression node, Operator operator, VMExpression ex) {
		super(node);
		this.operator=operator;
		this.expression=ex;
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.addAll(expression.getDefs());
		result.add(((VMVariableReference) expression).getVariable());
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.addAll(expression.getUses());
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		if(expression!=null)
			expression.accept(visitor);
	}
	
	public VMExpression getExpression() {
		return expression;
	}

	public Operator getOperator() {
		return operator;
	}
	
	public static class Operator {

		/**
		 * The token for the operator.
		 */
		private String token;

		/**
		 * Creates a new postfix operator with the given token.
		 * <p>
		 * Note: this constructor is private. The only instances ever created
		 * are the ones for the standard operators.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
		 */
		private Operator(String token) {
			this.token = token;
		}

		/**
		 * Returns the character sequence for the operator.
		 * 
		 * @return the character sequence for the operator
		 */
		public String toString() {
			return token;
		}

		/** Postfix increment "++" operator. */
		public static final Operator INCREMENT = new Operator("++");//$NON-NLS-1$
		/** Postfix decrement "--" operator. */
		public static final Operator DECREMENT = new Operator("--");//$NON-NLS-1$

		/**
		 * Map from token to operator (key type: <code>String</code>; value
		 * type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = { INCREMENT, DECREMENT, };
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString(), ops[i]);
			}
		}

		/**
		 * Returns the postfix operator corresponding to the given string, or
		 * <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for
		 * all operators <code>op</code>.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
		 * @return the postfix operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return (Operator) CODES.get(token);
		}
	}





}
