// Description: Java 11 MySQL Jdbc DbIO implementation for ISOCtryLang.

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
 *	CFSecMySqlISOCtryLangTable MySQL Jdbc DbIO implementation
 *	for ISOCtryLang.
 */
public class CFSecMySqlISOCtryLangTable
	implements ICFSecISOCtryLangTable
{
	private CFSecMySqlSchema schema;
	protected PreparedStatement stmtReadBuffByPKey = null;
	protected PreparedStatement stmtLockBuffByPKey = null;
	protected PreparedStatement stmtCreateByPKey = null;
	protected PreparedStatement stmtUpdateByPKey = null;
	protected PreparedStatement stmtDeleteByPKey = null;
	protected PreparedStatement stmtReadAllBuff = null;
	protected PreparedStatement stmtReadBuffByIdIdx = null;
	protected PreparedStatement stmtReadBuffByCtryIdx = null;
	protected PreparedStatement stmtReadBuffByLangIdx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByCtryIdx = null;
	protected PreparedStatement stmtDeleteByLangIdx = null;

	public CFSecMySqlISOCtryLangTable( CFSecMySqlSchema argSchema ) {
		schema = argSchema;
	}

	public void createISOCtryLang( CFSecAuthorization Authorization,
		CFSecISOCtryLangBuff Buff )
	{
		final String S_ProcName = "createISOCtryLang";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			short ISOCtryId = Buff.getRequiredISOCtryId();
			short ISOLangId = Buff.getRequiredISOLangId();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_create_iso_cntrylng( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a006" );
			stmtCreateByPKey.setShort( argIdx++, ISOCtryId );
			stmtCreateByPKey.setShort( argIdx++, ISOLangId );
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
				CFSecISOCtryLangBuff createdBuff = unpackISOCtryLangResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredISOCtryId( createdBuff.getRequiredISOCtryId() );
				Buff.setRequiredISOLangId( createdBuff.getRequiredISOLangId() );
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

	protected static String S_sqlSelectISOCtryLangDistinctClassCode = null;

	public String getSqlSelectISOCtryLangDistinctClassCode() {
		if( S_sqlSelectISOCtryLangDistinctClassCode == null ) {
			S_sqlSelectISOCtryLangDistinctClassCode =
					"SELECT "
				+		"DISTINCT a006.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".iso_cntrylng AS a006 ";
		}
		return( S_sqlSelectISOCtryLangDistinctClassCode );
	}

	protected static String S_sqlSelectISOCtryLangBuff = null;

	public String getSqlSelectISOCtryLangBuff() {
		if( S_sqlSelectISOCtryLangBuff == null ) {
			S_sqlSelectISOCtryLangBuff =
					"SELECT "
				+		"a006.ISOCtryId, "
				+		"a006.ISOLangId, "
				+		"a006.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".iso_cntrylng AS a006 ";
		}
		return( S_sqlSelectISOCtryLangBuff );
	}

	protected CFSecISOCtryLangBuff unpackISOCtryLangResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackISOCtryLangResultSetToBuff";
		int idxcol = 1;
		CFSecISOCtryLangBuff buff = schema.getFactoryISOCtryLang().newBuff();
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
		idxcol++;		buff.setRequiredISOCtryId( resultSet.getShort( idxcol ) );
		idxcol++;
		buff.setRequiredISOLangId( resultSet.getShort( idxcol ) );
		idxcol++;

		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecISOCtryLangBuff readDerived( CFSecAuthorization Authorization,
		CFSecISOCtryLangPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOCtryLangBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecISOCtryLangBuff lockDerived( CFSecAuthorization Authorization,
		CFSecISOCtryLangPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOCtryLangBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecISOCtryLangBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecISOCtryLangBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecISOCtryLangBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		short ISOCtryId,
		short ISOLangId )
	{
		final String S_ProcName = "CFSecMySqlISOCtryLangTable.readDerivedByIdIdx() ";
		CFSecISOCtryLangBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				ISOCtryId,
				ISOLangId );
		return( buff );
	}

	public CFSecISOCtryLangBuff[] readDerivedByCtryIdx( CFSecAuthorization Authorization,
		short ISOCtryId )
	{
		final String S_ProcName = "readDerivedByCtryIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOCtryLangBuff[] buffList = readBuffByCtryIdx( Authorization,
				ISOCtryId );
		return( buffList );

	}

	public CFSecISOCtryLangBuff[] readDerivedByLangIdx( CFSecAuthorization Authorization,
		short ISOLangId )
	{
		final String S_ProcName = "readDerivedByLangIdx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOCtryLangBuff[] buffList = readBuffByLangIdx( Authorization,
				ISOLangId );
		return( buffList );

	}

	public CFSecISOCtryLangBuff readBuff( CFSecAuthorization Authorization,
		CFSecISOCtryLangPKey PKey )
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
			short ISOCtryId = PKey.getRequiredISOCtryId();
			short ISOLangId = PKey.getRequiredISOLangId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_cntrylng( ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByPKey.setShort( argIdx++, ISOCtryId );
			stmtReadBuffByPKey.setShort( argIdx++, ISOLangId );
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
				CFSecISOCtryLangBuff buff = unpackISOCtryLangResultSetToBuff( resultSet );
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

	public CFSecISOCtryLangBuff lockBuff( CFSecAuthorization Authorization,
		CFSecISOCtryLangPKey PKey )
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
			short ISOCtryId = PKey.getRequiredISOCtryId();
			short ISOLangId = PKey.getRequiredISOLangId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_lock_iso_cntrylng( ?, ?, ?, ?, ?" + ", "
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
			stmtLockBuffByPKey.setShort( argIdx++, ISOCtryId );
			stmtLockBuffByPKey.setShort( argIdx++, ISOLangId );
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
				CFSecISOCtryLangBuff buff = unpackISOCtryLangResultSetToBuff( resultSet );
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

	public CFSecISOCtryLangBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_cntrylng_all( ?, ?, ?, ?, ? )";
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
			List<CFSecISOCtryLangBuff> buffList = new LinkedList<CFSecISOCtryLangBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecISOCtryLangBuff buff = unpackISOCtryLangResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecISOCtryLangBuff[] retBuff = new CFSecISOCtryLangBuff[ buffList.size() ];
			Iterator<CFSecISOCtryLangBuff> iter = buffList.iterator();
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

	public CFSecISOCtryLangBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		short ISOCtryId,
		short ISOLangId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_cntrylng_by_ididx( ?, ?, ?, ?, ?" + ", "
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
			stmtReadBuffByIdIdx.setShort( argIdx++, ISOCtryId );
			stmtReadBuffByIdIdx.setShort( argIdx++, ISOLangId );
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
				CFSecISOCtryLangBuff buff = unpackISOCtryLangResultSetToBuff( resultSet );
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

	public CFSecISOCtryLangBuff[] readBuffByCtryIdx( CFSecAuthorization Authorization,
		short ISOCtryId )
	{
		final String S_ProcName = "readBuffByCtryIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_cntrylng_by_ctryidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByCtryIdx == null ) {
				stmtReadBuffByCtryIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByCtryIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByCtryIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByCtryIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByCtryIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByCtryIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByCtryIdx.setShort( argIdx++, ISOCtryId );
			try {
				resultSet = stmtReadBuffByCtryIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecISOCtryLangBuff> buffList = new LinkedList<CFSecISOCtryLangBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecISOCtryLangBuff buff = unpackISOCtryLangResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecISOCtryLangBuff[] retBuff = new CFSecISOCtryLangBuff[ buffList.size() ];
			Iterator<CFSecISOCtryLangBuff> iter = buffList.iterator();
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

	public CFSecISOCtryLangBuff[] readBuffByLangIdx( CFSecAuthorization Authorization,
		short ISOLangId )
	{
		final String S_ProcName = "readBuffByLangIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_cntrylng_by_langidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByLangIdx == null ) {
				stmtReadBuffByLangIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByLangIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByLangIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByLangIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByLangIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByLangIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByLangIdx.setShort( argIdx++, ISOLangId );
			try {
				resultSet = stmtReadBuffByLangIdx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecISOCtryLangBuff> buffList = new LinkedList<CFSecISOCtryLangBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecISOCtryLangBuff buff = unpackISOCtryLangResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecISOCtryLangBuff[] retBuff = new CFSecISOCtryLangBuff[ buffList.size() ];
			Iterator<CFSecISOCtryLangBuff> iter = buffList.iterator();
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

	public void updateISOCtryLang( CFSecAuthorization Authorization,
		CFSecISOCtryLangBuff Buff )
	{
		final String S_ProcName = "updateISOCtryLang";
		ResultSet resultSet = null;
		try {
			short ISOCtryId = Buff.getRequiredISOCtryId();
			short ISOLangId = Buff.getRequiredISOLangId();
			int Revision = Buff.getRequiredRevision();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_update_iso_cntrylng( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a006" );
			stmtUpdateByPKey.setShort( argIdx++, ISOCtryId );
			stmtUpdateByPKey.setShort( argIdx++, ISOLangId );
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
				CFSecISOCtryLangBuff updatedBuff = unpackISOCtryLangResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
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

	public void deleteISOCtryLang( CFSecAuthorization Authorization,
		CFSecISOCtryLangBuff Buff )
	{
		final String S_ProcName = "deleteISOCtryLang";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			short ISOCtryId = Buff.getRequiredISOCtryId();
			short ISOLangId = Buff.getRequiredISOLangId();

			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_cntrylng( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByPKey.setShort( argIdx++, ISOCtryId );
			stmtDeleteByPKey.setShort( argIdx++, ISOLangId );
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

	public void deleteISOCtryLangByIdIdx( CFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOLangId )
	{
		final String S_ProcName = "deleteISOCtryLangByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_cntrylng_by_ididx( ?, ?, ?, ?, ?" + ", "
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
			stmtDeleteByIdIdx.setShort( argIdx++, argISOCtryId );
			stmtDeleteByIdIdx.setShort( argIdx++, argISOLangId );
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

	public void deleteISOCtryLangByIdIdx( CFSecAuthorization Authorization,
		CFSecISOCtryLangPKey argKey )
	{
		deleteISOCtryLangByIdIdx( Authorization,
			argKey.getRequiredISOCtryId(),
			argKey.getRequiredISOLangId() );
	}

	public void deleteISOCtryLangByCtryIdx( CFSecAuthorization Authorization,
		short argISOCtryId )
	{
		final String S_ProcName = "deleteISOCtryLangByCtryIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_cntrylng_by_ctryidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByCtryIdx == null ) {
				stmtDeleteByCtryIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByCtryIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByCtryIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByCtryIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByCtryIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByCtryIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByCtryIdx.setShort( argIdx++, argISOCtryId );
			stmtDeleteByCtryIdx.executeUpdate();
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

	public void deleteISOCtryLangByCtryIdx( CFSecAuthorization Authorization,
		CFSecISOCtryLangByCtryIdxKey argKey )
	{
		deleteISOCtryLangByCtryIdx( Authorization,
			argKey.getRequiredISOCtryId() );
	}

	public void deleteISOCtryLangByLangIdx( CFSecAuthorization Authorization,
		short argISOLangId )
	{
		final String S_ProcName = "deleteISOCtryLangByLangIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_cntrylng_by_langidx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByLangIdx == null ) {
				stmtDeleteByLangIdx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByLangIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByLangIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByLangIdx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByLangIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByLangIdx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByLangIdx.setShort( argIdx++, argISOLangId );
			stmtDeleteByLangIdx.executeUpdate();
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

	public void deleteISOCtryLangByLangIdx( CFSecAuthorization Authorization,
		CFSecISOCtryLangByLangIdxKey argKey )
	{
		deleteISOCtryLangByLangIdx( Authorization,
			argKey.getRequiredISOLangId() );
	}

	/**
	 *	Release the prepared statements.
	 *	<p>
	 *	When the schema changes connections, the prepared statements
	 *	have to be released because they contain connection-specific
	 *	information for most databases.
	 */
	public void releasePreparedStatements() {
		S_sqlSelectISOCtryLangDistinctClassCode = null;
		S_sqlSelectISOCtryLangBuff = null;
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
		if( stmtDeleteByCtryIdx != null ) {
			try {
				stmtDeleteByCtryIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByCtryIdx = null;
		}
		if( stmtDeleteByLangIdx != null ) {
			try {
				stmtDeleteByLangIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByLangIdx = null;
		}
		if( stmtReadAllBuff != null ) {
			try {
				stmtReadAllBuff .close();
			}
			catch( SQLException e ) {
			}
			stmtReadAllBuff = null;
		}
		if( stmtReadBuffByIdIdx != null ) {
			try {
				stmtReadBuffByIdIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByIdIdx = null;
		}
		if( stmtReadBuffByCtryIdx != null ) {
			try {
				stmtReadBuffByCtryIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByCtryIdx = null;
		}
		if( stmtReadBuffByLangIdx != null ) {
			try {
				stmtReadBuffByLangIdx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByLangIdx = null;
		}
	}
}
