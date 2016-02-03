package edu.fudan.wqhe.javaslicer.popup.actions;

import java.util.Collection;
import java.util.Map;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import edu.fudan.wqhe.javaslicer.core.Slice;
import edu.fudan.wqhe.javaslicer.core.dom.VMStatement;
import edu.fudan.wqhe.javaslicer.core.trace.MethodTracer;

public class SliceAction implements IObjectActionDelegate {

	private Shell shell;
	private IMethod selectedMethod;
	
	/**
	 * Constructor for Action1.
	 */
	public SliceAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(
			shell,
			"Javaslicer",
			"Slice was executed for method "+selectedMethod.getElementName());
		long start=System.currentTimeMillis();
		MethodTracer tracer = new MethodTracer(selectedMethod);
		tracer.doTrace();
		Map<IMethod, Slice>result=tracer.getSlices();
		long time=System.currentTimeMillis()-start;
		for(IMethod method:result.keySet()){
			System.out.println(method.getElementName()+" ---->");
			Collection<VMStatement> relevantStatements=result.get(method).getRelevantStatements();
			VMStatement[] s = relevantStatements.toArray(new VMStatement[] {});
			for (int i = 0; i < s.length; i++) {
				VMStatement curStmt = s[s.length - i - 1];
				System.out.print(curStmt.toString());
			}
		};
		System.out.println("Time used: "+(double)time/1000000);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		selectedMethod = ((IMethod) ((IStructuredSelection) selection)
				.getFirstElement());
	}

}
