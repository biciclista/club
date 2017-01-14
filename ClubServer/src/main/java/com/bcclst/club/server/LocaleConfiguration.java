package com.bcclst.club.server;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.bcclst.club.server.util.LocaleMessage;
import com.bcclst.club.server.util.LocaleMessageImpl;

/**
 * Spring configuration for localization. Localization is made using session
 * resolver looking for param "lang".
 * 
 * @author Nacho
 */
@Configuration
public class LocaleConfiguration extends WebMvcConfigurerAdapter {

	/**
	 * Provides the message source bean for localizing messages.
	 * 
	 * @return The MessageSource bean.
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasenames("classpath:i18n/messages");
		source.setFallbackToSystemLocale(false);
		source.setDefaultEncoding("UTF-8");

		return source;
	}

	/**
	 * Provides the locale resolver bean. Uses SessionLocaleResolver with "es"
	 * locale default.
	 * 
	 * @return The LocaleResolver bean.
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		//sessionLocaleResolver.setDefaultLocale(Locale.forLanguageTag("es"));

		return sessionLocaleResolver;
	}

	/**
	 * Provides the bean for intercept locale changes. Looks for param "lang".
	 * 
	 * @return The LocaleChangeInterceptor bean.
	 */
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");

		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * Gets the bean for retrieving localized messages.
	 * 
	 * @return The {@link LocaleMessage} bean.
	 */
	@Bean
	public LocaleMessage localeMessage() {
		return new LocaleMessageImpl(this.messageSource());
	}
	
}
