package fr.byob.client.chat.util;

/**
 * Base class for exceptions thrown by the chat service method.
 */
public class ChatServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ChatServiceException() {
	}

	public ChatServiceException(String string) {
		super(string);
	}
}
