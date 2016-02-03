package edu.fudan.wqhe.javaslicer.core.trace;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.core.JavaModelManager;

import edu.fudan.wqhe.javaslicer.core.Slice;
import edu.fudan.wqhe.javaslicer.core.Slicer;
import edu.fudan.wqhe.javaslicer.core.VM;
import edu.fudan.wqhe.javaslicer.core.jdt.MethodVisitor;

public class MethodTracer {

	private IMethod targetMethod;

	private Map<IMethod, Slice> slices;

	public MethodTracer(IMethod targetMethod) {
		this.targetMethod = targetMethod;
		this.slices = new HashMap<IMethod, Slice>();
	}

	public void doTrace() {
		Collection<IMethod> referencedMethods = findReferencedMethods(targetMethod);
		for (IMethod method : referencedMethods) {
			MethodDeclaration methodDecl=getMethodDeclaration(method);
			Slice slice=Slicer.calcSlice(VM.asVMBlock(methodDecl.getBody()), this.targetMethod);
			slices.put(method, slice);
		}
	}

	private Collection<IMethod> findReferencedMethods(IMethod method) {
		final Collection<IMethod> methods = new HashSet<IMethod>();
		SearchPattern pattern = SearchPattern.createPattern(method,
				IJavaSearchConstants.REFERENCES);
		SearchEngine engine = new SearchEngine();
		try {
			engine.search(pattern, new SearchParticipant[] { SearchEngine
					.getDefaultSearchParticipant() }, JavaModelManager
					.getJavaModelManager().getWorkspaceScope(),
					new SearchRequestor() {

						@Override
						public void acceptSearchMatch(SearchMatch match)
								throws CoreException {
							methods.add((IMethod) match.getElement());
						}

					}, null);
			return methods;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<IMethod>();
		}
	}

	private MethodDeclaration getMethodDeclaration(final IMethod method) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setResolveBindings(true);
		parser.setSource(method.getCompilationUnit());
		CompilationUnit parsedCompilationUnit = (CompilationUnit) parser
				.createAST(null);
		final MethodDeclaration[] result = new MethodDeclaration[1];
		parsedCompilationUnit.accept(new ASTVisitor() {
			public boolean visit(MethodDeclaration node) {
				if (node.resolveBinding().getJavaElement().equals(method))
					result[0] = node;
				return false;
			}
		});
		return result[0];
	}

	public IMethod getTargetMethod() {
		return targetMethod;
	}

	public Map<IMethod, Slice> getSlices() {
		return slices;
	}

	
}
