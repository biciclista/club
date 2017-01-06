package com.bcclst.club.server.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleMessageImpl implements LocaleMessage {

	private static final Logger logger = LoggerFactory.getLogger(LocaleMessage.class);

	private MessageSource messageSource;

	public LocaleMessageImpl(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public String getMessage(String code, Object[] params) {
		final Locale locale = LocaleContextHolder.getLocale();

		logger.debug("Locale: {}", locale);

		return messageSource.getMessage(code, params, locale);
	}

	@Override
	public String getMessage(String code) {
		return getMessage(code, null);
	}

}
