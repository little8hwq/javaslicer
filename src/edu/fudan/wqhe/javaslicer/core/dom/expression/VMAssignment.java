package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMAssignment extends VMExpression {
	private VMExpression left;
	private VMExpression right;
	private Operator operator;
	
	public VMAssignment(Expression expression, VMExpression left, VMExpression right, Operator operator) {
		super(expression);
		this.left=left;
		this.right=right;
		this.operator=operator;
	}
	
	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		if(left instanceof VMVariableReference)
			result.add(((VMVariableReference) left).getVariable());
		else if(left instanceof VMArrayAccess) 
			result.addAll(((VMArrayAccess)left).getArray().getDefs());
		if(!(right instanceof VMVariableReference)){
			result.addAll(right.getDefs());
		}
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		result.addAll(right.getUses());
		if (operator.hasSideEffect()){
			if(left instanceof VMVariableReference)
				result.add(((VMVariableReference) left).getVariable());
		}
		if(left instanceof VMArrayAccess) 
			result.addAll(((VMArrayAccess)left).getArray().getUses());
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
	}
	
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Assignment operators (typesafe enumeration).
	 * 
	 * <pre>
	 * AssignmentOperator:<code>
	 *    <b>=</b> ASSIGN
	 *    <b>+=</b> PLUS_ASSIGN
	 *    <b>-=</b> MINUS_ASSIGN
	 *    <b>*=</b> TIMES_ASSIGN
	 *    <b>/=</b> DIVIDE_ASSIGN
	 *    <b>&amp;=</b> BIT_AND_ASSIGN
	 *    <b>|=</b> BIT_OR_ASSIGN
	 *    <b>^=</b> BIT_XOR_ASSIGN
	 *    <b>%=</b> REMAINDER_ASSIGN
	 *    <b>&lt;&lt;=</b> LEFT_SHIFT_ASSIGN
	 *    <b>&gt;&gt;=</b> RIGHT_SHIFT_SIGNED_ASSIGN
	 *    <b>&gt;&gt;&gt;=</b> RIGHT_SHIFT_UNSIGNED_ASSIGN</code>
	 * </pre>
	 */
	public static class Operator {

		/**
		 * The name of the operator
		 */
		private String op;

		/**
		 * Creates a new assignment operator with the given name.
		 * <p>
		 * Note: this constructor is private. The only instances ever created
		 * are the ones for the standard operators.
		 * </p>
		 * 
		 * @param op
		 *            the character sequence for the operator
		 */
		private Operator(String op) {
			this.op = op;
		}

		/**
		 * Returns the character sequence for the operator.
		 * 
		 * @return the character sequence for the operator
		 */
		public String toString() {
			return this.op;
		}

		/** = operator. */
		public static final Operator ASSIGN = new Operator("=");//$NON-NLS-1$
		/** += operator. */
		public static final Operator PLUS_ASSIGN = new Operator("+=");//$NON-NLS-1$
		/** -= operator. */
		public static final Operator MINUS_ASSIGN = new Operator("-=");//$NON-NLS-1$
		/** *= operator. */
		public static final Operator TIMES_ASSIGN = new Operator("*=");//$NON-NLS-1$
		/** /= operator. */
		public static final Operator DIVIDE_ASSIGN = new Operator("/=");//$NON-NLS-1$
		/** &amp;= operator. */
		public static final Operator BIT_AND_ASSIGN = new Operator("&=");//$NON-NLS-1$
		/** |= operator. */
		public static final Operator BIT_OR_ASSIGN = new Operator("|=");//$NON-NLS-1$
		/** ^= operator. */
		public static final Operator BIT_XOR_ASSIGN = new Operator("^=");//$NON-NLS-1$
		/** %= operator. */
		public static final Operator REMAINDER_ASSIGN = new Operator("%=");//$NON-NLS-1$
		/** &lt;&lt;== operator. */
		public static final Operator LEFT_SHIFT_ASSIGN = new Operator("<<=");//$NON-NLS-1$
		/** &gt;&gt;= operator. */
		public static final Operator RIGHT_SHIFT_SIGNED_ASSIGN = new Operator(
				">>=");//$NON-NLS-1$
		/** &gt;&gt;&gt;= operator. */
		public static final Operator RIGHT_SHIFT_UNSIGNED_ASSIGN = new Operator(
				">>>=");//$NON-NLS-1$

		/**
		 * Returns the assignment operator corresponding to the given string, or
		 * <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for
		 * all operators <code>op</code>.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
		 * @return the assignment operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return (Operator) CODES.get(token);
		}

		public boolean hasSideEffect() {
			return this != ASSIGN;
		}

		/**
		 * Map from token to operator (key type: <code>String</code>; value
		 * type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = { ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, TIMES_ASSIGN,
					DIVIDE_ASSIGN, BIT_AND_ASSIGN, BIT_OR_ASSIGN,
					BIT_XOR_ASSIGN, REMAINDER_ASSIGN, LEFT_SHIFT_ASSIGN,
					RIGHT_SHIFT_SIGNED_ASSIGN, RIGHT_SHIFT_UNSIGNED_ASSIGN };
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString(), ops[i]);
			}
		}
	}

}
