// Description: Java 11 MySQL Jdbc DbIO implementation for CFSec.

/*
 *	server.markhome.msscf.CFSec
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfsec.CFSecMySql;

import java.lang.reflect.*;
import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfsec.CFSecObj.*;
import server.markhome.msscf.cfsec.CFSecSaxLoader.CFSecSaxLoader;

public class CFSecMySqlSchema
	extends CFSecSchema
	implements ICFSecSchema
{
	protected Connection cnx;
	protected boolean inTransaction;
	protected PreparedStatement stmtSelectIsSystemUser = null;
	protected PreparedStatement stmtSelectIsClusterUser = null;
	protected PreparedStatement stmtSelectIsTenantUser = null;
	protected PreparedStatement stmtSelectNextISOCcyIdGen = null;
	protected PreparedStatement stmtInsertNextISOCcyIdGen = null;
	protected PreparedStatement stmtUpdateNextISOCcyIdGen = null;
	protected PreparedStatement stmtSelectNextISOCtryIdGen = null;
	protected PreparedStatement stmtInsertNextISOCtryIdGen = null;
	protected PreparedStatement stmtUpdateNextISOCtryIdGen = null;
	protected PreparedStatement stmtSelectNextISOLangIdGen = null;
	protected PreparedStatement stmtInsertNextISOLangIdGen = null;
	protected PreparedStatement stmtUpdateNextISOLangIdGen = null;
	protected PreparedStatement stmtSelectNextISOTZoneIdGen = null;
	protected PreparedStatement stmtInsertNextISOTZoneIdGen = null;
	protected PreparedStatement stmtUpdateNextISOTZoneIdGen = null;
	protected PreparedStatement stmtSelectNextServiceTypeIdGen = null;
	protected PreparedStatement stmtInsertNextServiceTypeIdGen = null;
	protected PreparedStatement stmtUpdateNextServiceTypeIdGen = null;
	protected PreparedStatement stmtSelectNextClusterIdGen = null;
	protected PreparedStatement stmtInsertNextClusterIdGen = null;
	protected PreparedStatement stmtUpdateNextClusterIdGen = null;
	protected PreparedStatement stmtSelectNextTenantIdGen = null;
	protected PreparedStatement stmtInsertNextTenantIdGen = null;
	protected PreparedStatement stmtUpdateNextTenantIdGen = null;

	public CFSecMySqlSchema() {
		super();
		cnx = null;
		inTransaction = false;
		tableCluster = new CFSecMySqlClusterTable( this );
		tableHostNode = new CFSecMySqlHostNodeTable( this );
		tableISOCcy = new CFSecMySqlISOCcyTable( this );
		tableISOCtry = new CFSecMySqlISOCtryTable( this );
		tableISOCtryCcy = new CFSecMySqlISOCtryCcyTable( this );
		tableISOCtryLang = new CFSecMySqlISOCtryLangTable( this );
		tableISOLang = new CFSecMySqlISOLangTable( this );
		tableISOTZone = new CFSecMySqlISOTZoneTable( this );
		tableSecApp = new CFSecMySqlSecAppTable( this );
		tableSecDevice = new CFSecMySqlSecDeviceTable( this );
		tableSecForm = new CFSecMySqlSecFormTable( this );
		tableSecGroup = new CFSecMySqlSecGroupTable( this );
		tableSecGroupForm = new CFSecMySqlSecGroupFormTable( this );
		tableSecGrpInc = new CFSecMySqlSecGrpIncTable( this );
		tableSecGrpMemb = new CFSecMySqlSecGrpMembTable( this );
		tableSecSession = new CFSecMySqlSecSessionTable( this );
		tableSecUser = new CFSecMySqlSecUserTable( this );
		tableService = new CFSecMySqlServiceTable( this );
		tableServiceType = new CFSecMySqlServiceTypeTable( this );
		tableSysCluster = new CFSecMySqlSysClusterTable( this );
		tableTSecGroup = new CFSecMySqlTSecGroupTable( this );
		tableTSecGrpInc = new CFSecMySqlTSecGrpIncTable( this );
		tableTSecGrpMemb = new CFSecMySqlTSecGrpMembTable( this );
		tableTenant = new CFSecMySqlTenantTable( this );
	}

	public CFSecMySqlSchema( CFSecConfigurationFile conf ) {
		super( conf );
		cnx = null;
		inTransaction = false;
		tableCluster = new CFSecMySqlClusterTable( this );
		tableHostNode = new CFSecMySqlHostNodeTable( this );
		tableISOCcy = new CFSecMySqlISOCcyTable( this );
		tableISOCtry = new CFSecMySqlISOCtryTable( this );
		tableISOCtryCcy = new CFSecMySqlISOCtryCcyTable( this );
		tableISOCtryLang = new CFSecMySqlISOCtryLangTable( this );
		tableISOLang = new CFSecMySqlISOLangTable( this );
		tableISOTZone = new CFSecMySqlISOTZoneTable( this );
		tableSecApp = new CFSecMySqlSecAppTable( this );
		tableSecDevice = new CFSecMySqlSecDeviceTable( this );
		tableSecForm = new CFSecMySqlSecFormTable( this );
		tableSecGroup = new CFSecMySqlSecGroupTable( this );
		tableSecGroupForm = new CFSecMySqlSecGroupFormTable( this );
		tableSecGrpInc = new CFSecMySqlSecGrpIncTable( this );
		tableSecGrpMemb = new CFSecMySqlSecGrpMembTable( this );
		tableSecSession = new CFSecMySqlSecSessionTable( this );
		tableSecUser = new CFSecMySqlSecUserTable( this );
		tableService = new CFSecMySqlServiceTable( this );
		tableServiceType = new CFSecMySqlServiceTypeTable( this );
		tableSysCluster = new CFSecMySqlSysClusterTable( this );
		tableTSecGroup = new CFSecMySqlTSecGroupTable( this );
		tableTSecGrpInc = new CFSecMySqlTSecGrpIncTable( this );
		tableTSecGrpMemb = new CFSecMySqlTSecGrpMembTable( this );
		tableTenant = new CFSecMySqlTenantTable( this );
		setDbSchemaName( conf.getDbDatabase() );
	}

	public CFSecMySqlSchema( String argJndiName ) {
		super( argJndiName );
		cnx = null;
		inTransaction = false;
		tableCluster = new CFSecMySqlClusterTable( this );
		tableHostNode = new CFSecMySqlHostNodeTable( this );
		tableISOCcy = new CFSecMySqlISOCcyTable( this );
		tableISOCtry = new CFSecMySqlISOCtryTable( this );
		tableISOCtryCcy = new CFSecMySqlISOCtryCcyTable( this );
		tableISOCtryLang = new CFSecMySqlISOCtryLangTable( this );
		tableISOLang = new CFSecMySqlISOLangTable( this );
		tableISOTZone = new CFSecMySqlISOTZoneTable( this );
		tableSecApp = new CFSecMySqlSecAppTable( this );
		tableSecDevice = new CFSecMySqlSecDeviceTable( this );
		tableSecForm = new CFSecMySqlSecFormTable( this );
		tableSecGroup = new CFSecMySqlSecGroupTable( this );
		tableSecGroupForm = new CFSecMySqlSecGroupFormTable( this );
		tableSecGrpInc = new CFSecMySqlSecGrpIncTable( this );
		tableSecGrpMemb = new CFSecMySqlSecGrpMembTable( this );
		tableSecSession = new CFSecMySqlSecSessionTable( this );
		tableSecUser = new CFSecMySqlSecUserTable( this );
		tableService = new CFSecMySqlServiceTable( this );
		tableServiceType = new CFSecMySqlServiceTypeTable( this );
		tableSysCluster = new CFSecMySqlSysClusterTable( this );
		tableTSecGroup = new CFSecMySqlTSecGroupTable( this );
		tableTSecGrpInc = new CFSecMySqlTSecGrpIncTable( this );
		tableTSecGrpMemb = new CFSecMySqlTSecGrpMembTable( this );
		tableTenant = new CFSecMySqlTenantTable( this );
	}

	public CFSecMySqlSchema( Connection argCnx ) {
		super();
		cnx = argCnx;
		inTransaction = false;
		tableCluster = new CFSecMySqlClusterTable( this );
		tableHostNode = new CFSecMySqlHostNodeTable( this );
		tableISOCcy = new CFSecMySqlISOCcyTable( this );
		tableISOCtry = new CFSecMySqlISOCtryTable( this );
		tableISOCtryCcy = new CFSecMySqlISOCtryCcyTable( this );
		tableISOCtryLang = new CFSecMySqlISOCtryLangTable( this );
		tableISOLang = new CFSecMySqlISOLangTable( this );
		tableISOTZone = new CFSecMySqlISOTZoneTable( this );
		tableSecApp = new CFSecMySqlSecAppTable( this );
		tableSecDevice = new CFSecMySqlSecDeviceTable( this );
		tableSecForm = new CFSecMySqlSecFormTable( this );
		tableSecGroup = new CFSecMySqlSecGroupTable( this );
		tableSecGroupForm = new CFSecMySqlSecGroupFormTable( this );
		tableSecGrpInc = new CFSecMySqlSecGrpIncTable( this );
		tableSecGrpMemb = new CFSecMySqlSecGrpMembTable( this );
		tableSecSession = new CFSecMySqlSecSessionTable( this );
		tableSecUser = new CFSecMySqlSecUserTable( this );
		tableService = new CFSecMySqlServiceTable( this );
		tableServiceType = new CFSecMySqlServiceTypeTable( this );
		tableSysCluster = new CFSecMySqlSysClusterTable( this );
		tableTSecGroup = new CFSecMySqlTSecGroupTable( this );
		tableTSecGrpInc = new CFSecMySqlTSecGrpIncTable( this );
		tableTSecGrpMemb = new CFSecMySqlTSecGrpMembTable( this );
		tableTenant = new CFSecMySqlTenantTable( this );
		try {
			cnx.setAutoCommit( false );
			cnx.setTransactionIsolation( Connection.TRANSACTION_REPEATABLE_READ );
			cnx.rollback();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				"CFSecMySqlSchema-constructor",
				e );
		}
	}

	public Connection getCnx() {
		return( cnx );
	}

	public boolean isConnected() {
		final String S_ProcName = "isConnected";
		boolean retval;
		if( cnx == null ) {
			retval = false;
		}
		else {
			try {
				if( cnx.isClosed() ) {
					retval = false;
					cnx = null;
					releasePreparedStatements();
				}
				else {
					retval = true;
				}
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
		}
		return( retval );
	}

	public boolean connect() {
		final String S_ProcName = "connect";
		if( cnx != null ) {
			return( false );
		}

		if( configuration != null ) {
			try {
				Class.forName( "com.mysql.jdbc.Driver" );
			}
			catch( ClassNotFoundException e ) {
				throw new CFLibRuntimeException( getClass(),
					"connect",
					"Could not load MySql JDBC driver",
					e );
			}
			String dbServer = configuration.getDbServer();
			int dbPort = configuration.getDbPort();
			String dbDatabase = configuration.getDbDatabase();
			String dbUserName = configuration.getDbUserName();
			String dbPassword = configuration.getDbPassword();
			String url =
					"jdbc:mysql://" + dbServer
				+	":" + Integer.toString( dbPort )
				+	"/" + dbDatabase;
			Properties props = new Properties();
			props.setProperty( "user", dbUserName );
			props.setProperty( "password", dbPassword );
			try {
				cnx = DriverManager.getConnection( url, props );
				cnx.setAutoCommit( false );
				cnx.setTransactionIsolation( Connection.TRANSACTION_REPEATABLE_READ );
				cnx.rollback();
				setDbSchemaName( dbDatabase );
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			return( true );
		}
		if( jndiName != null ) {
			try {
				Context ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup( jndiName );
				if( ds == null ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Could not get resolve DataSource \"" + jndiName + "\"" );
				}
				cnx = ds.getConnection();
				if( cnx == null ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Could not get Connection from PooledConnection for ConnectionPoolDataSource \"" + jndiName + "\"" );
				}
				cnx.setAutoCommit( false );
				cnx.setTransactionIsolation( Connection.TRANSACTION_REPEATABLE_READ );
				cnx.rollback();
			}
			catch( NamingException e ) {
				cnx = null;
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"NamingException " + e.getMessage(),
					e );
			}
			catch( SQLException e ) {
				cnx = null;
				inTransaction = false;
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			return( true );
		}
		throw new CFLibUsageException( getClass(),
			S_ProcName,
			"Neither configurationFile nor jndiName found, do not know how to connect to database" );
	}

	public boolean connect( String username, String password ) {
		final String S_ProcName = "connect-userpw";
		if( cnx != null ) {
			return( false );
		}
		if( ( username == null ) || ( username.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"username" );
		}
		if( password == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"password" );
		}

		if( configuration != null ) {
			try {
				Class.forName( "com.mysql.jdbc.Driver" );
			}
			catch( ClassNotFoundException e ) {
				throw new CFLibRuntimeException( getClass(),
					"connect",
					"Could not load MySql JDBC driver",
					e );
			}
			String dbServer = configuration.getDbServer();
			int dbPort = configuration.getDbPort();
			String dbDatabase = configuration.getDbDatabase();
			String dbUserName = username;
			String dbPassword = password;
			String url =
					"jdbc:mysql://" + dbServer
				+	":" + Integer.toString( dbPort )
				+	"/" + dbDatabase;
			Properties props = new Properties();
			props.setProperty( "user", dbUserName );
			props.setProperty( "password", dbPassword );
			try {
				cnx = DriverManager.getConnection( url, props );
				cnx.setAutoCommit( false );
				cnx.setTransactionIsolation( Connection.TRANSACTION_REPEATABLE_READ );
				cnx.rollback();
				setDbSchemaName( dbDatabase );
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			return( true );
		}
		throw new CFLibUsageException( getClass(),
			S_ProcName,
			"configurationFile not found, do not know how to connect to database" );
	}

	public void disconnect( boolean doCommit ) {
		final String S_ProcName = "disconnect";
		if( cnx != null ) {
			try {
				if( ! cnx.isClosed() ) {
					if( doCommit ) {
						cnx.commit();
					}
					else {
						cnx.rollback();
					}
					cnx.close();
				}
			}
			catch( SQLException e ) {
				throw new CFLibDbException( getClass(),
					S_ProcName,
					e );
			}
			finally {
				cnx = null;
			}
		}
		releasePreparedStatements();
	}

	public boolean isTransactionOpen() {
		return( inTransaction );
	}

	public boolean beginTransaction() {
		if( inTransaction ) {
			return( false );
		}
//		try {
//			String sql =
//					"begin transaction";
//			Statement stmt = cnx.createStatement( ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
//			stmt.execute( sql );
			inTransaction = true;
//		}
//		catch( SQLException e ) {
//			throw new CFLibDbException( getClass(),
//				"beginTransaction",
//				e );
//		}
		return( inTransaction );
	}

	public void commit() {
		try {
			cnx.commit();
			inTransaction = false;
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				"commit",
				e );
		}
	}

	public void rollback() {
		try {
			if( cnx != null ) {
				cnx.rollback();
			}
			inTransaction = false;
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				"rollback",
				e );
		}
	}

	public boolean isSystemUser( CFSecAuthorization Authorization) {
		if( ! inTransaction ) {
			throw new CFLibUsageException( getClass(),
				"isSystemUser",
				"Not in a transaction" );
		}
		ResultSet resultSet = null;
		try {
			String sql = "SELECT " + getLowerDbSchemaName() + ".sp_is_system_user( ? ) as IsSystemUser";
			if( stmtSelectIsSystemUser == null ) {
				stmtSelectIsSystemUser = cnx.prepareStatement( sql );
			}
			stmtSelectIsSystemUser.setString( 1, Authorization.getSecUserId().toString() );
			resultSet = stmtSelectIsSystemUser.executeQuery();
			boolean resultFlag;
			if( resultSet.next() ) {
				resultFlag = resultSet.getBoolean( 1 );
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					"isSystemUser",
					"Query of sp_is_system_user() did not return a result row" );
			}
			resultSet.close();
			return( resultFlag );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				"isSystemUser",
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public boolean isClusterUser( CFSecAuthorization Authorization,
		long clusterId,
		String groupName )
	{
		if( ! inTransaction ) {
			throw new CFLibUsageException( getClass(),
				"isClusterUser",
				"Not in a transaction" );
		}
		ResultSet resultSet = null;
		try {
			String sql = "SELECT " + getLowerDbSchemaName() + ".sp_is_cluster_user( ?, ?, ? ) as IsClusterUser";
			if( stmtSelectIsClusterUser == null ) {
				stmtSelectIsClusterUser = cnx.prepareStatement( sql );
			}
			stmtSelectIsClusterUser.setLong( 1, clusterId );
			stmtSelectIsClusterUser.setString( 2, groupName );
			stmtSelectIsClusterUser.setString( 3, Authorization.getSecUserId().toString() );
			resultSet = stmtSelectIsClusterUser.executeQuery();
			boolean resultFlag;
			if( resultSet.next() ) {
				resultFlag = resultSet.getBoolean( 1 );
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					"isClusterUser",
					"Query of sp_is_cluster_user() did not return a result row" );
			}
			resultSet.close();
			return( resultFlag );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				"isClusterUser",
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public boolean isTenantUser( CFSecAuthorization Authorization,
		long tenantId,
		String groupName )
	{
		if( ! inTransaction ) {
			throw new CFLibUsageException( getClass(),
				"isTenantUser",
				"Not in a transaction" );
		}
		ResultSet resultSet = null;
		try {
			String sql = "SELECT " + getLowerDbSchemaName() + ".sp_is_tenant_user( ?, ?, ? ) as IsTenantUser";
			if( stmtSelectIsTenantUser == null ) {
				stmtSelectIsTenantUser = cnx.prepareStatement( sql );
			}
			stmtSelectIsTenantUser.setLong( 1, tenantId );
			stmtSelectIsTenantUser.setString( 2, groupName );
			stmtSelectIsTenantUser.setString( 3, Authorization.getSecUserId().toString() );
			resultSet = stmtSelectIsTenantUser.executeQuery();
			boolean resultFlag;
			if( resultSet.next() ) {
				resultFlag = resultSet.getBoolean( 1 );
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					"isTenantUser",
					"Query of sp_is_tenant_user() did not return a result row" );
			}
			resultSet.close();
			return( resultFlag );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				"isTenantUser",
				e );
		}
		finally {
			if( resultSet != null ) {
				try {
					resultSet.close();
				}
				catch( SQLException e ) {
				}
				resultSet = null;
			}
		}
	}

	public void releasePreparedStatements() {
		if( stmtSelectIsSystemUser != null ) {
			try {
				stmtSelectIsSystemUser.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectIsSystemUser = null;
		}
		if( stmtSelectIsClusterUser != null ) {
			try {
				stmtSelectIsClusterUser.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectIsClusterUser = null;
		}
		if( stmtSelectIsTenantUser != null ) {
			try {
				stmtSelectIsTenantUser.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectIsTenantUser = null;
		}
		if( stmtSelectNextISOCcyIdGen != null ) {
			try {
				stmtSelectNextISOCcyIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextISOCcyIdGen= null;
		}
		if( stmtInsertNextISOCcyIdGen != null ) {
			try {
				stmtInsertNextISOCcyIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextISOCcyIdGen= null;
		}
		if( stmtUpdateNextISOCcyIdGen != null ) {
			try {
				stmtUpdateNextISOCcyIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextISOCcyIdGen= null;
		}
		if( stmtSelectNextISOCtryIdGen != null ) {
			try {
				stmtSelectNextISOCtryIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextISOCtryIdGen= null;
		}
		if( stmtInsertNextISOCtryIdGen != null ) {
			try {
				stmtInsertNextISOCtryIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextISOCtryIdGen= null;
		}
		if( stmtUpdateNextISOCtryIdGen != null ) {
			try {
				stmtUpdateNextISOCtryIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextISOCtryIdGen= null;
		}
		if( stmtSelectNextISOLangIdGen != null ) {
			try {
				stmtSelectNextISOLangIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextISOLangIdGen= null;
		}
		if( stmtInsertNextISOLangIdGen != null ) {
			try {
				stmtInsertNextISOLangIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextISOLangIdGen= null;
		}
		if( stmtUpdateNextISOLangIdGen != null ) {
			try {
				stmtUpdateNextISOLangIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextISOLangIdGen= null;
		}
		if( stmtSelectNextISOTZoneIdGen != null ) {
			try {
				stmtSelectNextISOTZoneIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextISOTZoneIdGen= null;
		}
		if( stmtInsertNextISOTZoneIdGen != null ) {
			try {
				stmtInsertNextISOTZoneIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextISOTZoneIdGen= null;
		}
		if( stmtUpdateNextISOTZoneIdGen != null ) {
			try {
				stmtUpdateNextISOTZoneIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextISOTZoneIdGen= null;
		}
		if( stmtSelectNextServiceTypeIdGen != null ) {
			try {
				stmtSelectNextServiceTypeIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextServiceTypeIdGen= null;
		}
		if( stmtInsertNextServiceTypeIdGen != null ) {
			try {
				stmtInsertNextServiceTypeIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextServiceTypeIdGen= null;
		}
		if( stmtUpdateNextServiceTypeIdGen != null ) {
			try {
				stmtUpdateNextServiceTypeIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextServiceTypeIdGen= null;
		}
		if( stmtSelectNextClusterIdGen != null ) {
			try {
				stmtSelectNextClusterIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextClusterIdGen= null;
		}
		if( stmtInsertNextClusterIdGen != null ) {
			try {
				stmtInsertNextClusterIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextClusterIdGen= null;
		}
		if( stmtUpdateNextClusterIdGen != null ) {
			try {
				stmtUpdateNextClusterIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextClusterIdGen= null;
		}
		if( stmtSelectNextTenantIdGen != null ) {
			try {
				stmtSelectNextTenantIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtSelectNextTenantIdGen= null;
		}
		if( stmtInsertNextTenantIdGen != null ) {
			try {
				stmtInsertNextTenantIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtInsertNextTenantIdGen= null;
		}
		if( stmtUpdateNextTenantIdGen != null ) {
			try {
				stmtUpdateNextTenantIdGen.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateNextTenantIdGen= null;
		}
		if( tableCluster != null ) {
			((CFSecMySqlClusterTable)tableCluster).releasePreparedStatements();
		}
		if( tableHostNode != null ) {
			((CFSecMySqlHostNodeTable)tableHostNode).releasePreparedStatements();
		}
		if( tableISOCcy != null ) {
			((CFSecMySqlISOCcyTable)tableISOCcy).releasePreparedStatements();
		}
		if( tableISOCtry != null ) {
			((CFSecMySqlISOCtryTable)tableISOCtry).releasePreparedStatements();
		}
		if( tableISOCtryCcy != null ) {
			((CFSecMySqlISOCtryCcyTable)tableISOCtryCcy).releasePreparedStatements();
		}
		if( tableISOCtryLang != null ) {
			((CFSecMySqlISOCtryLangTable)tableISOCtryLang).releasePreparedStatements();
		}
		if( tableISOLang != null ) {
			((CFSecMySqlISOLangTable)tableISOLang).releasePreparedStatements();
		}
		if( tableISOTZone != null ) {
			((CFSecMySqlISOTZoneTable)tableISOTZone).releasePreparedStatements();
		}
		if( tableSecApp != null ) {
			((CFSecMySqlSecAppTable)tableSecApp).releasePreparedStatements();
		}
		if( tableSecDevice != null ) {
			((CFSecMySqlSecDeviceTable)tableSecDevice).releasePreparedStatements();
		}
		if( tableSecForm != null ) {
			((CFSecMySqlSecFormTable)tableSecForm).releasePreparedStatements();
		}
		if( tableSecGroup != null ) {
			((CFSecMySqlSecGroupTable)tableSecGroup).releasePreparedStatements();
		}
		if( tableSecGroupForm != null ) {
			((CFSecMySqlSecGroupFormTable)tableSecGroupForm).releasePreparedStatements();
		}
		if( tableSecGrpInc != null ) {
			((CFSecMySqlSecGrpIncTable)tableSecGrpInc).releasePreparedStatements();
		}
		if( tableSecGrpMemb != null ) {
			((CFSecMySqlSecGrpMembTable)tableSecGrpMemb).releasePreparedStatements();
		}
		if( tableSecSession != null ) {
			((CFSecMySqlSecSessionTable)tableSecSession).releasePreparedStatements();
		}
		if( tableSecUser != null ) {
			((CFSecMySqlSecUserTable)tableSecUser).releasePreparedStatements();
		}
		if( tableService != null ) {
			((CFSecMySqlServiceTable)tableService).releasePreparedStatements();
		}
		if( tableServiceType != null ) {
			((CFSecMySqlServiceTypeTable)tableServiceType).releasePreparedStatements();
		}
		if( tableSysCluster != null ) {
			((CFSecMySqlSysClusterTable)tableSysCluster).releasePreparedStatements();
		}
		if( tableTSecGroup != null ) {
			((CFSecMySqlTSecGroupTable)tableTSecGroup).releasePreparedStatements();
		}
		if( tableTSecGrpInc != null ) {
			((CFSecMySqlTSecGrpIncTable)tableTSecGrpInc).releasePreparedStatements();
		}
		if( tableTSecGrpMemb != null ) {
			((CFSecMySqlTSecGrpMembTable)tableTSecGrpMemb).releasePreparedStatements();
		}
		if( tableTenant != null ) {
			((CFSecMySqlTenantTable)tableTenant).releasePreparedStatements();
		}
	}

	public short nextISOCcyIdGen() {
		final String S_ProcName = "nextISOCcyIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_isoccyidgen() as NextISOCcyIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextISOCcyIdGen == null ) {
				stmtSelectNextISOCcyIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			short nextId;
			rsSelect = stmtSelectNextISOCcyIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getShort( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_isoccyidgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public short nextISOCtryIdGen() {
		final String S_ProcName = "nextISOCtryIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_isoctryidgen() as NextISOCtryIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextISOCtryIdGen == null ) {
				stmtSelectNextISOCtryIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			short nextId;
			rsSelect = stmtSelectNextISOCtryIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getShort( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_isoctryidgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public short nextISOLangIdGen() {
		final String S_ProcName = "nextISOLangIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_isolangidgen() as NextISOLangIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextISOLangIdGen == null ) {
				stmtSelectNextISOLangIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			short nextId;
			rsSelect = stmtSelectNextISOLangIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getShort( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_isolangidgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public short nextISOTZoneIdGen() {
		final String S_ProcName = "nextISOTZoneIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_isotzoneidgen() as NextISOTZoneIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextISOTZoneIdGen == null ) {
				stmtSelectNextISOTZoneIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			short nextId;
			rsSelect = stmtSelectNextISOTZoneIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getShort( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_isotzoneidgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public int nextServiceTypeIdGen() {
		final String S_ProcName = "nextServiceTypeIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_servicetypeidgen() as NextServiceTypeIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextServiceTypeIdGen == null ) {
				stmtSelectNextServiceTypeIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			int nextId;
			rsSelect = stmtSelectNextServiceTypeIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getInt( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_servicetypeidgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public long nextClusterIdGen() {
		final String S_ProcName = "nextClusterIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_clusteridgen() as NextClusterIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextClusterIdGen == null ) {
				stmtSelectNextClusterIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			long nextId;
			rsSelect = stmtSelectNextClusterIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getLong( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_clusteridgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public long nextTenantIdGen() {
		final String S_ProcName = "nextTenantIdGen";
		String sqlSelectNextIdGen =
				"SELECT " + getLowerDbSchemaName() + ".sp_next_tenantidgen() as NextTenantIdGen;";
		if( ! isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet rsSelect = null;
		try {
			Connection cnx = getCnx();
			if( stmtSelectNextTenantIdGen == null ) {
				stmtSelectNextTenantIdGen = cnx.prepareStatement( sqlSelectNextIdGen );
			}
			long nextId;
			rsSelect = stmtSelectNextTenantIdGen.executeQuery();
			if( rsSelect.next() ) {
				nextId = rsSelect.getLong( 1 );
				if( rsSelect.wasNull() ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"next_id cannot be null!" );
				}
				if( rsSelect.next() ) {
					rsSelect.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-buff select response, " + rsSelect.getRow() + " rows selected" );
				}
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected 1 row to be returned by invoking " + getLowerDbSchemaName() + ".sp_next_tenantidgen()" );
			}
			return( nextId );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
				e );
		}
		finally {
			if( rsSelect != null ) {
				try {
					rsSelect.close();
				}
				catch( SQLException e ) {
				}
				rsSelect = null;
			}
		}
	}

	public UUID nextSecSessionIdGen() {
		UUID retval = UUID.randomUUID();
		return( retval );
	}

	public UUID nextSecUserIdGen() {
		UUID retval = UUID.randomUUID();
		return( retval );
	}

	/**
	 *	Import the contents of the specified file name
	 *	and file contents by applying a SAX Loader parse.
	 */
	public String fileImport( CFSecAuthorization auth, String fileName, String fileContent ) {
		if( isTransactionOpen() ) {
			rollback();
		}

		try {
			beginTransaction();

			CFSecSaxLoader saxLoader = new CFSecSaxLoader();
			ICFSecSchemaObj schemaObj = new CFSecSchemaObj();
			schemaObj.setBackingStore( this );
			saxLoader.setSchemaObj( schemaObj );
			ICFSecClusterObj useCluster = schemaObj.getClusterTableObj().readClusterByIdIdx( auth.getSecClusterId() );
			ICFSecTenantObj useTenant = schemaObj.getTenantTableObj().readTenantByIdIdx( auth.getSecTenantId() );
			CFLibCachedMessageLog runlog = new CFLibCachedMessageLog();
			saxLoader.setLog( runlog );
			saxLoader.setUseCluster( useCluster );
			saxLoader.setUseTenant( useTenant );
			saxLoader.parseStringContents( fileContent );
			String logFileContent = runlog.getCacheContents();
			if( logFileContent == null ) {
				logFileContent = "";
			}

			commit();

			return( logFileContent );
		}
		catch( RuntimeException e ) {
			rollback();
			throw e;
		}
		catch( Error e ) {
			rollback();
			throw e;
		}
	}

	public static String getQuotedString(String val) {
		if (val == null) {
			return ("null");
		}
		else {
			char c;
			StringBuilder quoted = new StringBuilder();
			quoted.append( "'" );
			int len = val.length();
			for (int i = 0; i < len; i++)
			{
				if (val.charAt( i ) == '\'')
				{
					quoted.append("''");
				}
				else if (val.charAt( i ) == '\\') {
					quoted.append("'||E'\\\\'||'");
				}
				else {
					c = val.charAt( i );
					if (   ( c == '0' )
						|| ( c == '1' )
						|| ( c == '2' )
						|| ( c == '3' )
						|| ( c == '4' )
						|| ( c == '5' )
						|| ( c == '6' )
						|| ( c == '7' )
						|| ( c == '8' )
						|| ( c == '9' )
						|| ( c == 'a' )
						|| ( c == 'b' )
						|| ( c == 'c' )
						|| ( c == 'd' )
						|| ( c == 'e' )
						|| ( c == 'f' )
						|| ( c == 'g' )
						|| ( c == 'h' )
						|| ( c == 'i' )
						|| ( c == 'j' )
						|| ( c == 'k' )
						|| ( c == 'l' )
						|| ( c == 'm' )
						|| ( c == 'n' )
						|| ( c == 'o' )
						|| ( c == 'p' )
						|| ( c == 'q' )
						|| ( c == 'r' )
						|| ( c == 's' )
						|| ( c == 't' )
						|| ( c == 'u' )
						|| ( c == 'v' )
						|| ( c == 'w' )
						|| ( c == 'x' )
						|| ( c == 'y' )
						|| ( c == 'z' )
						|| ( c == 'A' )
						|| ( c == 'B' )
						|| ( c == 'C' )
						|| ( c == 'D' )
						|| ( c == 'E' )
						|| ( c == 'F' )
						|| ( c == 'G' )
						|| ( c == 'H' )
						|| ( c == 'I' )
						|| ( c == 'J' )
						|| ( c == 'K' )
						|| ( c == 'L' )
						|| ( c == 'M' )
						|| ( c == 'N' )
						|| ( c == 'O' )
						|| ( c == 'P' )
						|| ( c == 'Q' )
						|| ( c == 'R' )
						|| ( c == 'S' )
						|| ( c == 'T' )
						|| ( c == 'U' )
						|| ( c == 'V' )
						|| ( c == 'W' )
						|| ( c == 'X' )
						|| ( c == 'Y' )
						|| ( c == 'Z' )
						|| ( c == ' ' )
						|| ( c == '\t' )
						|| ( c == '\r' )
						|| ( c == '\n' )
						|| ( c == '`' )
						|| ( c == '~' )
						|| ( c == '!' )
						|| ( c == '@' )
						|| ( c == '#' )
						|| ( c == '$' )
						|| ( c == '%' )
						|| ( c == '^' )
						|| ( c == '&' )
						|| ( c == '*' )
						|| ( c == '(' )
						|| ( c == ')' )
						|| ( c == '-' )
						|| ( c == '_' )
						|| ( c == '=' )
						|| ( c == '+' )
						|| ( c == '[' )
						|| ( c == ']' )
						|| ( c == '{' )
						|| ( c == '}' )
						|| ( c == '|' )
						|| ( c == ';' )
						|| ( c == ':' )
						|| ( c == '"' )
						|| ( c == '<' )
						|| ( c == '>' )
						|| ( c == ',' )
						|| ( c == '.' )
						|| ( c == '/' )
						|| ( c == '?' ))
					{
						quoted.append(c);
					}
					else {
//						Syslog.warn("\t\t\tReplacing invalid character '" + c + "' with space");
						quoted.append( ' ' );
					}
				}
			}
			quoted.append( "'" );
			return (quoted.toString());
			}
		}

	public static String getNullableString(ResultSet reader, int colidx) {
		try {
			String val = reader.getString( colidx );
			if( reader.wasNull() ) {
				return(null);
			}
			else {
				return( val );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableString",
				e );
		}
	}

	public static String getBlobString(byte[] val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( "'" + new String( Base64.encodeBase64( val ) ) + "'" );
		}
	}

	public static String getBoolString(Boolean val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			if( val ) {
				return( "true" );
			}
			else {
				return( "false" );
			}
		}
	}

	public static String getBoolString(boolean val) {
		if( val ) {
			return( "true" );
		}
		else {
			return( "false" );
		}
	}

	public static String getInt16String(Short val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getInt16String(short val) {
		return( Short.toString( val ) );
	}

	public static String getInt32String(Integer val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getInt32String(int val) {
		return( Integer.toString( val ) );
	}

	public static String getInt64String(Long val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getInt64String(long val) {
		return( Long.toString( val ) );
	}

	public static String getUInt16String(Integer val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getUInt16String(int val) {
		return( Integer.toString( val ) );
	}

	public static String getUInt32String(Long val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getUInt32String(long val) {
		return( Long.toString( val ) );
	}

	public static String getUInt64String(BigDecimal val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getFloatString(Float val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getFloatString(float val) {
		return( Float.toString( val ) );
	}

	public static String getDoubleString(Double val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static String getDoubleString(double val) {
		return( Double.toString( val ) );
	}

	public static String getNumberString(BigDecimal val) {
		if( val == null ) {
			return( "null" );
		}
		else {
			return( val.toString() );
		}
	}

	public static Integer getNullableInt32(ResultSet reader, int colidx) {
		try {
			int val = reader.getInt( colidx );
			if( reader.wasNull() ) {
				return(null);
			}
			else {
				return( Integer.valueOf( val ) );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableInt32",
				e );
		}
	}

	public static Short getNullableInt16(ResultSet reader, int colidx) {
		try {
			short val = reader.getShort( colidx );
			if( reader.wasNull() ) {
				return(null);
			}
			else {
				return( Short.valueOf( val ) );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableInt64",
				e );
		}
	}

	public static Integer getNullableUInt16(ResultSet reader, int colidx) {
		try {
			int val = reader.getInt( colidx );
			if( reader.wasNull() ) {
				return(null);
			}
			else {
				return( Integer.valueOf( val ) );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableUInt16",
				e );
		}
	}

	public static Long getNullableUInt32(ResultSet reader, int colidx) {
		try {
			long val = reader.getLong( colidx );
			if( reader.wasNull() ) {
				return(null);
			}
			else {
				return( Long.valueOf( val ) );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableUInt32",
				e );
		}
	}

	public static BigDecimal getNullableUInt64(ResultSet reader, int colidx) {
		try {
			String strval = reader.getString( colidx );
			if( reader.wasNull() || ( strval == null ) || ( strval.length() <=0 ) ) {
				return(null);
			}
			else {
				BigDecimal retval = new BigDecimal( strval );
				return( retval );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableUInt64",
				e );
		}
	}

	public static Byte getNullableByte(ResultSet reader, int colidx) {
		try {
			byte val = reader.getByte( colidx );
			if( reader.wasNull() ) {
				return(null);
			}
			else {
				return( Byte.valueOf( val ) );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( CFSecMySqlSchema.class,
				"getNullableByte",
				e );
		}
	}

	public static String getQuotedDateString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			StringBuffer buff = new StringBuffer( "'" );
			Formatter fmt = new Formatter( buff );
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( "'" );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getQuotedTimeString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			StringBuffer buff = new StringBuffer( "'" );
			Formatter fmt = new Formatter( buff );
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			buff.append( "'" );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getQuotedTimestampString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			StringBuffer buff = new StringBuffer( "'" );
			Formatter fmt = new Formatter( buff );
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			buff.append( "'" );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getQuotedTZDateString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			StringBuffer buff = new StringBuffer( "'" );
			Formatter fmt = new Formatter( buff );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			buff.append( "'" );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getQuotedTZTimeString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			StringBuffer buff = new StringBuffer( "'" );
			Formatter fmt = new Formatter( buff );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			buff.append( "'" );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getQuotedTZTimestampString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			StringBuffer buff = new StringBuffer( "'" );
			Formatter fmt = new Formatter( buff );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			buff.append( "'" );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getDateString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			StringBuffer buff = new StringBuffer();
			Formatter fmt = new Formatter( buff );
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getTimeString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			StringBuffer buff = new StringBuffer();
			Formatter fmt = new Formatter( buff );
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getTimestampString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			StringBuffer buff = new StringBuffer();
			Formatter fmt = new Formatter( buff );
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getTZDateString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			StringBuffer buff = new StringBuffer();
			Formatter fmt = new Formatter( buff );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getTZTimeString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			StringBuffer buff = new StringBuffer();
			Formatter fmt = new Formatter( buff );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getTZTimestampString(Calendar val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			Calendar db = CFLibDbUtil.getDbServerCalendar( val );
			StringBuffer buff = new StringBuffer();
			Formatter fmt = new Formatter( buff );
			fmt.format( "%1$04d", db.get( Calendar.YEAR ) );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.MONTH ) + 1 );
			buff.append( "-" );
			fmt.format( "%1$02d", db.get( Calendar.DAY_OF_MONTH ) );
			buff.append( " " );
			fmt.format( "%1$02d", db.get( Calendar.HOUR_OF_DAY ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.MINUTE ) );
			buff.append( ":" );
			fmt.format( "%1$02d", db.get( Calendar.SECOND ) );
			fmt.close();
			return( buff.toString() );
		}
	}

	public static String getUuidString(UUID val) {
		if ( val == null ) {
			return ("null");
		}
		else {
			return( "'" + val.toString() + "'" );
		}
	}

	public static Calendar convertDateString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else if ( val.length() != 10 ) {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertDateString",
				"Value must be in YYYY-MM-DD format, \"" + val + "\" is invalid" );
		}
		else if (((val.charAt( 0 ) >= '0') && (val.charAt( 0 ) <= '9'))
			 && ((val.charAt( 1 ) >= '0') && (val.charAt( 1 ) <= '9'))
			 && ((val.charAt( 2 ) >= '0') && (val.charAt( 2 ) <= '9'))
			 && ((val.charAt( 3 ) >= '0') && (val.charAt( 3 ) <= '9'))
			 && (val.charAt( 4 ) == '-')
			 && ((val.charAt( 5 ) >= '0') && (val.charAt( 5 ) <= '1'))
			 && ((val.charAt( 6 ) >= '0') && (val.charAt( 6 ) <= '9'))
			 && (val.charAt( 7 ) == '-' )
			 && ((val.charAt( 8 ) >= '0') && (val.charAt( 8 ) <= '3'))
			 && ((val.charAt( 9 ) >= '0') && (val.charAt( 9 ) <= '9')) )
		{
			/*
			 *	NOTE:
			 *		.Net uses substring( startcol, lengthOfSubstring )
			 *		Java uses substring( startcol, endcol ) and does not
			 *			include charAt( endcol );
			 */
			int year = Integer.parseInt( val.substring( 0, 4 ) );
			int month = Integer.parseInt( val.substring( 5, 7 ) );
			int day = Integer.parseInt( val.substring( 8, 10 ) );
			Calendar retval = new GregorianCalendar( CFLibDbUtil.getDbServerTimeZone() );
			retval.set( Calendar.YEAR, year );
			retval.set( Calendar.MONTH, month - 1 );
			retval.set( Calendar.DAY_OF_MONTH, day );
			retval.set( Calendar.HOUR_OF_DAY, 0 );
			retval.set( Calendar.MINUTE, 0 );
			retval.set( Calendar.SECOND, 0 );
			Calendar local = new GregorianCalendar();
			local.setTimeInMillis( retval.getTimeInMillis() );
			return( local );
		}
		else {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertDateString",
				"Value must be in YYYY-MM-DD format, \"" + val + "\" is invalid" );
		}
	}
	public static Calendar convertTimeString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else if ( val.length() != 8 ) {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTimeString",
				"Value must be in HH24:MI:SS format, \"" + val + "\" is invalid" );
		}
		else if (((val.charAt( 0 ) >= '0') && (val.charAt( 0 ) <= '2'))
			 && ((val.charAt( 1 ) >= '0') && (val.charAt( 1 ) <= '9'))
			 && (val.charAt( 2 ) == ':')
			 && ((val.charAt( 3 ) >= '0') && (val.charAt( 3 ) <= '5'))
			 && ((val.charAt( 4 ) >= '0') && (val.charAt( 4 ) <= '9'))
			 && (val.charAt( 5 ) == ':')
			 && ((val.charAt( 6 ) >= '0') && (val.charAt( 6 ) <= '5'))
			 && ((val.charAt( 7 ) >= '0') && (val.charAt( 7 ) <= '9')) )
		{
			/*
			 *	NOTE:
			 *		.Net uses substring( startcol, lengthOfSubstring )
			 *		Java uses substring( startcol, endcol ) and does not
			 *			include charAt( endcol );
			 */
			int hour = Integer.parseInt( val.substring( 0, 2 ) );
			int minute = Integer.parseInt( val.substring( 3, 5 ) );
			int second = Integer.parseInt( val.substring( 6, 8 ) );
			Calendar retval = new GregorianCalendar( CFLibDbUtil.getDbServerTimeZone() );
			retval.set( Calendar.YEAR, 2000 );
			retval.set( Calendar.MONTH, 0 );
			retval.set( Calendar.DAY_OF_MONTH, 1 );
			retval.set( Calendar.HOUR_OF_DAY, hour );
			retval.set( Calendar.MINUTE, minute );
			retval.set( Calendar.SECOND, second );
			Calendar local = new GregorianCalendar();
			local.setTimeInMillis( retval.getTimeInMillis() );
			return( local );
		}
		else {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTimeString",
				"Value must be in HH24:MI:SS format \"" + val + "\" is invalid" );
		}
	}
	public static Calendar convertTimestampString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else if ( val.length() != 19 ) {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTimestampString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
		else if (((val.charAt( 0 ) >= '0') && (val.charAt( 0 ) <= '9'))
			 && ((val.charAt( 1 ) >= '0') && (val.charAt( 1 ) <= '9'))
			 && ((val.charAt( 2 ) >= '0') && (val.charAt( 2 ) <= '9'))
			 && ((val.charAt( 3 ) >= '0') && (val.charAt( 3 ) <= '9'))
			 && (val.charAt( 4 ) == '-')
			 && ((val.charAt( 5 ) >= '0') && (val.charAt( 5 ) <= '1'))
			 && ((val.charAt( 6 ) >= '0') && (val.charAt( 6 ) <= '9'))
			 && (val.charAt( 7 ) == '-' )
			 && ((val.charAt( 8 ) >= '0') && (val.charAt( 8 ) <= '3'))
			 && ((val.charAt( 9 ) >= '0') && (val.charAt( 9 ) <= '9'))
			 && (val.charAt( 10 ) == ' ' )
			 && ((val.charAt( 11 ) >= '0') && (val.charAt( 11 ) <= '2'))
			 && ((val.charAt( 12 ) >= '0') && (val.charAt( 12 ) <= '9'))
			 && (val.charAt( 13 ) == ':' )
			 && ((val.charAt( 14 ) >= '0') && (val.charAt( 14 ) <= '5'))
			 && ((val.charAt( 15 ) >= '0') && (val.charAt( 15 ) <= '9'))
			 && (val.charAt( 16 ) == ':' )
			 && ((val.charAt( 17 ) >= '0') && (val.charAt( 17 ) <= '5'))
			 && ((val.charAt( 18 ) >= '0') && (val.charAt( 18 ) <= '9')) )
		{
			/*
			 *	NOTE:
			 *		.Net uses substring( startcol, lengthOfSubstring )
			 *		Java uses substring( startcol, endcol ) and does not
			 *			include charAt( endcol );
			 */
			int year = Integer.parseInt( val.substring( 0, 4 ) );
			int month = Integer.parseInt( val.substring( 5, 7 ) );
			int day = Integer.parseInt( val.substring( 8, 10 ) );
			int hour = Integer.parseInt( val.substring( 11, 13 ) );
			int minute = Integer.parseInt( val.substring( 14, 16 ) );
			int second = Integer.parseInt( val.substring( 17, 19 ) );
			Calendar retval = new GregorianCalendar( CFLibDbUtil.getDbServerTimeZone() );
			retval.set( Calendar.YEAR, year );
			retval.set( Calendar.MONTH, month - 1 );
			retval.set( Calendar.DAY_OF_MONTH, day );
			retval.set( Calendar.HOUR_OF_DAY, hour );
			retval.set( Calendar.MINUTE, minute );
			retval.set( Calendar.SECOND, second );
			Calendar local = new GregorianCalendar();
			local.setTimeInMillis( retval.getTimeInMillis() );
			return( local );
		}
		else {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTimestampString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
	}

	public static Calendar convertTZDateString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else if ( val.length() != 19 ) {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTZDateString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
		else if (((val.charAt( 0 ) >= '0') && (val.charAt( 0 ) <= '9'))
			 && ((val.charAt( 1 ) >= '0') && (val.charAt( 1 ) <= '9'))
			 && ((val.charAt( 2 ) >= '0') && (val.charAt( 2 ) <= '9'))
			 && ((val.charAt( 3 ) >= '0') && (val.charAt( 3 ) <= '9'))
			 && (val.charAt( 4 ) == '-')
			 && ((val.charAt( 5 ) >= '0') && (val.charAt( 5 ) <= '1'))
			 && ((val.charAt( 6 ) >= '0') && (val.charAt( 6 ) <= '9'))
			 && (val.charAt( 7 ) == '-' )
			 && ((val.charAt( 8 ) >= '0') && (val.charAt( 8 ) <= '3'))
			 && ((val.charAt( 9 ) >= '0') && (val.charAt( 9 ) <= '9'))
			 && (val.charAt( 10 ) == ' ' )
			 && ((val.charAt( 11 ) >= '0') && (val.charAt( 11 ) <= '2'))
			 && ((val.charAt( 12 ) >= '0') && (val.charAt( 12 ) <= '9'))
			 && (val.charAt( 13 ) == ':' )
			 && ((val.charAt( 14 ) >= '0') && (val.charAt( 14 ) <= '5'))
			 && ((val.charAt( 15 ) >= '0') && (val.charAt( 15 ) <= '9'))
			 && (val.charAt( 16 ) == ':' )
			 && ((val.charAt( 17 ) >= '0') && (val.charAt( 17 ) <= '5'))
			 && ((val.charAt( 18 ) >= '0') && (val.charAt( 18 ) <= '9')) )
		{
			/*
			 *	NOTE:
			 *		.Net uses substring( startcol, lengthOfSubstring )
			 *		Java uses substring( startcol, endcol ) and does not
			 *			include charAt( endcol );
			 */
			int year = Integer.parseInt( val.substring( 0, 4 ) );
			int month = Integer.parseInt( val.substring( 5, 7 ) );
			int day = Integer.parseInt( val.substring( 8, 10 ) );
			int hour = Integer.parseInt( val.substring( 11, 13 ) );
			int minute = Integer.parseInt( val.substring( 14, 16 ) );
			int second = Integer.parseInt( val.substring( 17, 19 ) );
			Calendar retval = new GregorianCalendar( CFLibDbUtil.getDbServerTimeZone() );
			retval.set( Calendar.YEAR, year );
			retval.set( Calendar.MONTH, month - 1 );
			retval.set( Calendar.DAY_OF_MONTH, day );
			retval.set( Calendar.HOUR_OF_DAY, hour );
			retval.set( Calendar.MINUTE, minute );
			retval.set( Calendar.SECOND, second );
			Calendar utc = CFLibDbUtil.getUTCCalendar( retval );
			return( utc );
		}
		else {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTZDateString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
	}

	public static Calendar convertTZTimeString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else if ( val.length() != 19 ) {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTZTimeString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
		else if (((val.charAt( 0 ) >= '0') && (val.charAt( 0 ) <= '9'))
			 && ((val.charAt( 1 ) >= '0') && (val.charAt( 1 ) <= '9'))
			 && ((val.charAt( 2 ) >= '0') && (val.charAt( 2 ) <= '9'))
			 && ((val.charAt( 3 ) >= '0') && (val.charAt( 3 ) <= '9'))
			 && (val.charAt( 4 ) == '-')
			 && ((val.charAt( 5 ) >= '0') && (val.charAt( 5 ) <= '1'))
			 && ((val.charAt( 6 ) >= '0') && (val.charAt( 6 ) <= '9'))
			 && (val.charAt( 7 ) == '-' )
			 && ((val.charAt( 8 ) >= '0') && (val.charAt( 8 ) <= '3'))
			 && ((val.charAt( 9 ) >= '0') && (val.charAt( 9 ) <= '9'))
			 && (val.charAt( 10 ) == ' ' )
			 && ((val.charAt( 11 ) >= '0') && (val.charAt( 11 ) <= '2'))
			 && ((val.charAt( 12 ) >= '0') && (val.charAt( 12 ) <= '9'))
			 && (val.charAt( 13 ) == ':' )
			 && ((val.charAt( 14 ) >= '0') && (val.charAt( 14 ) <= '5'))
			 && ((val.charAt( 15 ) >= '0') && (val.charAt( 15 ) <= '9'))
			 && (val.charAt( 16 ) == ':' )
			 && ((val.charAt( 17 ) >= '0') && (val.charAt( 17 ) <= '5'))
			 && ((val.charAt( 18 ) >= '0') && (val.charAt( 18 ) <= '9')) )
		{
			/*
			 *	NOTE:
			 *		.Net uses substring( startcol, lengthOfSubstring )
			 *		Java uses substring( startcol, endcol ) and does not
			 *			include charAt( endcol );
			 */
			int year = Integer.parseInt( val.substring( 0, 4 ) );
			int month = Integer.parseInt( val.substring( 5, 7 ) );
			int day = Integer.parseInt( val.substring( 8, 10 ) );
			int hour = Integer.parseInt( val.substring( 11, 13 ) );
			int minute = Integer.parseInt( val.substring( 14, 16 ) );
			int second = Integer.parseInt( val.substring( 17, 19 ) );
			Calendar retval = new GregorianCalendar( CFLibDbUtil.getDbServerTimeZone() );
			retval.set( Calendar.YEAR, year );
			retval.set( Calendar.MONTH, month - 1 );
			retval.set( Calendar.DAY_OF_MONTH, day );
			retval.set( Calendar.HOUR_OF_DAY, hour );
			retval.set( Calendar.MINUTE, minute );
			retval.set( Calendar.SECOND, second );
			Calendar utc = CFLibDbUtil.getUTCCalendar( retval );
			return( utc );
		}
		else {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTZTimeString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
	}

	public static Calendar convertTZTimestampString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else if ( val.length() != 19 ) {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTZTimestampString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
		else if (((val.charAt( 0 ) >= '0') && (val.charAt( 0 ) <= '9'))
			 && ((val.charAt( 1 ) >= '0') && (val.charAt( 1 ) <= '9'))
			 && ((val.charAt( 2 ) >= '0') && (val.charAt( 2 ) <= '9'))
			 && ((val.charAt( 3 ) >= '0') && (val.charAt( 3 ) <= '9'))
			 && (val.charAt( 4 ) == '-')
			 && ((val.charAt( 5 ) >= '0') && (val.charAt( 5 ) <= '1'))
			 && ((val.charAt( 6 ) >= '0') && (val.charAt( 6 ) <= '9'))
			 && (val.charAt( 7 ) == '-' )
			 && ((val.charAt( 8 ) >= '0') && (val.charAt( 8 ) <= '3'))
			 && ((val.charAt( 9 ) >= '0') && (val.charAt( 9 ) <= '9'))
			 && (val.charAt( 10 ) == ' ' )
			 && ((val.charAt( 11 ) >= '0') && (val.charAt( 11 ) <= '2'))
			 && ((val.charAt( 12 ) >= '0') && (val.charAt( 12 ) <= '9'))
			 && (val.charAt( 13 ) == ':' )
			 && ((val.charAt( 14 ) >= '0') && (val.charAt( 14 ) <= '5'))
			 && ((val.charAt( 15 ) >= '0') && (val.charAt( 15 ) <= '9'))
			 && (val.charAt( 16 ) == ':' )
			 && ((val.charAt( 17 ) >= '0') && (val.charAt( 17 ) <= '5'))
			 && ((val.charAt( 18 ) >= '0') && (val.charAt( 18 ) <= '9')) )
		{
			/*
			 *	NOTE:
			 *		.Net uses substring( startcol, lengthOfSubstring )
			 *		Java uses substring( startcol, endcol ) and does not
			 *			include charAt( endcol );
			 */
			int year = Integer.parseInt( val.substring( 0, 4 ) );
			int month = Integer.parseInt( val.substring( 5, 7 ) );
			int day = Integer.parseInt( val.substring( 8, 10 ) );
			int hour = Integer.parseInt( val.substring( 11, 13 ) );
			int minute = Integer.parseInt( val.substring( 14, 16 ) );
			int second = Integer.parseInt( val.substring( 17, 19 ) );
			Calendar retval = new GregorianCalendar( CFLibDbUtil.getDbServerTimeZone() );
			retval.set( Calendar.YEAR, year );
			retval.set( Calendar.MONTH, month - 1 );
			retval.set( Calendar.DAY_OF_MONTH, day );
			retval.set( Calendar.HOUR_OF_DAY, hour );
			retval.set( Calendar.MINUTE, minute );
			retval.set( Calendar.SECOND, second );
			Calendar utc = CFLibDbUtil.getUTCCalendar( retval );
			return( utc );
		}
		else {
			throw new CFLibUsageException( CFSecMySqlSchema.class,
				"convertTZTimestampString",
				"Value must be in YYYY-MM-DD HH24:MI:SS format \"" + val + "\" is invalid" );
		}
	}

	public static UUID convertUuidString(String val) {
		if( ( val == null ) || ( val.length() == 0 ) ) {
			return( null );
		}
		else {
			return( UUID.fromString( val ) );
		}
	}
}
