package com.bcclst.club.webclient.util;

import org.springframework.context.MessageSourceResolvable;

/**
 * Interface to look for localized messages.
 * 
 * @author Nacho
 */
public interface LocaleMessage {
	/**
	 * Retrieves a localized message formatted with parameters.
	 * 
	 * @param id Identifier of message to look for.
	 * @param params Parameters to format the message.
	 * @return The localized formatted message.
	 */
	public String getMessage(String id, Object[] params);

	/**
	 * Retrieves a localized message without parameters.
	 * 
	 * @param id Identifier of message to look for.
	 * @return The localized message.
	 */
	public String getMessage(String id);
	
	String getMessage(MessageSourceResolvable resolvable);
}
