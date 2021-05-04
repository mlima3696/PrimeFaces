package br.com.framework.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import br.com.framework.implementacao.crud.VariavelConexaoUtil;

//Responsavel por estabelecer conexao com Hibernate

@ApplicationScoped // E para toda aplicacao ou usuarios
public class HibernateUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static String  JAVA_COMP_ENV_JDBC_DATA_SOURCE="java:/comp/env/datasource";
	
	private static SessionFactory sessionFactory = buildSessionFactory();
	
	//Responsavel por ler o arquivo de configuracao hibernate.cfg.xml
	//@return SessionFactory
	
	private static SessionFactory buildSessionFactory() {
		try {
			if(sessionFactory == null) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			return sessionFactory;
		} catch (Exception e) {
			throw new ExceptionInInitializerError("Erro ao criar conexao SessionFactory");
		}
	}
	
	// Retorna o sessionFactory corrente
	// @return SessionFacctory
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	// Retorna a sessao do SessionFactory
	// @return Session
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
	
	// Abre uma nova sessao no SessionFactory
	// Return Session
	
	public static Session openSession() {
		if(sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory.openSession();
	}
	// Obtem a conection do provedor de conexoes configurado
	// @return Connection SQL
	// Connection do pacote java.sql
	// throws  SQLException java.sql
	// SessionFactoryImplementor org.hibernate.engine
	// getConnectionProvider():Conection-HibernateUtil
	// getConnection():Connection-ConnectionProvider
	
	public static Connection getConnectionProvider () throws SQLException{
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}
	// @return Connection no InitialContext java:/comp/env/jdbc/datasource
	// @throws Exception
	// Connection java.sql
	// Execption  java.lang
	// InitialContext javax.naming
	// InitialContext javax.naming.InitialContext
	// DataSource javax.sql
	// lookup(Name name): Object-InitialContext
	// getConnection():Connection-DataSource
	
	 public static Connection getConnection() throws Exception{
		 InitialContext context =  new InitialContext();
		 DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		 
		 return ds.getConnection();
	 }
	 
	 // @return DataSource JNDI TomCat
	 // @throws NamingException
	 // DataSource-javax.sql
	 //NamingException-javax.naming
	 // InitialContext-javax.naming
	 //initialContext()-javax.naming.initialContext
	 // DataSource-javax.sql
	 // lookuo(Name name):Object-InitialCOntext
	
	 public DataSource getDataSourceJndi () throws NamingException{
		 InitialContext context = new InitialContext();
		 return (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
	 }
}
