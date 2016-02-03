package edu.fudan.wqhe.javaslicer.core.dom.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.eclipse.jdt.core.dom.InfixExpression;

import edu.fudan.wqhe.javaslicer.core.jdt.IVMNodeVisitor;
import edu.fudan.wqhe.javaslicer.core.model.IVariable;

public class VMInfixExpression extends VMExpression {
	private Collection<VMExpression> expressions;
	private Operator operator;
	
	public VMInfixExpression(InfixExpression expression,Operator operator) {
		super(expression);
		this.expressions = new ArrayList<VMExpression>();
		this.operator=operator;
	}

	@Override
	public Collection<IVariable> getDefs() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		for (VMExpression ex : expressions) {
			if(!(ex instanceof VMVariableReference)){
				result.addAll(ex.getDefs());
			}
		}
		return result;
	}

	@Override
	public Collection<IVariable> getUses() {
		Collection<IVariable> result = new LinkedHashSet<IVariable>();
		for (VMExpression exp : expressions) {
			result.addAll(exp.getUses());
		}
		return result;
	}

	@Override
	public void accept(IVMNodeVisitor visitor) {
		visitor.visit(this);
		for(VMExpression expression : expressions){
			expression.accept(visitor);
		}
	}

	public Operator getOperator() {
		return operator;
	}
	
	public Collection<VMExpression> getExpressions() {
		return expressions;
	}
	
	public static class Operator {

		/**
		 * The token for the operator.
		 */
		private String token;

		/**
		 * Creates a new infix operator with the given token.
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

		/** Multiplication "*" operator. */
		public static final Operator TIMES = new Operator("*");//$NON-NLS-1$
		/** Division "/" operator. */
		public static final Operator DIVIDE = new Operator("/");//$NON-NLS-1$
		/** Remainder "%" operator. */
		public static final Operator REMAINDER = new Operator("%");//$NON-NLS-1$
		/** Addition (or string concatenation) "+" operator. */
		public static final Operator PLUS = new Operator("+");//$NON-NLS-1$
		/** Subtraction "-" operator. */
		public static final Operator MINUS = new Operator("-");//$NON-NLS-1$
		/** Left shift "&lt;&lt;" operator. */
		public static final Operator LEFT_SHIFT = new Operator("<<");//$NON-NLS-1$
		/** Signed right shift "&gt;&gt;" operator. */
		public static final Operator RIGHT_SHIFT_SIGNED = new Operator(">>");//$NON-NLS-1$
		/** Unsigned right shift "&gt;&gt;&gt;" operator. */
		public static final Operator RIGHT_SHIFT_UNSIGNED = new Operator(">>>");//$NON-NLS-1$
		/** Less than "&lt;" operator. */
		public static final Operator LESS = new Operator("<");//$NON-NLS-1$
		/** Greater than "&gt;" operator. */
		public static final Operator GREATER = new Operator(">");//$NON-NLS-1$
		/** Less than or equals "&lt;=" operator. */
		public static final Operator LESS_EQUALS = new Operator("<=");//$NON-NLS-1$
		/** Greater than or equals "&gt=;" operator. */
		public static final Operator GREATER_EQUALS = new Operator(">=");//$NON-NLS-1$
		/** Equals "==" operator. */
		public static final Operator EQUALS = new Operator("==");//$NON-NLS-1$
		/** Not equals "!=" operator. */
		public static final Operator NOT_EQUALS = new Operator("!=");//$NON-NLS-1$
		/** Exclusive OR "^" operator. */
		public static final Operator XOR = new Operator("^");//$NON-NLS-1$
		/** Inclusive OR "|" operator. */
		public static final Operator OR = new Operator("|");//$NON-NLS-1$
		/** AND "&amp;" operator. */
		public static final Operator AND = new Operator("&");//$NON-NLS-1$
		/** Conditional OR "||" operator. */
		public static final Operator CONDITIONAL_OR = new Operator("||");//$NON-NLS-1$
		/** Conditional AND "&amp;&amp;" operator. */
		public static final Operator CONDITIONAL_AND = new Operator("&&");//$NON-NLS-1$

		/**
		 * Map from token to operator (key type: <code>String</code>; value
		 * type: <code>Operator</code>).
		 */
		private static final Map<String, Operator> CODES;
		static {
			CODES = new HashMap<String, Operator>(20);
			Operator[] ops = { TIMES, DIVIDE, REMAINDER, PLUS, MINUS,
					LEFT_SHIFT, RIGHT_SHIFT_SIGNED, RIGHT_SHIFT_UNSIGNED, LESS,
					GREATER, LESS_EQUALS, GREATER_EQUALS, EQUALS, NOT_EQUALS,
					XOR, OR, AND, CONDITIONAL_OR, CONDITIONAL_AND, };
			for (int i = 0; i < ops.length; i++) {
				CODES.put(ops[i].toString(), ops[i]);
			}
		}

		/**
		 * Returns the infix operator corresponding to the given string, or
		 * <code>null</code> if none.
		 * <p>
		 * <code>toOperator</code> is the converse of <code>toString</code>:
		 * that is, <code>Operator.toOperator(op.toString()) == op</code> for
		 * all operators <code>op</code>.
		 * </p>
		 * 
		 * @param token
		 *            the character sequence for the operator
		 * @return the infix operator, or <code>null</code> if none
		 */
		public static Operator toOperator(String token) {
			return CODES.get(token);
		}

	}


}
