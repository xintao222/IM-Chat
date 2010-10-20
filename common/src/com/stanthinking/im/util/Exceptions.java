package com.stanthinking.im.util;

import com.google.common.base.Joiner;

/**
 *
 * @author Stanislav Peshterliev
 */
public class Exceptions {
	public static String getStackTrace(Exception e) {
		return Joiner.on("\n").join(e.getStackTrace());
	}
}
