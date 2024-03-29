/**
 * 
 */
package org.junit.internal.runners.model;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class EachTestNotifier {
	private final RunNotifier fNotifier;

	private final Description fDescription;

	public EachTestNotifier(RunNotifier notifier, Description description) {
		fNotifier= notifier;
		fDescription= description;
	}

	public void addFailure(Throwable targetException) {
		if (targetException instanceof MultipleFailureException) {
			addMultipleFailureException((MultipleFailureException) targetException);
		} else {
			fNotifier
					.fireTestFailure(new Failure(fDescription, targetException));
		}
	}

	private void addMultipleFailureException(MultipleFailureException mfe) {
		for (Throwable each : mfe.getFailures())
			addFailure(each);
	}

	public void fireTestFinished() {
		fNotifier.fireTestFinished(fDescription);
	}

	public void fireTestStarted() {
		fNotifier.fireTestStarted(fDescription);
	}

	public void fireTestIgnored() {
		fNotifier.fireTestIgnored(fDescription);
	}
}