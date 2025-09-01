// Description: Java 11 MySQL Jdbc DbIO implementation for TSecGrpMemb.

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
 *	CFSecMySqlTSecGrpMembTable MySQL Jdbc DbIO implementation
 *	for TSecGrpMemb.
 */
public class CFSecMySqlTSecGrpMembTable
	implements ICFSecTSecGrpMembTable
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
	protected PreparedStatement stmtReadBuffByTenantIdx = null;
	protected PreparedStatement stmtPageBuffByTenantIdx = null;
	protected PreparedStatement stmtReadBuffByGroupIdx = null;
	protected PreparedStatement stmtPageBuffByGroupIdx = null;
	protected PreparedStatement stmtReadBuffByUserIdx = null;
	protected PreparedStatement stmtPageBuffByUserIdx = null;
	protected PreparedStatement stmtReadBuffByUUserIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByTenantIdx = null;
	protected PreparedStatement stmtDeleteByGroupIdx = null;
	protected PreparedStatement stmtDeleteByUserIdx = null;
	protected PreparedStatement stmtDeleteByUUserIdx = null;

	public CFSecMySqlTSecGrpMembTable( CFSecMySqlSchema argSchema ) {
		schema = argSchema;
	}

	public void createTSecGrpMemb( CFSecAuthorization Authorization,
		CFSecTSecGrpMembBuff Buff )
	{
		final String S_ProcName = "createTSecGrpMemb";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			long TenantId = Buff.getRequiredTenantId();
			int TSecGroupId = Buff.getRequiredTSecGroupId();
			UUID SecUserId = Buff.getRequiredSecUserId();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_create_tsecmemb( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a018" );
			stmtCreateByPKey.setLong( argIdx++, TenantId );
			stmtCreateByPKey.setInt( argIdx++, TSecGroupId );
			stmtCreateByPKey.setString( argIdx++, SecUserId.toString() );
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
				CFSecTSecGrpMembBuff createdBuff = unpackTSecGrpMembResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredTenantId( createdBuff.getRequiredTenantId() );
				Buff.setRequiredTSecGrpMembId( createdBuff.getRequiredTSecGrpMembId() );
				Buff.setRequiredTSecGroupId( createdBuff.getRequiredTSecGroupId() );
				Buff.setRequiredSecUserId( createdBuff.getRequiredSecUserId() );
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

	protected static String S_sqlSelectTSecGrpMembDistinctClassCode = null;

	public String getSqlSelectTSecGrpMembDistinctClassCode() {
		if( S_sqlSelectTSecGrpMembDistinctClassCode == null ) {
			S_sqlSelectTSecGrpMembDistinctClassCode =
					"SELECT "
				+		"DISTINCT a018.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".TSecMemb AS a018 ";
		}
		return( S_sqlSelectTSecGrpMembDistinctClassCode );
	}

	protected static String S_sqlSelectTSecGrpMembBuff = null;

	public String getSqlSelectTSecGrpMembBuff() {
		if( S_sqlSelectTSecGrpMembBuff == null ) {
			S_sqlSelectTSecGrpMembBuff =
					"SELECT "
				+		"a018.TenantId, "
				+		"a018.TSecGrpMembId, "
				+		"a018.TSecGroupId, "
				+		"a018.SecUserId, "
				+		"a018.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".TSecMemb AS a018 ";
		}
		return( S_sqlSelectTSecGrpMembBuff );
	}

	protected CFSecTSecGrpMembBuff unpackTSecGrpMembResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackTSecGrpMembResultSetToBuff";
		int idxcol = 1;
		CFSecTSecGrpMembBuff buff = schema.getFactoryTSecGrpMemb().newBuff();
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
		idxcol++;		buff.setRequiredTenantId( resultSet.getLong( idxcol ) );
		idxcol++;
		buff.setRequiredTSecGrpMembId( resultSet.getLong( idxcol ) );
		idxcol++;
		buff.setRequiredTSecGroupId( resultSet.getInt( idxcol ) );
		idxcol++;
		buff.setRequiredSecUserId( CFSecMySqlSchema.convertUuidString( resultSet.getString( idxcol ) ) );
		idxcol++;

		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecTSecGrpMembBuff readDerived( CFSecAuthorization Authorization,
		CFSecTSecGrpMembPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecTSecGrpMembBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecTSecGrpMembBuff lockDerived( CFSecAuthorization Authorization,
		CFSecTSecGrpMembPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecTSecGrpMembBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecTSecGrpMembBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecTSecGrpMembBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecTSecGrpMembBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		long TSecGrpMembId )
	{
		final String S_ProcName = "CFSecMySqlTSecGrpMembTable.readDerivedByIdIdx() ";
		CFSecTSecGrpMembBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				TenantId,
				TSecGrpMembId );
		return( buff );
	}

	public CFSecTSecGrpMembBuff[] readDerivedByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "readDerivedByTenantIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecTSecGrpMembBuff[] buffList = readBuffByTenantIdx( Authorization,
				TenantId );
		return( buffList );

	}

	public CFSecTSecGrpMembBuff[] readDerivedByGroupIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId )
	{
		final String S_ProcName = "readDerivedByGroupIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecTSecGrpMembBuff[] buffList = readBuffByGroupIdx( Authorization,
				TenantId,
				TSecGroupId );
		return( buffList );

	}

	public CFSecTSecGrpMembBuff[] readDerivedByUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readDerivedByUserIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecTSecGrpMembBuff[] buffList = readBuffByUserIdx( Authorization,
				SecUserId );
		return( buffList );

	}

	public CFSecTSecGrpMembBuff readDerivedByUUserIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId,
		UUID SecUserId )
	{
		final String S_ProcName = "CFSecMySqlTSecGrpMembTable.readDerivedByUUserIdx() ";
		CFSecTSecGrpMembBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByUUserIdx( Authorization,
				TenantId,
				TSecGroupId,
				SecUserId );
		return( buff );
	}

	public CFSecTSecGrpMembBuff readBuff( CFSecAuthorization Authorization,
		CFSecTSecGrpMembPKey PKey )
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
			long TenantId = PKey.getRequiredTenantId();
			long TSecGrpMembId = PKey.getRequiredTSecGrpMembId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
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
			stmtReadBuffByPKey.setLong( argIdx++, TenantId );
			stmtReadBuffByPKey.setLong( argIdx++, TSecGrpMembId );
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
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
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

	public CFSecTSecGrpMembBuff lockBuff( CFSecAuthorization Authorization,
		CFSecTSecGrpMembPKey PKey )
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
			long TenantId = PKey.getRequiredTenantId();
			long TSecGrpMembId = PKey.getRequiredTSecGrpMembId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_lock_tsecmemb( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
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
			stmtLockBuffByPKey.setLong( argIdx++, TenantId );
			stmtLockBuffByPKey.setLong( argIdx++, TSecGrpMembId );
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
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
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

	public CFSecTSecGrpMembBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb_all( ?, ?, ?, ?, ? )";
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
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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
	 *	Read a page of all the specific TSecGrpMemb buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TSecGrpMemb instances in the database accessible for the Authorization.
	 */
	public CFSecTSecGrpMembBuff[] pageAllBuff( CFSecAuthorization Authorization,
		Long priorTenantId,
		Long priorTSecGrpMembId )
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
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_tsecmemb_all( ?, ?, ?, ?, ?, ?, ? )";
			if( stmtPageAllBuff == null ) {
				stmtPageAllBuff = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageAllBuff.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageAllBuff.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( priorTenantId != null ) {
				stmtPageAllBuff.setLong( argIdx++, priorTenantId.longValue() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorTSecGrpMembId != null ) {
				stmtPageAllBuff.setLong( argIdx++, priorTSecGrpMembId.longValue() );
			}
			else {
				stmtPageAllBuff.setNull( argIdx++, java.sql.Types.BIGINT );
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
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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

	public CFSecTSecGrpMembBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		long TSecGrpMembId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb_by_ididx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
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
			stmtReadBuffByIdIdx.setLong( argIdx++, TenantId );
			stmtReadBuffByIdIdx.setLong( argIdx++, TSecGrpMembId );
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
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
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

	public CFSecTSecGrpMembBuff[] readBuffByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "readBuffByTenantIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb_by_tenantidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByTenantIdx == null ) {
				stmtReadBuffByTenantIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByTenantIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByTenantIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByTenantIdx.setLong( argIdx++, TenantId );
			try {
				resultSet = stmtReadBuffByTenantIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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

	public CFSecTSecGrpMembBuff[] readBuffByGroupIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId )
	{
		final String S_ProcName = "readBuffByGroupIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb_by_groupidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByGroupIdx == null ) {
				stmtReadBuffByGroupIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByGroupIdx.setLong( argIdx++, TenantId );
			stmtReadBuffByGroupIdx.setInt( argIdx++, TSecGroupId );
			try {
				resultSet = stmtReadBuffByGroupIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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

	public CFSecTSecGrpMembBuff[] readBuffByUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readBuffByUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb_by_useridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByUserIdx == null ) {
				stmtReadBuffByUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByUserIdx.setString( argIdx++, SecUserId.toString() );
			try {
				resultSet = stmtReadBuffByUserIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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

	public CFSecTSecGrpMembBuff readBuffByUUserIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId,
		UUID SecUserId )
	{
		final String S_ProcName = "readBuffByUUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_tsecmemb_by_uuseridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByUUserIdx == null ) {
				stmtReadBuffByUUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByUUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByUUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByUUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByUUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByUUserIdx.setLong( argIdx++, TenantId );
			stmtReadBuffByUUserIdx.setInt( argIdx++, TSecGroupId );
			stmtReadBuffByUUserIdx.setString( argIdx++, SecUserId.toString() );
			try {
				resultSet = stmtReadBuffByUUserIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
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

	/**
	 *	Read a page array of the specific TSecGrpMemb buffer instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argTenantId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTSecGrpMembBuff[] pageBuffByTenantIdx( CFSecAuthorization Authorization,
		long TenantId,
		Long priorTenantId,
		Long priorTSecGrpMembId )
	{
		final String S_ProcName = "pageBuffByTenantIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_tsecmemb_by_tenantidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffByTenantIdx == null ) {
				stmtPageBuffByTenantIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByTenantIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByTenantIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByTenantIdx.setLong( argIdx++, TenantId );
			if( priorTenantId != null ) {
				stmtPageBuffByTenantIdx.setLong( argIdx++, priorTenantId.longValue() );
			}
			else {
				stmtPageBuffByTenantIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorTSecGrpMembId != null ) {
				stmtPageBuffByTenantIdx.setLong( argIdx++, priorTSecGrpMembId.longValue() );
			}
			else {
				stmtPageBuffByTenantIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			try {
				resultSet = stmtPageBuffByTenantIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific TSecGrpMemb buffer instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argTenantId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	argTSecGroupId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTSecGrpMembBuff[] pageBuffByGroupIdx( CFSecAuthorization Authorization,
		long TenantId,
		int TSecGroupId,
		Long priorTenantId,
		Long priorTSecGrpMembId )
	{
		final String S_ProcName = "pageBuffByGroupIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_tsecmemb_by_groupidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffByGroupIdx == null ) {
				stmtPageBuffByGroupIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByGroupIdx.setLong( argIdx++, TenantId );
			stmtPageBuffByGroupIdx.setInt( argIdx++, TSecGroupId );
			if( priorTenantId != null ) {
				stmtPageBuffByGroupIdx.setLong( argIdx++, priorTenantId.longValue() );
			}
			else {
				stmtPageBuffByGroupIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorTSecGrpMembId != null ) {
				stmtPageBuffByGroupIdx.setLong( argIdx++, priorTSecGrpMembId.longValue() );
			}
			else {
				stmtPageBuffByGroupIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			try {
				resultSet = stmtPageBuffByGroupIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific TSecGrpMemb buffer instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argSecUserId	The TSecGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTSecGrpMembBuff[] pageBuffByUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		Long priorTenantId,
		Long priorTSecGrpMembId )
	{
		final String S_ProcName = "pageBuffByUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_tsecmemb_by_useridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtPageBuffByUserIdx == null ) {
				stmtPageBuffByUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtPageBuffByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtPageBuffByUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtPageBuffByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtPageBuffByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtPageBuffByUserIdx.setString( argIdx++, SecUserId.toString() );
			if( priorTenantId != null ) {
				stmtPageBuffByUserIdx.setLong( argIdx++, priorTenantId.longValue() );
			}
			else {
				stmtPageBuffByUserIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			if( priorTSecGrpMembId != null ) {
				stmtPageBuffByUserIdx.setLong( argIdx++, priorTSecGrpMembId.longValue() );
			}
			else {
				stmtPageBuffByUserIdx.setNull( argIdx++, java.sql.Types.BIGINT );
			}
			try {
				resultSet = stmtPageBuffByUserIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecTSecGrpMembBuff> buffList = new LinkedList<CFSecTSecGrpMembBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecTSecGrpMembBuff buff = unpackTSecGrpMembResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecTSecGrpMembBuff[] retBuff = new CFSecTSecGrpMembBuff[ buffList.size() ];
			Iterator<CFSecTSecGrpMembBuff> iter = buffList.iterator();
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

	public void updateTSecGrpMemb( CFSecAuthorization Authorization,
		CFSecTSecGrpMembBuff Buff )
	{
		final String S_ProcName = "updateTSecGrpMemb";
		ResultSet resultSet = null;
		try {
			long TenantId = Buff.getRequiredTenantId();
			long TSecGrpMembId = Buff.getRequiredTSecGrpMembId();
			int TSecGroupId = Buff.getRequiredTSecGroupId();
			UUID SecUserId = Buff.getRequiredSecUserId();
			int Revision = Buff.getRequiredRevision();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_update_tsecmemb( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a018" );
			stmtUpdateByPKey.setLong( argIdx++, TenantId );
			stmtUpdateByPKey.setLong( argIdx++, TSecGrpMembId );
			stmtUpdateByPKey.setInt( argIdx++, TSecGroupId );
			stmtUpdateByPKey.setString( argIdx++, SecUserId.toString() );
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
				CFSecTSecGrpMembBuff updatedBuff = unpackTSecGrpMembResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredTSecGroupId( updatedBuff.getRequiredTSecGroupId() );
				Buff.setRequiredSecUserId( updatedBuff.getRequiredSecUserId() );
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

	public void deleteTSecGrpMemb( CFSecAuthorization Authorization,
		CFSecTSecGrpMembBuff Buff )
	{
		final String S_ProcName = "deleteTSecGrpMemb";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			long TenantId = Buff.getRequiredTenantId();
			long TSecGrpMembId = Buff.getRequiredTSecGrpMembId();

			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_tsecmemb( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
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
			stmtDeleteByPKey.setLong( argIdx++, TenantId );
			stmtDeleteByPKey.setLong( argIdx++, TSecGrpMembId );
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

	public void deleteTSecGrpMembByIdIdx( CFSecAuthorization Authorization,
		long argTenantId,
		long argTSecGrpMembId )
	{
		final String S_ProcName = "deleteTSecGrpMembByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_tsecmemb_by_ididx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
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
			stmtDeleteByIdIdx.setLong( argIdx++, argTenantId );
			stmtDeleteByIdIdx.setLong( argIdx++, argTSecGrpMembId );
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

	public void deleteTSecGrpMembByIdIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpMembPKey argKey )
	{
		deleteTSecGrpMembByIdIdx( Authorization,
			argKey.getRequiredTenantId(),
			argKey.getRequiredTSecGrpMembId() );
	}

	public void deleteTSecGrpMembByTenantIdx( CFSecAuthorization Authorization,
		long argTenantId )
	{
		final String S_ProcName = "deleteTSecGrpMembByTenantIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_tsecmemb_by_tenantidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByTenantIdx == null ) {
				stmtDeleteByTenantIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByTenantIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByTenantIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByTenantIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByTenantIdx.setLong( argIdx++, argTenantId );
			stmtDeleteByTenantIdx.executeUpdate();
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

	public void deleteTSecGrpMembByTenantIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpMembByTenantIdxKey argKey )
	{
		deleteTSecGrpMembByTenantIdx( Authorization,
			argKey.getRequiredTenantId() );
	}

	public void deleteTSecGrpMembByGroupIdx( CFSecAuthorization Authorization,
		long argTenantId,
		int argTSecGroupId )
	{
		final String S_ProcName = "deleteTSecGrpMembByGroupIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_tsecmemb_by_groupidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtDeleteByGroupIdx == null ) {
				stmtDeleteByGroupIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByGroupIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByGroupIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByGroupIdx.setLong( argIdx++, argTenantId );
			stmtDeleteByGroupIdx.setInt( argIdx++, argTSecGroupId );
			stmtDeleteByGroupIdx.executeUpdate();
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

	public void deleteTSecGrpMembByGroupIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpMembByGroupIdxKey argKey )
	{
		deleteTSecGrpMembByGroupIdx( Authorization,
			argKey.getRequiredTenantId(),
			argKey.getRequiredTSecGroupId() );
	}

	public void deleteTSecGrpMembByUserIdx( CFSecAuthorization Authorization,
		UUID argSecUserId )
	{
		final String S_ProcName = "deleteTSecGrpMembByUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_tsecmemb_by_useridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByUserIdx == null ) {
				stmtDeleteByUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByUserIdx.setString( argIdx++, argSecUserId.toString() );
			stmtDeleteByUserIdx.executeUpdate();
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

	public void deleteTSecGrpMembByUserIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpMembByUserIdxKey argKey )
	{
		deleteTSecGrpMembByUserIdx( Authorization,
			argKey.getRequiredSecUserId() );
	}

	public void deleteTSecGrpMembByUUserIdx( CFSecAuthorization Authorization,
		long argTenantId,
		int argTSecGroupId,
		UUID argSecUserId )
	{
		final String S_ProcName = "deleteTSecGrpMembByUUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_tsecmemb_by_uuseridx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtDeleteByUUserIdx == null ) {
				stmtDeleteByUUserIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByUUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByUUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByUUserIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByUUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByUUserIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByUUserIdx.setLong( argIdx++, argTenantId );
			stmtDeleteByUUserIdx.setInt( argIdx++, argTSecGroupId );
			stmtDeleteByUUserIdx.setString( argIdx++, argSecUserId.toString() );
			stmtDeleteByUUserIdx.executeUpdate();
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

	public void deleteTSecGrpMembByUUserIdx( CFSecAuthorization Authorization,
		CFSecTSecGrpMembByUUserIdxKey argKey )
	{
		deleteTSecGrpMembByUUserIdx( Authorization,
			argKey.getRequiredTenantId(),
			argKey.getRequiredTSecGroupId(),
			argKey.getRequiredSecUserId() );
	}

	/**
	 *	Release the prepared statements.
	 *	<p>
	 *	When the schema changes connections, the prepared statements
	 *	have to be released because they contain connection-specific
	 *	information for most databases.
	 */
	public void releasePreparedStatements() {
		S_sqlSelectTSecGrpMembDistinctClassCode = null;
		S_sqlSelectTSecGrpMembBuff = null;
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
		if( stmtDeleteByTenantIdx != null ) {
			try {
				stmtDeleteByTenantIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByTenantIdx = null;
		}
		if( stmtDeleteByGroupIdx != null ) {
			try {
				stmtDeleteByGroupIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByGroupIdx = null;
		}
		if( stmtDeleteByUserIdx != null ) {
			try {
				stmtDeleteByUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByUserIdx = null;
		}
		if( stmtDeleteByUUserIdx != null ) {
			try {
				stmtDeleteByUUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByUUserIdx = null;
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
		if( stmtReadBuffByTenantIdx != null ) {
			try {
				stmtReadBuffByTenantIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByTenantIdx = null;
		}
		if( stmtPageBuffByTenantIdx != null ) {
			try {
				stmtPageBuffByTenantIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByTenantIdx = null;
		}
		if( stmtReadBuffByGroupIdx != null ) {
			try {
				stmtReadBuffByGroupIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByGroupIdx = null;
		}
		if( stmtPageBuffByGroupIdx != null ) {
			try {
				stmtPageBuffByGroupIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByGroupIdx = null;
		}
		if( stmtReadBuffByUserIdx != null ) {
			try {
				stmtReadBuffByUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByUserIdx = null;
		}
		if( stmtPageBuffByUserIdx != null ) {
			try {
				stmtPageBuffByUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtPageBuffByUserIdx = null;
		}
		if( stmtReadBuffByUUserIdx != null ) {
			try {
				stmtReadBuffByUUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByUUserIdx = null;
		}
	}
}
