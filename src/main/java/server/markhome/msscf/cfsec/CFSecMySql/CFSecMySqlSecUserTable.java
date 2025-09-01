// Description: Java 11 MySQL Jdbc DbIO implementation for SecUser.

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
 *	CFSecMySqlSecUserTable MySQL Jdbc DbIO implementation
 *	for SecUser.
 */
public class CFSecMySqlSecUserTable
	implements ICFSecSecUserTable
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
	protected PreparedStatement stmtReadBuffByULoginIdx = null;
	protected PreparedStatement stmtReadBuffByEMConfIdx = null;
	protected PreparedStatement stmtPageBuffByEMConfIdx = null;
	protected PreparedStatement stmtReadBuffByPwdResetIdx = null;
	protected PreparedStatement stmtPageBuffByPwdResetIdx = null;
	protected PreparedStatement stmtReadBuffByDefDevIdx = null;
	protected PreparedStatement stmtPageBuffByDefDevIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByULoginIdx = null;
	protected PreparedStatement stmtDeleteByEMConfIdx = null;
	protected PreparedStatement stmtDeleteByPwdResetIdx = null;
	protected PreparedStatement stmtDeleteByDefDevIdx = null;

	public CFSecMySqlSecUserTable( CFSecMySqlSchema argSchema ) {
		schema = argSchema;
	}

	public void createSecUser( CFSecAuthorization Authorization,
		CFSecSecUserBuff Buff )
	{
		final String S_ProcName = "createSecUser";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			String LoginId = Buff.getRequiredLoginId();
			String EMailAddress = Buff.getRequiredEMailAddress();
			UUID EMailConfirmUuid = Buff.getOptionalEMailConfirmUuid();
			UUID DfltDevUserId = Buff.getOptionalDfltDevUserId();
			String DfltDevName = Buff.getOptionalDfltDevName();
			String PasswordHash = Buff.getRequiredPasswordHash();
			UUID PasswordResetUuid = Buff.getOptionalPasswordResetUuid();

			UUID SecUserId = UUID.randomUUID();			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_create_secuser( ?, ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a011" );
			stmtCreateByPKey.setString( argIdx++, SecUserId.toString() );
			stmtCreateByPKey.setString( argIdx++, LoginId );
			stmtCreateByPKey.setString( argIdx++, EMailAddress );
			if( EMailConfirmUuid != null ) {
				stmtCreateByPKey.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevUserId != null ) {
				stmtCreateByPKey.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtCreateByPKey.setString( argIdx++, DfltDevName );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtCreateByPKey.setString( argIdx++, PasswordHash );
			if( PasswordResetUuid != null ) {
				stmtCreateByPKey.setString( argIdx++, PasswordResetUuid.toString() );
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
				CFSecSecUserBuff createdBuff = unpackSecUserResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredSecUserId( createdBuff.getRequiredSecUserId() );
				Buff.setRequiredLoginId( createdBuff.getRequiredLoginId() );
				Buff.setRequiredEMailAddress( createdBuff.getRequiredEMailAddress() );
				Buff.setOptionalEMailConfirmUuid( createdBuff.getOptionalEMailConfirmUuid() );
				Buff.setOptionalDfltDevUserId( createdBuff.getOptionalDfltDevUserId() );
				Buff.setOptionalDfltDevName( createdBuff.getOptionalDfltDevName() );
				Buff.setRequiredPasswordHash( createdBuff.getRequiredPasswordHash() );
				Buff.setOptionalPasswordResetUuid( createdBuff.getOptionalPasswordResetUuid() );
				Buff.setRequiredRevision( createdBuff.getRequiredRevision() );
				Buff.setCreatedByUserId( createdBuff.getCreatedByUserId() );
				Buff.setCreatedAt( createdBuff.getCreatedAt() );
				Buff.setUpdatedByUserId( createdBuff.getUpdatedByUserId() );
				Buff.setUpdatedAt( createdBuff.getUpdatedAt() );
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

	protected static String S_sqlSelectSecUserDistinctClassCode = null;

	public String getSqlSelectSecUserDistinctClassCode() {
		if( S_sqlSelectSecUserDistinctClassCode == null ) {
			S_sqlSelectSecUserDistinctClassCode =
					"SELECT "
				+		"DISTINCT a011.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecUser AS a011 ";
		}
		return( S_sqlSelectSecUserDistinctClassCode );
	}

	protected static String S_sqlSelectSecUserBuff = null;

	public String getSqlSelectSecUserBuff() {
		if( S_sqlSelectSecUserBuff == null ) {
			S_sqlSelectSecUserBuff =
					"SELECT "
				+		"a011.SecUserId, "
				+		"a011.login_id, "
				+		"a011.email_addr, "
				+		"a011.em_confuuid, "
				+		"a011.DefDevUserId, "
				+		"a011.DefDevName, "
				+		"a011.pwd_hash, "
				+		"a011.pwdrstuuid, "
				+		"a011.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecUser AS a011 ";
		}
		return( S_sqlSelectSecUserBuff );
	}

	protected CFSecSecUserBuff unpackSecUserResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackSecUserResultSetToBuff";
		int idxcol = 1;
		CFSecSecUserBuff buff = schema.getFactorySecUser().newBuff();
		{
			String colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setCreatedAt( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setCreatedAt( null );
			}
			else {
				buff.setCreatedAt( CFSecMySqlSchema.convertTimestampString( colString ) );
			}
		}
		idxcol++;
		{
			String colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setCreatedByUserId( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setCreatedByUserId( null );
			}
			else {
				buff.setCreatedByUserId( UUID.fromString( colString ) );
			}
		}
		idxcol++;
		{
			String colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setUpdatedAt( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setUpdatedAt( null );
			}
			else {
				buff.setUpdatedAt( CFSecMySqlSchema.convertTimestampString( colString ) );
			}
		}
		idxcol++;
		{
			String colString = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setUpdatedByUserId( null );
			}
			else if( ( colString == null ) || ( colString.length() <= 0 ) ) {
				buff.setUpdatedByUserId( null );
			}
			else {
				buff.setUpdatedByUserId( UUID.fromString( colString ) );
			}
		}
		idxcol++;		buff.setRequiredSecUserId( CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) ) );
		idxcol++;
		buff.setRequiredLoginId( resultSet.getString( idxcol ) );
		idxcol++;
		buff.setRequiredEMailAddress( resultSet.getString( idxcol ) );
		idxcol++;
		{
			UUID colVal = CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalEMailConfirmUuid( null );
			}
			else {
				buff.setOptionalEMailConfirmUuid( colVal );
			}
		}
		idxcol++;
		{
			UUID colVal = CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalDfltDevUserId( null );
			}
			else {
				buff.setOptionalDfltDevUserId( colVal );
			}
		}
		idxcol++;
		{
			String colVal = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setOptionalDfltDevName( null );
			}
			else {
				buff.setOptionalDfltDevName( colVal );
			}
		}
		idxcol++;
		buff.setRequiredPasswordHash( resultSet.getString( idxcol ) );
		idxcol++;
		{
			UUID colVal = CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) );
			if( resultSet.wasNull() ) {
				buff.setOptionalPasswordResetUuid( null );
			}
			else {
				buff.setOptionalPasswordResetUuid( colVal );
			}
		}
		idxcol++;

		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecSecUserBuff readDerived( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecUserBuff lockDerived( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecUserBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecSecUserBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecSecUserBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "CFSecMySqlSecUserTable.readDerivedByIdIdx() ";
		CFSecSecUserBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				SecUserId );
		return( buff );
	}

	public CFSecSecUserBuff readDerivedByULoginIdx( CFSecAuthorization Authorization,
		String LoginId )
	{
		final String S_ProcName = "CFSecMySqlSecUserTable.readDerivedByULoginIdx() ";
		CFSecSecUserBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByULoginIdx( Authorization,
				LoginId );
		return( buff );
	}

	public CFSecSecUserBuff[] readDerivedByEMConfIdx( CFSecAuthorization Authorization,
		UUID EMailConfirmUuid )
	{
		final String S_ProcName = "readDerivedByEMConfIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff[] buffList = readBuffByEMConfIdx( Authorization,
				EMailConfirmUuid );
		return( buffList );

	}

	public CFSecSecUserBuff[] readDerivedByPwdResetIdx( CFSecAuthorization Authorization,
		UUID PasswordResetUuid )
	{
		final String S_ProcName = "readDerivedByPwdResetIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff[] buffList = readBuffByPwdResetIdx( Authorization,
				PasswordResetUuid );
		return( buffList );

	}

	public CFSecSecUserBuff[] readDerivedByDefDevIdx( CFSecAuthorization Authorization,
		UUID DfltDevUserId,
		String DfltDevName )
	{
		final String S_ProcName = "readDerivedByDefDevIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecUserBuff[] buffList = readBuffByDefDevIdx( Authorization,
				DfltDevUserId,
				DfltDevName );
		return( buffList );

	}

	public CFSecSecUserBuff readBuff( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
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
			UUID SecUserId = PKey.getRequiredSecUserId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser( ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByPKey.setString( argIdx++, SecUserId.toString() );
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
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff lockBuff( CFSecAuthorization Authorization,
		CFSecSecUserPKey PKey )
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
			UUID SecUserId = PKey.getRequiredSecUserId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_lock_secuser( ?, ?, ?, ?, ?" + ", "
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
			stmtLockBuffByPKey.setString( argIdx++, SecUserId.toString() );
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
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser_all( ?, ?, ?, ?, ? )";
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
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
	 *	Read a page of all the specific SecUser buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecUser instances in the database accessible for the Authorization.
	 */
	public CFSecSecUserBuff[] pageAllBuff( CFSecAuthorization Authorization,
		UUID priorSecUserId )
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
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secuser_all( ?, ?, ?, ?, ?, ? )";
			if( stmtPageAllBuff == null ) {
				stmtPageAllBuff = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( priorSecUserId != null ) {
				stmtPageAllBuff.setString( argIdx++, priorSecUserId.toString() );
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
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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

	public CFSecSecUserBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser_by_ididx( ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByIdIdx.setString( argIdx++, SecUserId.toString() );
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
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff readBuffByULoginIdx( CFSecAuthorization Authorization,
		String LoginId )
	{
		final String S_ProcName = "readBuffByULoginIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser_by_uloginidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByULoginIdx == null ) {
				stmtReadBuffByULoginIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByULoginIdx.setString( argIdx++, LoginId );
			try {
				resultSet = stmtReadBuffByULoginIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
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

	public CFSecSecUserBuff[] readBuffByEMConfIdx( CFSecAuthorization Authorization,
		UUID EMailConfirmUuid )
	{
		final String S_ProcName = "readBuffByEMConfIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser_by_emconfidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByEMConfIdx == null ) {
				stmtReadBuffByEMConfIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( EMailConfirmUuid != null ) {
				stmtReadBuffByEMConfIdx.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtReadBuffByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffByEMConfIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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

	public CFSecSecUserBuff[] readBuffByPwdResetIdx( CFSecAuthorization Authorization,
		UUID PasswordResetUuid )
	{
		final String S_ProcName = "readBuffByPwdResetIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser_by_pwdresetidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByPwdResetIdx == null ) {
				stmtReadBuffByPwdResetIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( PasswordResetUuid != null ) {
				stmtReadBuffByPwdResetIdx.setString( argIdx++, PasswordResetUuid.toString() );
			}
			else {
				stmtReadBuffByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffByPwdResetIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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

	public CFSecSecUserBuff[] readBuffByDefDevIdx( CFSecAuthorization Authorization,
		UUID DfltDevUserId,
		String DfltDevName )
	{
		final String S_ProcName = "readBuffByDefDevIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secuser_by_defdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByDefDevIdx == null ) {
				stmtReadBuffByDefDevIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( DfltDevUserId != null ) {
				stmtReadBuffByDefDevIdx.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtReadBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtReadBuffByDefDevIdx.setString( argIdx++, DfltDevName );
			}
			else {
				stmtReadBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffByDefDevIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific SecUser buffer instances identified by the duplicate key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argEMailConfirmUuid	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecUserBuff[] pageBuffByEMConfIdx( CFSecAuthorization Authorization,
		UUID EMailConfirmUuid,
		UUID priorSecUserId )
	{
		final String S_ProcName = "pageBuffByEMConfIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secuser_by_emconfidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffByEMConfIdx == null ) {
				stmtPageBuffByEMConfIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( EMailConfirmUuid != null ) {
				stmtPageBuffByEMConfIdx.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtPageBuffByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecUserId != null ) {
				stmtPageBuffByEMConfIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffByEMConfIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific SecUser buffer instances identified by the duplicate key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argPasswordResetUuid	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecUserBuff[] pageBuffByPwdResetIdx( CFSecAuthorization Authorization,
		UUID PasswordResetUuid,
		UUID priorSecUserId )
	{
		final String S_ProcName = "pageBuffByPwdResetIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secuser_by_pwdresetidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffByPwdResetIdx == null ) {
				stmtPageBuffByPwdResetIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( PasswordResetUuid != null ) {
				stmtPageBuffByPwdResetIdx.setString( argIdx++, PasswordResetUuid.toString() );
			}
			else {
				stmtPageBuffByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecUserId != null ) {
				stmtPageBuffByPwdResetIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffByPwdResetIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific SecUser buffer instances identified by the duplicate key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argDfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	argDfltDevName	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecUserBuff[] pageBuffByDefDevIdx( CFSecAuthorization Authorization,
		UUID DfltDevUserId,
		String DfltDevName,
		UUID priorSecUserId )
	{
		final String S_ProcName = "pageBuffByDefDevIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secuser_by_defdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffByDefDevIdx == null ) {
				stmtPageBuffByDefDevIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( DfltDevUserId != null ) {
				stmtPageBuffByDefDevIdx.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtPageBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtPageBuffByDefDevIdx.setString( argIdx++, DfltDevName );
			}
			else {
				stmtPageBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorSecUserId != null ) {
				stmtPageBuffByDefDevIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtPageBuffByDefDevIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecSecUserBuff> buffList = new LinkedList<CFSecSecUserBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecUserBuff buff = unpackSecUserResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecUserBuff[] retBuff = new CFSecSecUserBuff[ buffList.size() ];
			Iterator<CFSecSecUserBuff> iter = buffList.iterator();
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

	public void updateSecUser( CFSecAuthorization Authorization,
		CFSecSecUserBuff Buff )
	{
		final String S_ProcName = "updateSecUser";
		ResultSet resultSet = null;
		try {
			UUID SecUserId = Buff.getRequiredSecUserId();
			String LoginId = Buff.getRequiredLoginId();
			String EMailAddress = Buff.getRequiredEMailAddress();
			UUID EMailConfirmUuid = Buff.getOptionalEMailConfirmUuid();
			UUID DfltDevUserId = Buff.getOptionalDfltDevUserId();
			String DfltDevName = Buff.getOptionalDfltDevName();
			String PasswordHash = Buff.getRequiredPasswordHash();
			UUID PasswordResetUuid = Buff.getOptionalPasswordResetUuid();
			int Revision = Buff.getRequiredRevision();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_update_secuser( ?, ?, ?, ?, ?, ?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
					+	"?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a011" );
			stmtUpdateByPKey.setString( argIdx++, SecUserId.toString() );
			stmtUpdateByPKey.setString( argIdx++, LoginId );
			stmtUpdateByPKey.setString( argIdx++, EMailAddress );
			if( EMailConfirmUuid != null ) {
				stmtUpdateByPKey.setString( argIdx++, EMailConfirmUuid.toString() );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevUserId != null ) {
				stmtUpdateByPKey.setString( argIdx++, DfltDevUserId.toString() );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( DfltDevName != null ) {
				stmtUpdateByPKey.setString( argIdx++, DfltDevName );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtUpdateByPKey.setString( argIdx++, PasswordHash );
			if( PasswordResetUuid != null ) {
				stmtUpdateByPKey.setString( argIdx++, PasswordResetUuid.toString() );
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
				CFSecSecUserBuff updatedBuff = unpackSecUserResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredLoginId( updatedBuff.getRequiredLoginId() );
				Buff.setRequiredEMailAddress( updatedBuff.getRequiredEMailAddress() );
				Buff.setOptionalEMailConfirmUuid( updatedBuff.getOptionalEMailConfirmUuid() );
				Buff.setOptionalDfltDevUserId( updatedBuff.getOptionalDfltDevUserId() );
				Buff.setOptionalDfltDevName( updatedBuff.getOptionalDfltDevName() );
				Buff.setRequiredPasswordHash( updatedBuff.getRequiredPasswordHash() );
				Buff.setOptionalPasswordResetUuid( updatedBuff.getOptionalPasswordResetUuid() );
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

	public void deleteSecUser( CFSecAuthorization Authorization,
		CFSecSecUserBuff Buff )
	{
		final String S_ProcName = "deleteSecUser";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			UUID SecUserId = Buff.getRequiredSecUserId();

			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secuser( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByPKey.setString( argIdx++, SecUserId.toString() );
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

	public void deleteSecUserByIdIdx( CFSecAuthorization Authorization,
		UUID argSecUserId )
	{
		final String S_ProcName = "deleteSecUserByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secuser_by_ididx( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByIdIdx.setString( argIdx++, argSecUserId.toString() );
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

	public void deleteSecUserByIdIdx( CFSecAuthorization Authorization,
		CFSecSecUserPKey argKey )
	{
		deleteSecUserByIdIdx( Authorization,
			argKey.getRequiredSecUserId() );
	}

	public void deleteSecUserByULoginIdx( CFSecAuthorization Authorization,
		String argLoginId )
	{
		final String S_ProcName = "deleteSecUserByULoginIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secuser_by_uloginidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByULoginIdx == null ) {
				stmtDeleteByULoginIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByULoginIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByULoginIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByULoginIdx.setString( argIdx++, argLoginId );
			stmtDeleteByULoginIdx.executeUpdate();
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

	public void deleteSecUserByULoginIdx( CFSecAuthorization Authorization,
		CFSecSecUserByULoginIdxKey argKey )
	{
		deleteSecUserByULoginIdx( Authorization,
			argKey.getRequiredLoginId() );
	}

	public void deleteSecUserByEMConfIdx( CFSecAuthorization Authorization,
		UUID argEMailConfirmUuid )
	{
		final String S_ProcName = "deleteSecUserByEMConfIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secuser_by_emconfidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByEMConfIdx == null ) {
				stmtDeleteByEMConfIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByEMConfIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByEMConfIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( argEMailConfirmUuid != null ) {
				stmtDeleteByEMConfIdx.setString( argIdx++, argEMailConfirmUuid.toString() );
			}
			else {
				stmtDeleteByEMConfIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteByEMConfIdx.executeUpdate();
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

	public void deleteSecUserByEMConfIdx( CFSecAuthorization Authorization,
		CFSecSecUserByEMConfIdxKey argKey )
	{
		deleteSecUserByEMConfIdx( Authorization,
			argKey.getOptionalEMailConfirmUuid() );
	}

	public void deleteSecUserByPwdResetIdx( CFSecAuthorization Authorization,
		UUID argPasswordResetUuid )
	{
		final String S_ProcName = "deleteSecUserByPwdResetIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secuser_by_pwdresetidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByPwdResetIdx == null ) {
				stmtDeleteByPwdResetIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByPwdResetIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByPwdResetIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( argPasswordResetUuid != null ) {
				stmtDeleteByPwdResetIdx.setString( argIdx++, argPasswordResetUuid.toString() );
			}
			else {
				stmtDeleteByPwdResetIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteByPwdResetIdx.executeUpdate();
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

	public void deleteSecUserByPwdResetIdx( CFSecAuthorization Authorization,
		CFSecSecUserByPwdResetIdxKey argKey )
	{
		deleteSecUserByPwdResetIdx( Authorization,
			argKey.getOptionalPasswordResetUuid() );
	}

	public void deleteSecUserByDefDevIdx( CFSecAuthorization Authorization,
		UUID argDfltDevUserId,
		String argDfltDevName )
	{
		final String S_ProcName = "deleteSecUserByDefDevIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secuser_by_defdevidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtDeleteByDefDevIdx == null ) {
				stmtDeleteByDefDevIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByDefDevIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByDefDevIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( argDfltDevUserId != null ) {
				stmtDeleteByDefDevIdx.setString( argIdx++, argDfltDevUserId.toString() );
			}
			else {
				stmtDeleteByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( argDfltDevName != null ) {
				stmtDeleteByDefDevIdx.setString( argIdx++, argDfltDevName );
			}
			else {
				stmtDeleteByDefDevIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteByDefDevIdx.executeUpdate();
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

	public void deleteSecUserByDefDevIdx( CFSecAuthorization Authorization,
		CFSecSecUserByDefDevIdxKey argKey )
	{
		deleteSecUserByDefDevIdx( Authorization,
			argKey.getOptionalDfltDevUserId(),
			argKey.getOptionalDfltDevName() );
	}

	/**
	 *	Release the prepared statements.
	 *	<p>
	 *	When the schema changes connections, the prepared statements
	 *	have to be released because they contain connection-specific
	 *	information for most databases.
	 */
	public void releasePreparedStatements() {
		S_sqlSelectSecUserDistinctClassCode = null;
		S_sqlSelectSecUserBuff = null;
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
		if( stmtDeleteByULoginIdx != null ) {
			try {
				stmtDeleteByULoginIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByULoginIdx = null;
		}
		if( stmtDeleteByEMConfIdx != null ) {
			try {
				stmtDeleteByEMConfIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByEMConfIdx = null;
		}
		if( stmtDeleteByPwdResetIdx != null ) {
			try {
				stmtDeleteByPwdResetIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByPwdResetIdx = null;
		}
		if( stmtDeleteByDefDevIdx != null ) {
			try {
				stmtDeleteByDefDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByDefDevIdx = null;
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
		if( stmtReadBuffByULoginIdx != null ) {
			try {
				stmtReadBuffByULoginIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByULoginIdx = null;
		}
		if( stmtReadBuffByEMConfIdx != null ) {
			try {
				stmtReadBuffByEMConfIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByEMConfIdx = null;
		}
		if( stmtPageBuffByEMConfIdx != null ) {
			try {
				stmtPageBuffByEMConfIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByEMConfIdx = null;
		}
		if( stmtReadBuffByPwdResetIdx != null ) {
			try {
				stmtReadBuffByPwdResetIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByPwdResetIdx = null;
		}
		if( stmtPageBuffByPwdResetIdx != null ) {
			try {
				stmtPageBuffByPwdResetIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByPwdResetIdx = null;
		}
		if( stmtReadBuffByDefDevIdx != null ) {
			try {
				stmtReadBuffByDefDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByDefDevIdx = null;
		}
		if( stmtPageBuffByDefDevIdx != null ) {
			try {
				stmtPageBuffByDefDevIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByDefDevIdx = null;
		}
	}
}
