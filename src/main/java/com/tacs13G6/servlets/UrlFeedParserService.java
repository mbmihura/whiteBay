package com.tacs13G6.servlets;

/**
 * Extrae el usuario, feed y torrent de una url (string) siguiendo el formato "/usuario/feed/torrent".
 */
public class UrlFeedParserService {
	String user;
	String feedTitle;
	String torrent;
	
	/**
	  * Crea un objeto al cual se le pueden consular el usuario, feed y torrent extraidos de la 
	  * url (string)". 
	  *
	  * @param url La url de la cual se desea extraer el usuario, feed y torrent.
	  */
	public UrlFeedParserService(String url)
	{
		if (url != null)
        {
        	String[] nodes = url.split("/(.*?)",5);
        	switch (nodes.length){
        	case 5:
        	case 4:
        		torrent = nodes[3];
        	case 3:
        		feedTitle = nodes[2];
        	case 2:
        		user = nodes[1];
        		break;
        	default:
        		// TODO: Url is not starting with a"/". See if it isn't requiered somewhere or in anoter situacion.
        	}
        }
	}
	/**
	  * Indica que el usuario no es nulo, ni que esta vacio.
	  */
	public boolean hasUser()
	{
	    return user != null && !user.isEmpty();
	}
	/**
	  * Extrae el usuario de la url. Notese que puede devolver tanto nulo con un string vacio.: 
	  * Nulo cuando la url con la que se crea el objeto es nula y string vacio cuando solamente contiene la raiz "/".
	  */
	public String getUser()
	{
	    return user;
	}
	/**
	  * Extrae el feed de la url. Notese que puede devolver tanto nulo con un string vacio.: 
	  * Nulo cuando la url es ".../usuario" y string vacio cuando es ".../usuario/".
	  */
	public String getFeed()
	{
	    return feedTitle;
	}
	/**
	  * Indica que el feed no es nulo, ni que esta vacio.
	  */
	public boolean hasFeed()
	{
	    return feedTitle != null && !feedTitle.isEmpty();
	}
	/**
	  * Extrae el torrent de la url. Notese que puede devolver tanto nulo con un string vacio.: 
	  * Nulo cuando la url es ".../usuario/feed" y string vacio cuando es ".../usuario/feed/".
	  */
	public String getTorrent()
	{
	    return torrent;
	}
	/**
	  * Indica que el torrent no es nulo, ni que esta vacio.
	  */
	public boolean hasTorrent() {
		return torrent != null && !torrent.isEmpty();
	}
}
