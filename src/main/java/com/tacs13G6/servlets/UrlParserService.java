package com.tacs13G6.servlets;

import java.util.Arrays;

/**
 * Permite obtener informacion sobre la confirmacion de la url.
 */
public class UrlParserService {

	/**
	  * Permite dividir la url y obtener los parametros/valores separados por '/'. Es decir,
	  * sieguiendo el siguiente patron: '/parametro1/>parametro2../parametroN'
	  *
	  * @param url La url de la cual se desea extraer los parametros.
	  * @param length Cantidad de parametros que se desean extraer de izquierda a derecha.
	  * @return String[] con los parametros de la url del largo especificado por 'length'.
	  */
	public static String[] parse(String url, int length)
	{
		if (url == null)
			url = "";
		String[] nodes = url.split("/(.*?)", length + 2);
		return  Arrays.copyOfRange(nodes, 1, 1 + length);
		
	}
}
