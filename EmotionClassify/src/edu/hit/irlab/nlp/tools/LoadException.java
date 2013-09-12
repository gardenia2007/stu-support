package edu.hit.irlab.nlp.tools;

/**
 * <code>LoadException</code> will be thrown until a exception in the
 * loadResource occurs.
 * 
 * @author Fandy Wang(lfwang@ir.hit.edu.cn)
 * @date 2010.04.22
 * @version 0.1
 */
public class LoadException extends Exception
{

	private static final long serialVersionUID = -2650473389897302692L;

	public LoadException(Throwable cause)
	{
		super(cause);
	}

	public LoadException(String message)
	{
		super(message);
	}
}