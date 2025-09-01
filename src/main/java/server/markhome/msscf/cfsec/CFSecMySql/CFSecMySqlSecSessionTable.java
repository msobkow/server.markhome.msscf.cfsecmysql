// Description: Java 11 MySQL Jdbc DbIO implementation for SecSession.

/*
 *	server.markhome.msscf.CFSec
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfsec.CFSecMySql;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfsec.CFSecObj.*;

/*
 *	CFSecMySqlSecSessionTable MySQL Jdbc DbIO implementation
 *	for SecSession.
 */
public class CFSecMySqlSecSessionTable
	implements ICFSecSecSessionTable
{
	private CFSecMySqlSchema schema;
	protected PreparedStatement stmtReadBuffByPKey = null;
	protected PreparedStatement stmtLockBuffByPKey = null;
	protected PreparedStatement stmtCreateByPKey = null;
	protected PreparedStatement stmtUpdateByPKey = null;
	protected PreparedStatement stmtDeleteByPKey = null;
	protected PreparedStatement stmtReadAllBuff = null;
	protected PreparedStatement stmtPageAllBuff = null;
	protected PreparedStatement stmtReadBuffByIdIdx = null;
	protected PreparedStatement stmtReadBuffBySecUserIdx = null;
	protected PreparedStatement stmtPageBuffBySecUserIdx = null;
	protected PreparedStatement stmtReadBuffBySecDevIdx = null;
	protected PreparedStatement stmtPageBuffBySecDevIdx = null;
	protected PreparedStatement stmtReadBuffByStartIdx = null;
	protected PreparedStatement stmtReadBuffByFinishIdx = null;
	protected PreparedStatement stmtPageBuffByFinishIdx = null;
	protected PreparedStatement stmtReadBuffBySecProxyIdx = null;
	protected PreparedStatement stmtPageBuffBySecProxyIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteBySecUserIdx = null;
	protected PreparedStatement stmtDeleteBySecDevIdx = null;
	protected PreparedStatement stmtDeleteByStartIdx = null;
	protected PreparedStatement stmtDeleteByFinishIdx = null;
	protected PreparedStatement stmtDeleteBySecProxyIdx = null;

	public CFSecMySqlSecSessionTable( CFSecMySqlSchema argSchema ) {
		schema = argSchema;
	}

	public void createSecSession( CFSecAuthorization Authorization,
		CFSecSecSessionBuff Buff )
	{
		final String S_ProcName = "createSecSession";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			UUID SecUserId = Buff.getRequiredSecUserId();
			String SecDevName = Buff.getOptionalSecDevName();
			Calendar Start = Buff.getRequiredStart();
			Calendar Finish = Buff.getOptionalFinish();
			UUID SecProxyId = Buff.getOptionalSecProxyId();

			UUID SecSessionId = UUID.randomUUID();			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_create_secsess( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"timestamp( ? )" + ", "
				+		"timestamp( ? )" + ", "
				+		"?" + " )";
			if( stmtCreateByPKey == null ) {
				stmtCreateByPKey = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtCreateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtCreateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtCreateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtCreateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtCreateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtCreateByPKey.setString( argIdx++, "a010" );
			stmtCreateByPKey.setString( argIdx++, SecSessionId.toString() );
			stmtCreateByPKey.setString( argIdx++, SecUserId.toString() );
			if( SecDevName != null ) {
				stmtCreateByPKey.setString( argIdx++, SecDevName );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtCreateByPKey.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Start ) );
			if( Finish != null ) {
				stmtCreateByPKey.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Finish ) );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( SecProxyId != null ) {
				stmtCreateByPKey.setString( argIdx++, SecProxyId.toString() );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtCreateByPKey.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff createdBuff = unpackSecSessionResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredSecSessionId( createdBuff.getRequiredSecSessionId() );
				Buff.setRequiredSecUserId( createdBuff.getRequiredSecUserId() );
				Buff.setOptionalSecDevName( createdBuff.getOptionalSecDevName() );
				Buff.setRequiredStart( createdBuff.getRequiredStart() );
				Buff.setOptionalFinish( createdBuff.getOptionalFinish() );
				Buff.setOptionalSecProxyId( createdBuff.getOptionalSecProxyId() );
				Buff.setRequiredRevision( createdBuff.getRequiredRevision() );
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected a single-record response, " + resultSet.getRow() + " rows selected" );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	protected static String S_sqlSelectSecSessionDistinctClassCode = null;

	public String getSqlSelectSecSessionDistinctClassCode() {
		if( S_sqlSelectSecSessionDistinctClassCode == null ) {
			S_sqlSelectSecSessionDistinctClassCode =
					"SELECT "
				+		"DISTINCT a010.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecSess AS a010 ";
		}
		return( S_sqlSelectSecSessionDistinctClassCode );
	}

	protected static String S_sqlSelectSecSessionBuff = null;

	public String getSqlSelectSecSessionBuff() {
		if( S_sqlSelectSecSessionBuff == null ) {
			S_sqlSelectSecSessionBuff =
					"SELECT "
				+		"a010.SecSessionId, "
				+		"a010.SecUserId, "
				+		"a010.SecDevName, "
				+		"date_format( a010.start_ts, '%Y-%m-%d %H:%i:%S' ) as start_ts, "
				+		"date_format( a010.finish_ts, '%Y-%m-%d %H:%i:%S' ) as finish_ts, "
				+		"a010.SecProxyId, "
				+		"a010.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecSess AS a010 ";
		}
		return( S_sqlSelectSecSessionBuff );
	}

	protected CFSecSecSessionBuff unpackSecSessionResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackSecSessionResultSetToBuff";
		int idxcol = 1;
		CFSecSecSessionBuff buff = schema.getFactorySecSession().newBuff();		buff.setRequiredSecSessionId( CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) ) );
		idxcol++;
		buff.setRequiredSecUserId( CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) ) );
		idxcol++;
		{
			String colVal = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setOptionalSecDevName( null );
			}
			else {
				buff.setOptionalSecDevName( colVal );
			}
		}
		idxcol++;
		buff.setRequiredStart( CFSecMySqlSchema.convertTimestampString( resultSet.getString( idxcol ) ) );
		idxcol++;
		{
			Calendar colVal = CFSecMySqlSchema.convertTimestampString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalFinish( null );
			}
			else {
				buff.setOptionalFinish( colVal );
			}
		}
		idxcol++;
		{
			UUID colVal = CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalSecProxyId( null );
			}
			else {
				buff.setOptionalSecProxyId( colVal );
			}
		}
		idxcol++;

		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecSecSessionBuff readDerived( CFSecAuthorization Authorization,
		CFSecSecSessionPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecSessionBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecSessionBuff lockDerived( CFSecAuthorization Authorization,
		CFSecSecSessionPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecSessionBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecSessionBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecSecSessionBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecSecSessionBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		UUID SecSessionId )
	{
		final String S_ProcName = "CFSecMySqlSecSessionTable.readDerivedByIdIdx() ";
		CFSecSecSessionBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				SecSessionId );
		return( buff );
	}

	public CFSecSecSessionBuff[] readDerivedBySecUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readDerivedBySecUserIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecSessionBuff[] buffList = readBuffBySecUserIdx( Authorization,
				SecUserId );
		return( buffList );

	}

	public CFSecSecSessionBuff[] readDerivedBySecDevIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String SecDevName )
	{
		final String S_ProcName = "readDerivedBySecDevIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecSessionBuff[] buffList = readBuffBySecDevIdx( Authorization,
				SecUserId,
				SecDevName );
		return( buffList );

	}

	public CFSecSecSessionBuff readDerivedByStartIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		Calendar Start )
	{
		final String S_ProcName = "CFSecMySqlSecSessionTable.readDerivedByStartIdx() ";
		CFSecSecSessionBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByStartIdx( Authorization,
				SecUserId,
				Start );
		return( buff );
	}

	public CFSecSecSessionBuff[] readDerivedByFinishIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		Calendar Finish )
	{
		final String S_ProcName = "readDerivedByFinishIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecSessionBuff[] buffList = readBuffByFinishIdx( Authorization,
				SecUserId,
				Finish );
		return( buffList );

	}

	public CFSecSecSessionBuff[] readDerivedBySecProxyIdx( CFSecAuthorization Authorization,
		UUID SecProxyId )
	{
		final String S_ProcName = "readDerivedBySecProxyIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecSessionBuff[] buffList = readBuffBySecProxyIdx( Authorization,
				SecProxyId );
		return( buffList );

	}

	public CFSecSecSessionBuff readBuff( CFSecAuthorization Authorization,
		CFSecSecSessionPKey PKey )
	{
		final String S_ProcName = "readBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			UUID SecSessionId = PKey.getRequiredSecSessionId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByPKey == null ) {
				stmtReadBuffByPKey = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByPKey.setString( argIdx++, SecSessionId.toString() );
			try {
				resultSet = stmtReadBuffByPKey.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				return( buff );
			}
			else {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff lockBuff( CFSecAuthorization Authorization,
		CFSecSecSessionPKey PKey )
	{
		final String S_ProcName = "lockBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			UUID SecSessionId = PKey.getRequiredSecSessionId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_lock_secsess( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtLockBuffByPKey == null ) {
				stmtLockBuffByPKey = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtLockBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtLockBuffByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtLockBuffByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtLockBuffByPKey.setString( argIdx++, SecSessionId.toString() );
			try {
				resultSet = stmtLockBuffByPKey.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				return( buff );
			}
			else {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_all( ?, ?, ?, ?, ? )";
			if( stmtReadAllBuff == null ) {
				stmtReadAllBuff = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			try {
				resultSet = stmtReadAllBuff.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	/**
	 *	Read a page of all the specific SecSession buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecSession instances in the database accessible for the Authorization.
	 */
	public CFSecSecSessionBuff[] pageAllBuff( CFSecAuthorization Authorization,
		UUID priorSecSessionId )
	{
		final String S_ProcName = "pageAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secsess_all( ?, ?, ?, ?, ?, ? )";
			if( stmtPageAllBuff == null ) {
				stmtPageAllBuff = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( priorSecSessionId != null ) {
				stmtPageAllBuff.setString( argIdx++, priorSecSessionId.toString() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageAllBuff.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		UUID SecSessionId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_by_ididx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByIdIdx == null ) {
				stmtReadBuffByIdIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByIdIdx.setString( argIdx++, SecSessionId.toString() );
			try {
				resultSet = stmtReadBuffByIdIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				return( buff );
			}
			else {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff[] readBuffBySecUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readBuffBySecUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_by_secuseridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffBySecUserIdx == null ) {
				stmtReadBuffBySecUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffBySecUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffBySecUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffBySecUserIdx.setString( argIdx++, SecUserId.toString() );
			try {
				resultSet = stmtReadBuffBySecUserIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff[] readBuffBySecDevIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String SecDevName )
	{
		final String S_ProcName = "readBuffBySecDevIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_by_secdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtReadBuffBySecDevIdx == null ) {
				stmtReadBuffBySecDevIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffBySecDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffBySecDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffBySecDevIdx.setString( argIdx++, SecUserId.toString() );
			if( SecDevName != null ) {
				stmtReadBuffBySecDevIdx.setString( argIdx++, SecDevName );
			}
			else {
				stmtReadBuffBySecDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffBySecDevIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff readBuffByStartIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		Calendar Start )
	{
		final String S_ProcName = "readBuffByStartIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_by_startidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"timestamp( ? )" + " )";
			if( stmtReadBuffByStartIdx == null ) {
				stmtReadBuffByStartIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByStartIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByStartIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByStartIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByStartIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByStartIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByStartIdx.setString( argIdx++, SecUserId.toString() );
			stmtReadBuffByStartIdx.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Start ) );
			try {
				resultSet = stmtReadBuffByStartIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				return( buff );
			}
			else {
				return( null );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff[] readBuffByFinishIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		Calendar Finish )
	{
		final String S_ProcName = "readBuffByFinishIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_by_finishidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"timestamp( ? )" + " )";
			if( stmtReadBuffByFinishIdx == null ) {
				stmtReadBuffByFinishIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByFinishIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByFinishIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByFinishIdx.setString( argIdx++, SecUserId.toString() );
			if( Finish != null ) {
				stmtReadBuffByFinishIdx.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Finish ) );
			}
			else {
				stmtReadBuffByFinishIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffByFinishIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public CFSecSecSessionBuff[] readBuffBySecProxyIdx( CFSecAuthorization Authorization,
		UUID SecProxyId )
	{
		final String S_ProcName = "readBuffBySecProxyIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secsess_by_secproxyidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffBySecProxyIdx == null ) {
				stmtReadBuffBySecProxyIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffBySecProxyIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffBySecProxyIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( SecProxyId != null ) {
				stmtReadBuffBySecProxyIdx.setString( argIdx++, SecProxyId.toString() );
			}
			else {
				stmtReadBuffBySecProxyIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffBySecProxyIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	/**
	 *	Read a page array of the specific SecSession buffer instances identified by the duplicate key SecUserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argSecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecSessionBuff[] pageBuffBySecUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		UUID priorSecSessionId )
	{
		final String S_ProcName = "pageBuffBySecUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secsess_by_secuseridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffBySecUserIdx == null ) {
				stmtPageBuffBySecUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffBySecUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffBySecUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffBySecUserIdx.setString( argIdx++, SecUserId.toString() );
			if( priorSecSessionId != null ) {
				stmtPageBuffBySecUserIdx.setString( argIdx++, priorSecSessionId.toString() );
			}
			else {
				stmtPageBuffBySecUserIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffBySecUserIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	/**
	 *	Read a page array of the specific SecSession buffer instances identified by the duplicate key SecDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argSecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	argSecDevName	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecSessionBuff[] pageBuffBySecDevIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String SecDevName,
		UUID priorSecSessionId )
	{
		final String S_ProcName = "pageBuffBySecDevIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secsess_by_secdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffBySecDevIdx == null ) {
				stmtPageBuffBySecDevIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffBySecDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffBySecDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffBySecDevIdx.setString( argIdx++, SecUserId.toString() );
			if( SecDevName != null ) {
				stmtPageBuffBySecDevIdx.setString( argIdx++, SecDevName );
			}
			else {
				stmtPageBuffBySecDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecSessionId != null ) {
				stmtPageBuffBySecDevIdx.setString( argIdx++, priorSecSessionId.toString() );
			}
			else {
				stmtPageBuffBySecDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffBySecDevIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	/**
	 *	Read a page array of the specific SecSession buffer instances identified by the duplicate key FinishIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argSecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	argFinish	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecSessionBuff[] pageBuffByFinishIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		Calendar Finish,
		UUID priorSecSessionId )
	{
		final String S_ProcName = "pageBuffByFinishIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secsess_by_finishidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"timestamp( ? )" + ", "
				+		"?" + " )";
			if( stmtPageBuffByFinishIdx == null ) {
				stmtPageBuffByFinishIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByFinishIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByFinishIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByFinishIdx.setString( argIdx++, SecUserId.toString() );
			if( Finish != null ) {
				stmtPageBuffByFinishIdx.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Finish ) );
			}
			else {
				stmtPageBuffByFinishIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecSessionId != null ) {
				stmtPageBuffByFinishIdx.setString( argIdx++, priorSecSessionId.toString() );
			}
			else {
				stmtPageBuffByFinishIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffByFinishIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	/**
	 *	Read a page array of the specific SecSession buffer instances identified by the duplicate key SecProxyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argSecProxyId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecSessionBuff[] pageBuffBySecProxyIdx( CFSecAuthorization Authorization,
		UUID SecProxyId,
		UUID priorSecSessionId )
	{
		final String S_ProcName = "pageBuffBySecProxyIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secsess_by_secproxyidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffBySecProxyIdx == null ) {
				stmtPageBuffBySecProxyIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffBySecProxyIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffBySecProxyIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( SecProxyId != null ) {
				stmtPageBuffBySecProxyIdx.setString( argIdx++, SecProxyId.toString() );
			}
			else {
				stmtPageBuffBySecProxyIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecSessionId != null ) {
				stmtPageBuffBySecProxyIdx.setString( argIdx++, priorSecSessionId.toString() );
			}
			else {
				stmtPageBuffBySecProxyIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffBySecProxyIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecSessionBuff> buffList = new LinkedList<CFSecSecSessionBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff buff = unpackSecSessionResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecSessionBuff[] retBuff = new CFSecSecSessionBuff[ buffList.size() ];
			Iterator<CFSecSecSessionBuff> iter = buffList.iterator();
			while( iter.hasNext() ) {
				retBuff[idx++] = iter.next();
			}
			return( retBuff );
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void updateSecSession( CFSecAuthorization Authorization,
		CFSecSecSessionBuff Buff )
	{
		final String S_ProcName = "updateSecSession";
		ResultSet resultSet = null;
		try {
			UUID SecSessionId = Buff.getRequiredSecSessionId();
			UUID SecUserId = Buff.getRequiredSecUserId();
			String SecDevName = Buff.getOptionalSecDevName();
			Calendar Start = Buff.getRequiredStart();
			Calendar Finish = Buff.getOptionalFinish();
			UUID SecProxyId = Buff.getOptionalSecProxyId();
			int Revision = Buff.getRequiredRevision();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_update_secsess( ?, ?, ?, ?, ?, ?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"timestamp( ? )" + ", "
					+	"timestamp( ? )" + ", "
					+	"?" + ", "
					+ "?" + " )";
			if( stmtUpdateByPKey == null ) {
				stmtUpdateByPKey = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtUpdateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtUpdateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtUpdateByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtUpdateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtUpdateByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtUpdateByPKey.setString( argIdx++, "a010" );
			stmtUpdateByPKey.setString( argIdx++, SecSessionId.toString() );
			stmtUpdateByPKey.setString( argIdx++, SecUserId.toString() );
			if( SecDevName != null ) {
				stmtUpdateByPKey.setString( argIdx++, SecDevName );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtUpdateByPKey.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Start ) );
			if( Finish != null ) {
				stmtUpdateByPKey.setString( argIdx++, CFSecMySqlSchema.getTimestampString( Finish ) );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( SecProxyId != null ) {
				stmtUpdateByPKey.setString( argIdx++, SecProxyId.toString() );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtUpdateByPKey.setInt( argIdx++, Revision );
			try {
				resultSet = stmtUpdateByPKey.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecSessionBuff updatedBuff = unpackSecSessionResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredSecUserId( updatedBuff.getRequiredSecUserId() );
				Buff.setOptionalSecDevName( updatedBuff.getOptionalSecDevName() );
				Buff.setRequiredStart( updatedBuff.getRequiredStart() );
				Buff.setOptionalFinish( updatedBuff.getOptionalFinish() );
				Buff.setOptionalSecProxyId( updatedBuff.getOptionalSecProxyId() );
				Buff.setRequiredRevision( updatedBuff.getRequiredRevision() );
			}
			else {
				throw new CFLibRuntimeException( getClass(),
					S_ProcName,
					"Expected a single-record response, " + resultSet.getRow() + " rows selected" );
			}
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSession( CFSecAuthorization Authorization,
		CFSecSecSessionBuff Buff )
	{
		final String S_ProcName = "deleteSecSession";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			UUID SecSessionId = Buff.getRequiredSecSessionId();

			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtDeleteByPKey == null ) {
				stmtDeleteByPKey = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByPKey.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPKey.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByPKey.setString( argIdx++, SecSessionId.toString() );
			stmtDeleteByPKey.setInt( argIdx++, Buff.getRequiredRevision() );;
			stmtDeleteByPKey.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionByIdIdx( CFSecAuthorization Authorization,
		UUID argSecSessionId )
	{
		final String S_ProcName = "deleteSecSessionByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess_by_ididx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByIdIdx == null ) {
				stmtDeleteByIdIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByIdIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByIdIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByIdIdx.setString( argIdx++, argSecSessionId.toString() );
			stmtDeleteByIdIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionByIdIdx( CFSecAuthorization Authorization,
		CFSecSecSessionPKey argKey )
	{
		deleteSecSessionByIdIdx( Authorization,
			argKey.getRequiredSecSessionId() );
	}

	public void deleteSecSessionBySecUserIdx( CFSecAuthorization Authorization,
		UUID argSecUserId )
	{
		final String S_ProcName = "deleteSecSessionBySecUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess_by_secuseridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteBySecUserIdx == null ) {
				stmtDeleteBySecUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteBySecUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteBySecUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteBySecUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteBySecUserIdx.setString( argIdx++, argSecUserId.toString() );
			stmtDeleteBySecUserIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionBySecUserIdx( CFSecAuthorization Authorization,
		CFSecSecSessionBySecUserIdxKey argKey )
	{
		deleteSecSessionBySecUserIdx( Authorization,
			argKey.getRequiredSecUserId() );
	}

	public void deleteSecSessionBySecDevIdx( CFSecAuthorization Authorization,
		UUID argSecUserId,
		String argSecDevName )
	{
		final String S_ProcName = "deleteSecSessionBySecDevIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess_by_secdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtDeleteBySecDevIdx == null ) {
				stmtDeleteBySecDevIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteBySecDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteBySecDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteBySecDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteBySecDevIdx.setString( argIdx++, argSecUserId.toString() );
			if( argSecDevName != null ) {
				stmtDeleteBySecDevIdx.setString( argIdx++, argSecDevName );
			}
			else {
				stmtDeleteBySecDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteBySecDevIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionBySecDevIdx( CFSecAuthorization Authorization,
		CFSecSecSessionBySecDevIdxKey argKey )
	{
		deleteSecSessionBySecDevIdx( Authorization,
			argKey.getRequiredSecUserId(),
			argKey.getOptionalSecDevName() );
	}

	public void deleteSecSessionByStartIdx( CFSecAuthorization Authorization,
		UUID argSecUserId,
		Calendar argStart )
	{
		final String S_ProcName = "deleteSecSessionByStartIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess_by_startidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"timestamp( ? )" + " )";
			if( stmtDeleteByStartIdx == null ) {
				stmtDeleteByStartIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByStartIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByStartIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByStartIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByStartIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByStartIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByStartIdx.setString( argIdx++, argSecUserId.toString() );
			stmtDeleteByStartIdx.setString( argIdx++, CFSecMySqlSchema.getTimestampString( argStart ) );
			stmtDeleteByStartIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionByStartIdx( CFSecAuthorization Authorization,
		CFSecSecSessionByStartIdxKey argKey )
	{
		deleteSecSessionByStartIdx( Authorization,
			argKey.getRequiredSecUserId(),
			argKey.getRequiredStart() );
	}

	public void deleteSecSessionByFinishIdx( CFSecAuthorization Authorization,
		UUID argSecUserId,
		Calendar argFinish )
	{
		final String S_ProcName = "deleteSecSessionByFinishIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess_by_finishidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"timestamp( ? )" + " )";
			if( stmtDeleteByFinishIdx == null ) {
				stmtDeleteByFinishIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByFinishIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByFinishIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByFinishIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByFinishIdx.setString( argIdx++, argSecUserId.toString() );
			if( argFinish != null ) {
				stmtDeleteByFinishIdx.setString( argIdx++, CFSecMySqlSchema.getTimestampString( argFinish ) );
			}
			else {
				stmtDeleteByFinishIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteByFinishIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionByFinishIdx( CFSecAuthorization Authorization,
		CFSecSecSessionByFinishIdxKey argKey )
	{
		deleteSecSessionByFinishIdx( Authorization,
			argKey.getRequiredSecUserId(),
			argKey.getOptionalFinish() );
	}

	public void deleteSecSessionBySecProxyIdx( CFSecAuthorization Authorization,
		UUID argSecProxyId )
	{
		final String S_ProcName = "deleteSecSessionBySecProxyIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secsess_by_secproxyidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteBySecProxyIdx == null ) {
				stmtDeleteBySecProxyIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteBySecProxyIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteBySecProxyIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteBySecProxyIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( argSecProxyId != null ) {
				stmtDeleteBySecProxyIdx.setString( argIdx++, argSecProxyId.toString() );
			}
			else {
				stmtDeleteBySecProxyIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteBySecProxyIdx.executeUpdate();
		}
		catch( SQLException e ) {
			throw new CFLibDbException( getClass(),
				S_ProcName,
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

	public void deleteSecSessionBySecProxyIdx( CFSecAuthorization Authorization,
		CFSecSecSessionBySecProxyIdxKey argKey )
	{
		deleteSecSessionBySecProxyIdx( Authorization,
			argKey.getOptionalSecProxyId() );
	}

	/**
	 *	Release the prepared statements.
	 *	<p>
	 *	When the schema changes connections, the prepared statements
	 *	have to be released because they contain connection-specific
	 *	information for most databases.
	 */
	public void releasePreparedStatements() {
		S_sqlSelectSecSessionDistinctClassCode = null;
		S_sqlSelectSecSessionBuff = null;
		if( stmtReadBuffByPKey != null ) {
			try {
				stmtReadBuffByPKey.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByPKey = null;
		}
		if( stmtLockBuffByPKey != null ) {
			try {
				stmtLockBuffByPKey.close();
			}
			catch( SQLException e ) {
			}
			stmtLockBuffByPKey = null;
		}
		if( stmtCreateByPKey != null ) {
			try {
				stmtCreateByPKey.close();
			}
			catch( SQLException e ) {
			}
			stmtCreateByPKey = null;
		}
		if( stmtUpdateByPKey != null ) {
			try {
				stmtUpdateByPKey.close();
			}
			catch( SQLException e ) {
			}
			stmtUpdateByPKey = null;
		}
		if( stmtDeleteByPKey != null ) {
			try {
				stmtDeleteByPKey.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByPKey = null;
		}
		if( stmtDeleteByIdIdx != null ) {
			try {
				stmtDeleteByIdIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByIdIdx = null;
		}
		if( stmtDeleteBySecUserIdx != null ) {
			try {
				stmtDeleteBySecUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteBySecUserIdx = null;
		}
		if( stmtDeleteBySecDevIdx != null ) {
			try {
				stmtDeleteBySecDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteBySecDevIdx = null;
		}
		if( stmtDeleteByStartIdx != null ) {
			try {
				stmtDeleteByStartIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByStartIdx = null;
		}
		if( stmtDeleteByFinishIdx != null ) {
			try {
				stmtDeleteByFinishIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByFinishIdx = null;
		}
		if( stmtDeleteBySecProxyIdx != null ) {
			try {
				stmtDeleteBySecProxyIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteBySecProxyIdx = null;
		}
		if( stmtReadAllBuff != null ) {
			try {
				stmtReadAllBuff .close();
			}
			catch( SQLException e ) {
			}
			stmtReadAllBuff = null;
		}
		if( stmtPageAllBuff != null ) {
			try {
				stmtPageAllBuff .close();
			}
			catch( SQLException e ) {
			}
			stmtPageAllBuff = null;
		}
		if( stmtReadBuffByIdIdx != null ) {
			try {
				stmtReadBuffByIdIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByIdIdx = null;
		}
		if( stmtReadBuffBySecUserIdx != null ) {
			try {
				stmtReadBuffBySecUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffBySecUserIdx = null;
		}
		if( stmtPageBuffBySecUserIdx != null ) {
			try {
				stmtPageBuffBySecUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffBySecUserIdx = null;
		}
		if( stmtReadBuffBySecDevIdx != null ) {
			try {
				stmtReadBuffBySecDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffBySecDevIdx = null;
		}
		if( stmtPageBuffBySecDevIdx != null ) {
			try {
				stmtPageBuffBySecDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffBySecDevIdx = null;
		}
		if( stmtReadBuffByStartIdx != null ) {
			try {
				stmtReadBuffByStartIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByStartIdx = null;
		}
		if( stmtReadBuffByFinishIdx != null ) {
			try {
				stmtReadBuffByFinishIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByFinishIdx = null;
		}
		if( stmtPageBuffByFinishIdx != null ) {
			try {
				stmtPageBuffByFinishIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByFinishIdx = null;
		}
		if( stmtReadBuffBySecProxyIdx != null ) {
			try {
				stmtReadBuffBySecProxyIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffBySecProxyIdx = null;
		}
		if( stmtPageBuffBySecProxyIdx != null ) {
			try {
				stmtPageBuffBySecProxyIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffBySecProxyIdx = null;
		}
	}
}
