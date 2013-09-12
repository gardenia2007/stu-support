package edu.hit.irlab.util.ds.tree;

/**
 * excepion thrown if a duplicate key is inserted in a {@link RadixTree}
 * 
 * @author Tahseen Ur Rehman email: tahseen.ur.rehman {at.spam.me.not} gmail.com
 */
public class DuplicateKeyException extends RuntimeException {
	private static final long serialVersionUID = 3141795907493885706L;

	public DuplicateKeyException(String msg) {
		super(msg);
	}
}
