package com.hrr3.services;

import java.io.IOException;
import java.util.List;

public interface EmailService {
	
	public boolean initializeEmailService();
		
	/**
	 * Envio de email
	 * @param subject Asunto
	 * @param content Contenido despues de ser preparado por el email template
	 * @param to Para
	 * @return true/false
	 */
	public boolean sendEmail (String subject, String content, String to) throws IOException;
	
	/**
	 * Prepara el conteido final del email para el tipo de Alerta de Fondo (Subsidio/Garantia)
	 * @param fondo Subsidio y Garantia
	 * @param template Template a aplicar
	 * @return Email con el contenido final
	 */
}
