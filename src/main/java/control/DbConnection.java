package control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class DbConnection implements ServletContextListener{
	private static Logger logger = Logger.getAnonymousLogger();
	public void contextInitialized(ServletContextEvent event) {
		
		
		ServletContext context = event.getServletContext();
		DataSource ds = null;
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			ds = (DataSource) envCtx.lookup("jdbc/guitargrove");
		} catch(NamingException e) {
			logger.log(Level.WARNING, "Problema accesso DB!");
		}
		
		context.setAttribute("DataSource", ds);
	}
}