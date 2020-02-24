package com.bolsadeideas.springboot.app.auth.handler;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SessionFlashMapManager flashManager = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();

		// Como es override, no podemos utilizar nuevas entradas, por lo que locale aqui
		// se hace de forma diferente
		Locale locale = localeResolver.resolveLocale(request);
		String mensaje = messageSource.getMessage("text.login.bienvenido", null, locale)
				+ authentication.getName() + "!, " + messageSource.getMessage("text.login.bienvenido.mensaje", null, locale);
		
		flashMap.put("success", mensaje );

		flashManager.saveOutputFlashMap(flashMap, request, response);

		if (authentication != null) {
			logger.info("El usuario '" + authentication.getName() + "' ha iniciado sesión con éxito!");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
