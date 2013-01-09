package com.tp.utils;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.mockito.Mockito;

public class ShiroTestUtils {
	private static ThreadState threadState;

	public static void mockSubject(Object principal) {
		Subject subject = Mockito.mock(Subject.class);
		Mockito.when(subject.isAuthenticated()).thenReturn(true);
		Mockito.when(subject.getPrincipal()).thenReturn(principal);

		bindSubject(subject);
	}

	protected static void bindSubject(Subject subject) {
		clearSubject();
		threadState = new SubjectThreadState(subject);
		threadState.bind();
	}

	public static void clearSubject() {
		if (threadState != null) {
			threadState.clear();
			threadState = null;
		}
	}
}
