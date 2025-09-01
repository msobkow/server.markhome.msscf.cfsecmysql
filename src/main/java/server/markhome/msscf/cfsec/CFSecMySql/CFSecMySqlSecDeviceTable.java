// Description: Java 11 MySQL Jdbc DbIO implementation for SecDevice.

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
 *	CFSecMySqlSecDeviceTable MySQL Jdbc DbIO implementation
 *	for SecDevice.
 */
public class CFSecMySqlSecDeviceTable
	implements ICFSecSecDeviceTable
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
	protected PreparedStatement stmtReadBuffByNameIdx = null;
	protected PreparedStatement stmtReadBuffByUserIdx = null;
	protected PreparedStatement stmtPageBuffByUserIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByNameIdx = null;
	protected PreparedStatement stmtDeleteByUserIdx = null;

	public CFSecMySqlSecDeviceTable( CFSecMySqlSchema argSchema ) {
		schema = argSchema;
	}

	public void createSecDevice( CFSecAuthorization Authorization,
		CFSecSecDeviceBuff Buff )
	{
		final String S_ProcName = "createSecDevice";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			UUID SecUserId = Buff.getRequiredSecUserId();
			String DevName = Buff.getRequiredDevName();
			String PubKey = Buff.getOptionalPubKey();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_create_secdev( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a00a" );
			stmtCreateByPKey.setString( argIdx++, SecUserId.toString() );
			stmtCreateByPKey.setString( argIdx++, DevName );
			if( PubKey != null ) {
				stmtCreateByPKey.setString( argIdx++, PubKey );
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
				CFSecSecDeviceBuff createdBuff = unpackSecDeviceResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredSecUserId( createdBuff.getRequiredSecUserId() );
				Buff.setRequiredDevName( createdBuff.getRequiredDevName() );
				Buff.setOptionalPubKey( createdBuff.getOptionalPubKey() );
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

	protected static String S_sqlSelectSecDeviceDistinctClassCode = null;

	public String getSqlSelectSecDeviceDistinctClassCode() {
		if( S_sqlSelectSecDeviceDistinctClassCode == null ) {
			S_sqlSelectSecDeviceDistinctClassCode =
					"SELECT "
				+		"DISTINCT a00a.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecDev AS a00a ";
		}
		return( S_sqlSelectSecDeviceDistinctClassCode );
	}

	protected static String S_sqlSelectSecDeviceBuff = null;

	public String getSqlSelectSecDeviceBuff() {
		if( S_sqlSelectSecDeviceBuff == null ) {
			S_sqlSelectSecDeviceBuff =
					"SELECT "
				+		"a00a.SecUserId, "
				+		"a00a.DevName, "
				+		"a00a.PubKey, "
				+		"a00a.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".SecDev AS a00a ";
		}
		return( S_sqlSelectSecDeviceBuff );
	}

	protected CFSecSecDeviceBuff unpackSecDeviceResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackSecDeviceResultSetToBuff";
		int idxcol = 1;
		CFSecSecDeviceBuff buff = schema.getFactorySecDevice().newBuff();
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
		buff.setRequiredDevName( resultSet.getString( idxcol ) );
		idxcol++;
		{
			String colVal = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setOptionalPubKey( null );
			}
			else {
				buff.setOptionalPubKey( colVal );
			}
		}
		idxcol++;

		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecSecDeviceBuff readDerived( CFSecAuthorization Authorization,
		CFSecSecDevicePKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecDeviceBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecDeviceBuff lockDerived( CFSecAuthorization Authorization,
		CFSecSecDevicePKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecDeviceBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecSecDeviceBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecSecDeviceBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecSecDeviceBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String DevName )
	{
		final String S_ProcName = "CFSecMySqlSecDeviceTable.readDerivedByIdIdx() ";
		CFSecSecDeviceBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				SecUserId,
				DevName );
		return( buff );
	}

	public CFSecSecDeviceBuff readDerivedByNameIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String DevName )
	{
		final String S_ProcName = "CFSecMySqlSecDeviceTable.readDerivedByNameIdx() ";
		CFSecSecDeviceBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByNameIdx( Authorization,
				SecUserId,
				DevName );
		return( buff );
	}

	public CFSecSecDeviceBuff[] readDerivedByUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readDerivedByUserIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecSecDeviceBuff[] buffList = readBuffByUserIdx( Authorization,
				SecUserId );
		return( buffList );

	}

	public CFSecSecDeviceBuff readBuff( CFSecAuthorization Authorization,
		CFSecSecDevicePKey PKey )
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
			String DevName = PKey.getRequiredDevName();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secdev( ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByPKey.setString( argIdx++, SecUserId.toString() );
			stmtReadBuffByPKey.setString( argIdx++, DevName );
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
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
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

	public CFSecSecDeviceBuff lockBuff( CFSecAuthorization Authorization,
		CFSecSecDevicePKey PKey )
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
			String DevName = PKey.getRequiredDevName();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_lock_secdev( ?, ?, ?, ?, ?" + ", "
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
			stmtLockBuffByPKey.setString( argIdx++, SecUserId.toString() );
			stmtLockBuffByPKey.setString( argIdx++, DevName );
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
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
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

	public CFSecSecDeviceBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secdev_all( ?, ?, ?, ?, ? )";
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
			List<CFSecSecDeviceBuff> buffList = new LinkedList<CFSecSecDeviceBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecDeviceBuff[] retBuff = new CFSecSecDeviceBuff[ buffList.size() ];
			Iterator<CFSecSecDeviceBuff> iter = buffList.iterator();
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
	 *	Read a page of all the specific SecDevice buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecDevice instances in the database accessible for the Authorization.
	 */
	public CFSecSecDeviceBuff[] pageAllBuff( CFSecAuthorization Authorization,
		UUID priorSecUserId,
		String priorDevName )
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
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secdev_all( ?, ?, ?, ?, ?, ?, ? )";
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
			if( priorDevName != null ) {
				stmtPageAllBuff.setString( argIdx++, priorDevName );
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
			List<CFSecSecDeviceBuff> buffList = new LinkedList<CFSecSecDeviceBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecDeviceBuff[] retBuff = new CFSecSecDeviceBuff[ buffList.size() ];
			Iterator<CFSecSecDeviceBuff> iter = buffList.iterator();
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

	public CFSecSecDeviceBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String DevName )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secdev_by_ididx( ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByIdIdx.setString( argIdx++, SecUserId.toString() );
			stmtReadBuffByIdIdx.setString( argIdx++, DevName );
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
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
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

	public CFSecSecDeviceBuff readBuffByNameIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		String DevName )
	{
		final String S_ProcName = "readBuffByNameIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secdev_by_nameidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByNameIdx == null ) {
				stmtReadBuffByNameIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByNameIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByNameIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByNameIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByNameIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByNameIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByNameIdx.setString( argIdx++, SecUserId.toString() );
			stmtReadBuffByNameIdx.setString( argIdx++, DevName );
			try {
				resultSet = stmtReadBuffByNameIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
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

	public CFSecSecDeviceBuff[] readBuffByUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId )
	{
		final String S_ProcName = "readBuffByUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_secdev_by_useridx( ?, ?, ?, ?, ?" + ", "
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
			List<CFSecSecDeviceBuff> buffList = new LinkedList<CFSecSecDeviceBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecDeviceBuff[] retBuff = new CFSecSecDeviceBuff[ buffList.size() ];
			Iterator<CFSecSecDeviceBuff> iter = buffList.iterator();
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
	 *	Read a page array of the specific SecDevice buffer instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argSecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecSecDeviceBuff[] pageBuffByUserIdx( CFSecAuthorization Authorization,
		UUID SecUserId,
		UUID priorSecUserId,
		String priorDevName )
	{
		final String S_ProcName = "pageBuffByUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_page_secdev_by_useridx( ?, ?, ?, ?, ?" + ", "
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
			if( priorSecUserId != null ) {
				stmtPageBuffByUserIdx.setString( argIdx++, priorSecUserId.toString() );
			}
			else {
				stmtPageBuffByUserIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			if( priorDevName != null ) {
				stmtPageBuffByUserIdx.setString( argIdx++, priorDevName );
			}
			else {
				stmtPageBuffByUserIdx.setNull( argIdx++, java.sql.Types.VARCHAR );
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
			List<CFSecSecDeviceBuff> buffList = new LinkedList<CFSecSecDeviceBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecSecDeviceBuff buff = unpackSecDeviceResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecSecDeviceBuff[] retBuff = new CFSecSecDeviceBuff[ buffList.size() ];
			Iterator<CFSecSecDeviceBuff> iter = buffList.iterator();
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

	public void updateSecDevice( CFSecAuthorization Authorization,
		CFSecSecDeviceBuff Buff )
	{
		final String S_ProcName = "updateSecDevice";
		ResultSet resultSet = null;
		try {
			UUID SecUserId = Buff.getRequiredSecUserId();
			String DevName = Buff.getRequiredDevName();
			String PubKey = Buff.getOptionalPubKey();
			int Revision = Buff.getRequiredRevision();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_update_secdev( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a00a" );
			stmtUpdateByPKey.setString( argIdx++, SecUserId.toString() );
			stmtUpdateByPKey.setString( argIdx++, DevName );
			if( PubKey != null ) {
				stmtUpdateByPKey.setString( argIdx++, PubKey );
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
				CFSecSecDeviceBuff updatedBuff = unpackSecDeviceResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setOptionalPubKey( updatedBuff.getOptionalPubKey() );
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

	public void deleteSecDevice( CFSecAuthorization Authorization,
		CFSecSecDeviceBuff Buff )
	{
		final String S_ProcName = "deleteSecDevice";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			UUID SecUserId = Buff.getRequiredSecUserId();
			String DevName = Buff.getRequiredDevName();

			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secdev( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByPKey.setString( argIdx++, SecUserId.toString() );
			stmtDeleteByPKey.setString( argIdx++, DevName );
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

	public void deleteSecDeviceByIdIdx( CFSecAuthorization Authorization,
		UUID argSecUserId,
		String argDevName )
	{
		final String S_ProcName = "deleteSecDeviceByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secdev_by_ididx( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByIdIdx.setString( argIdx++, argSecUserId.toString() );
			stmtDeleteByIdIdx.setString( argIdx++, argDevName );
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

	public void deleteSecDeviceByIdIdx( CFSecAuthorization Authorization,
		CFSecSecDevicePKey argKey )
	{
		deleteSecDeviceByIdIdx( Authorization,
			argKey.getRequiredSecUserId(),
			argKey.getRequiredDevName() );
	}

	public void deleteSecDeviceByNameIdx( CFSecAuthorization Authorization,
		UUID argSecUserId,
		String argDevName )
	{
		final String S_ProcName = "deleteSecDeviceByNameIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secdev_by_nameidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + ", "
				+		"?" + " )";
			if( stmtDeleteByNameIdx == null ) {
				stmtDeleteByNameIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByNameIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByNameIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByNameIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByNameIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByNameIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByNameIdx.setString( argIdx++, argSecUserId.toString() );
			stmtDeleteByNameIdx.setString( argIdx++, argDevName );
			stmtDeleteByNameIdx.executeUpdate();
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

	public void deleteSecDeviceByNameIdx( CFSecAuthorization Authorization,
		CFSecSecDeviceByNameIdxKey argKey )
	{
		deleteSecDeviceByNameIdx( Authorization,
			argKey.getRequiredSecUserId(),
			argKey.getRequiredDevName() );
	}

	public void deleteSecDeviceByUserIdx( CFSecAuthorization Authorization,
		UUID argSecUserId )
	{
		final String S_ProcName = "deleteSecDeviceByUserIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_secdev_by_useridx( ?, ?, ?, ?, ?" + ", "
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

	public void deleteSecDeviceByUserIdx( CFSecAuthorization Authorization,
		CFSecSecDeviceByUserIdxKey argKey )
	{
		deleteSecDeviceByUserIdx( Authorization,
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
		S_sqlSelectSecDeviceDistinctClassCode = null;
		S_sqlSelectSecDeviceBuff = null;
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
		if( stmtDeleteByNameIdx != null ) {
			try {
				stmtDeleteByNameIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByNameIdx = null;
		}
		if( stmtDeleteByUserIdx != null ) {
			try {
				stmtDeleteByUserIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByUserIdx = null;
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
		if( stmtReadBuffByNameIdx != null ) {
			try {
				stmtReadBuffByNameIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByNameIdx = null;
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
	}
}
