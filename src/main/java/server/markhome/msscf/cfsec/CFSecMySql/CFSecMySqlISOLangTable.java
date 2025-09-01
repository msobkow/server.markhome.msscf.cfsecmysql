// Description: Java 11 MySQL Jdbc DbIO implementation for ISOLang.

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
 *	CFSecMySqlISOLangTable MySQL Jdbc DbIO implementation
 *	for ISOLang.
 */
public class CFSecMySqlISOLangTable
	implements ICFSecISOLangTable
{
	private CFSecMySqlSchema schema;
	protected PreparedStatement stmtReadBuffByPKey = null;
	protected PreparedStatement stmtLockBuffByPKey = null;
	protected PreparedStatement stmtCreateByPKey = null;
	protected PreparedStatement stmtUpdateByPKey = null;
	protected PreparedStatement stmtDeleteByPKey = null;
	protected PreparedStatement stmtReadAllBuff = null;
	protected PreparedStatement stmtReadBuffByIdIdx = null;
	protected PreparedStatement stmtReadBuffByCode3Idx = null;
	protected PreparedStatement stmtReadBuffByCode2Idx = null;
	protected PreparedStatement stmtDeleteByIdIdx = null;
	protected PreparedStatement stmtDeleteByCode3Idx = null;
	protected PreparedStatement stmtDeleteByCode2Idx = null;

	public CFSecMySqlISOLangTable( CFSecMySqlSchema argSchema ) {
		schema = argSchema;
	}

	public void createISOLang( CFSecAuthorization Authorization,
		CFSecISOLangBuff Buff )
	{
		final String S_ProcName = "createISOLang";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			String ISO6392Code = Buff.getRequiredISO6392Code();
			String ISO6391Code = Buff.getOptionalISO6391Code();
			String EnglishName = Buff.getRequiredEnglishName();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_create_iso_lang( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtCreateByPKey.setString( argIdx++, "a007" );
			stmtCreateByPKey.setString( argIdx++, ISO6392Code );
			if( ISO6391Code != null ) {
				stmtCreateByPKey.setString( argIdx++, ISO6391Code );
			}
			else {
				stmtCreateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtCreateByPKey.setString( argIdx++, EnglishName );
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
				CFSecISOLangBuff createdBuff = unpackISOLangResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredISOLangId( createdBuff.getRequiredISOLangId() );
				Buff.setRequiredISO6392Code( createdBuff.getRequiredISO6392Code() );
				Buff.setOptionalISO6391Code( createdBuff.getOptionalISO6391Code() );
				Buff.setRequiredEnglishName( createdBuff.getRequiredEnglishName() );
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

	protected static String S_sqlSelectISOLangDistinctClassCode = null;

	public String getSqlSelectISOLangDistinctClassCode() {
		if( S_sqlSelectISOLangDistinctClassCode == null ) {
			S_sqlSelectISOLangDistinctClassCode =
					"SELECT "
				+		"DISTINCT a007.ClassCode "
				+	"FROM " + schema.getLowerDbSchemaName() + ".iso_lang AS a007 ";
		}
		return( S_sqlSelectISOLangDistinctClassCode );
	}

	protected static String S_sqlSelectISOLangBuff = null;

	public String getSqlSelectISOLangBuff() {
		if( S_sqlSelectISOLangBuff == null ) {
			S_sqlSelectISOLangBuff =
					"SELECT "
				+		"a007.ISOLangId, "
				+		"a007.iso_code3, "
				+		"a007.iso_code2, "
				+		"a007.eng_name, "
				+		"a007.Revision "
				+	"FROM " + schema.getLowerDbSchemaName() + ".iso_lang AS a007 ";
		}
		return( S_sqlSelectISOLangBuff );
	}

	protected CFSecISOLangBuff unpackISOLangResultSetToBuff( ResultSet resultSet )
	throws SQLException
	{
		final String S_ProcName = "unpackISOLangResultSetToBuff";
		int idxcol = 1;
		CFSecISOLangBuff buff = schema.getFactoryISOLang().newBuff();
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
		idxcol++;		buff.setRequiredISOLangId( resultSet.getShort( idxcol ) );
		idxcol++;
		buff.setRequiredISO6392Code( resultSet.getString( idxcol ) );
		idxcol++;
		{
			String colVal = resultSet.getString( idxcol );
			if( resultSet.wasNull() ) {
				buff.setOptionalISO6391Code( null );
			}
			else {
				buff.setOptionalISO6391Code( colVal );
			}
		}
		idxcol++;
		buff.setRequiredEnglishName( resultSet.getString( idxcol ) );
		idxcol++;

		buff.setRequiredRevision( resultSet.getInt( idxcol ) );
		return( buff );
	}

	public CFSecISOLangBuff readDerived( CFSecAuthorization Authorization,
		CFSecISOLangPKey PKey )
	{
		final String S_ProcName = "readDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOLangBuff buff;
		buff = readBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecISOLangBuff lockDerived( CFSecAuthorization Authorization,
		CFSecISOLangPKey PKey )
	{
		final String S_ProcName = "lockDerived";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOLangBuff buff;
		buff = lockBuff( Authorization, PKey );
		return( buff );
	}

	public CFSecISOLangBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllDerived";
		CFSecISOLangBuff[] buffArray;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buffArray = readAllBuff( Authorization );
		return( buffArray );
	}

	public CFSecISOLangBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		short ISOLangId )
	{
		final String S_ProcName = "CFSecMySqlISOLangTable.readDerivedByIdIdx() ";
		CFSecISOLangBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByIdIdx( Authorization,
				ISOLangId );
		return( buff );
	}

	public CFSecISOLangBuff readDerivedByCode3Idx( CFSecAuthorization Authorization,
		String ISO6392Code )
	{
		final String S_ProcName = "CFSecMySqlISOLangTable.readDerivedByCode3Idx() ";
		CFSecISOLangBuff buff;
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		buff = readBuffByCode3Idx( Authorization,
				ISO6392Code );
		return( buff );
	}

	public CFSecISOLangBuff[] readDerivedByCode2Idx( CFSecAuthorization Authorization,
		String ISO6391Code )
	{
		final String S_ProcName = "readDerivedByCode2Idx";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		CFSecISOLangBuff[] buffList = readBuffByCode2Idx( Authorization,
				ISO6391Code );
		return( buffList );

	}

	public CFSecISOLangBuff readBuff( CFSecAuthorization Authorization,
		CFSecISOLangPKey PKey )
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
			short ISOLangId = PKey.getRequiredISOLangId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_lang( ?, ?, ?, ?, ?" + ", "
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
				CFSecISOLangBuff buff = unpackISOLangResultSetToBuff( resultSet );
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

	public CFSecISOLangBuff lockBuff( CFSecAuthorization Authorization,
		CFSecISOLangPKey PKey )
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
			short ISOLangId = PKey.getRequiredISOLangId();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_lock_iso_lang( ?, ?, ?, ?, ?" + ", "
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
				CFSecISOLangBuff buff = unpackISOLangResultSetToBuff( resultSet );
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

	public CFSecISOLangBuff[] readAllBuff( CFSecAuthorization Authorization ) {
		final String S_ProcName = "readAllBuff";
		if( ! schema.isTransactionOpen() ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				"Transaction not open" );
		}
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_lang_all( ?, ?, ?, ?, ? )";
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
			List<CFSecISOLangBuff> buffList = new LinkedList<CFSecISOLangBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecISOLangBuff buff = unpackISOLangResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecISOLangBuff[] retBuff = new CFSecISOLangBuff[ buffList.size() ];
			Iterator<CFSecISOLangBuff> iter = buffList.iterator();
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

	public CFSecISOLangBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		short ISOLangId )
	{
		final String S_ProcName = "readBuffByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_lang_by_ididx( ?, ?, ?, ?, ?" + ", "
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
				CFSecISOLangBuff buff = unpackISOLangResultSetToBuff( resultSet );
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

	public CFSecISOLangBuff readBuffByCode3Idx( CFSecAuthorization Authorization,
		String ISO6392Code )
	{
		final String S_ProcName = "readBuffByCode3Idx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_lang_by_code3idx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByCode3Idx == null ) {
				stmtReadBuffByCode3Idx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByCode3Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByCode3Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByCode3Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByCode3Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByCode3Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtReadBuffByCode3Idx.setString( argIdx++, ISO6392Code );
			try {
				resultSet = stmtReadBuffByCode3Idx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			if( ( resultSet != null ) && resultSet.next() ) {
				CFSecISOLangBuff buff = unpackISOLangResultSetToBuff( resultSet );
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

	public CFSecISOLangBuff[] readBuffByCode2Idx( CFSecAuthorization Authorization,
		String ISO6391Code )
	{
		final String S_ProcName = "readBuffByCode2Idx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_read_iso_lang_by_code2idx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtReadBuffByCode2Idx == null ) {
				stmtReadBuffByCode2Idx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtReadBuffByCode2Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByCode2Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtReadBuffByCode2Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtReadBuffByCode2Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtReadBuffByCode2Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( ISO6391Code != null ) {
				stmtReadBuffByCode2Idx.setString( argIdx++, ISO6391Code );
			}
			else {
				stmtReadBuffByCode2Idx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			try {
				resultSet = stmtReadBuffByCode2Idx.executeQuery();
			}
			catch( SQLException e ) {
				if( e.getErrorCode() != 1329 ) {
					throw e;
				}
				resultSet = null;
			}
			List<CFSecISOLangBuff> buffList = new LinkedList<CFSecISOLangBuff>();
			while( ( resultSet != null ) && resultSet.next() ) {
				CFSecISOLangBuff buff = unpackISOLangResultSetToBuff( resultSet );
				buffList.add( buff );
			}
			int idx = 0;
			CFSecISOLangBuff[] retBuff = new CFSecISOLangBuff[ buffList.size() ];
			Iterator<CFSecISOLangBuff> iter = buffList.iterator();
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

	public void updateISOLang( CFSecAuthorization Authorization,
		CFSecISOLangBuff Buff )
	{
		final String S_ProcName = "updateISOLang";
		ResultSet resultSet = null;
		try {
			short ISOLangId = Buff.getRequiredISOLangId();
			String ISO6392Code = Buff.getRequiredISO6392Code();
			String ISO6391Code = Buff.getOptionalISO6391Code();
			String EnglishName = Buff.getRequiredEnglishName();
			int Revision = Buff.getRequiredRevision();
			Connection cnx = schema.getCnx();
			String sql =
				"call " + schema.getLowerDbSchemaName() + ".sp_update_iso_lang( ?, ?, ?, ?, ?, ?" + ", "
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
			stmtUpdateByPKey.setString( argIdx++, "a007" );
			stmtUpdateByPKey.setShort( argIdx++, ISOLangId );
			stmtUpdateByPKey.setString( argIdx++, ISO6392Code );
			if( ISO6391Code != null ) {
				stmtUpdateByPKey.setString( argIdx++, ISO6391Code );
			}
			else {
				stmtUpdateByPKey.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtUpdateByPKey.setString( argIdx++, EnglishName );
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
				CFSecISOLangBuff updatedBuff = unpackISOLangResultSetToBuff( resultSet );
				if( ( resultSet != null ) && resultSet.next() ) {
					resultSet.last();
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Did not expect multi-record response, " + resultSet.getRow() + " rows selected" );
				}
				Buff.setRequiredISO6392Code( updatedBuff.getRequiredISO6392Code() );
				Buff.setOptionalISO6391Code( updatedBuff.getOptionalISO6391Code() );
				Buff.setRequiredEnglishName( updatedBuff.getRequiredEnglishName() );
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

	public void deleteISOLang( CFSecAuthorization Authorization,
		CFSecISOLangBuff Buff )
	{
		final String S_ProcName = "deleteISOLang";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			short ISOLangId = Buff.getRequiredISOLangId();

			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_lang( ?, ?, ?, ?, ?" + ", "
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

	public void deleteISOLangByIdIdx( CFSecAuthorization Authorization,
		short argISOLangId )
	{
		final String S_ProcName = "deleteISOLangByIdIdx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_lang_by_ididx( ?, ?, ?, ?, ?" + ", "
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

	public void deleteISOLangByIdIdx( CFSecAuthorization Authorization,
		CFSecISOLangPKey argKey )
	{
		deleteISOLangByIdIdx( Authorization,
			argKey.getRequiredISOLangId() );
	}

	public void deleteISOLangByCode3Idx( CFSecAuthorization Authorization,
		String argISO6392Code )
	{
		final String S_ProcName = "deleteISOLangByCode3Idx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_lang_by_code3idx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByCode3Idx == null ) {
				stmtDeleteByCode3Idx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByCode3Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByCode3Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByCode3Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByCode3Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByCode3Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			stmtDeleteByCode3Idx.setString( argIdx++, argISO6392Code );
			stmtDeleteByCode3Idx.executeUpdate();
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

	public void deleteISOLangByCode3Idx( CFSecAuthorization Authorization,
		CFSecISOLangByCode3IdxKey argKey )
	{
		deleteISOLangByCode3Idx( Authorization,
			argKey.getRequiredISO6392Code() );
	}

	public void deleteISOLangByCode2Idx( CFSecAuthorization Authorization,
		String argISO6391Code )
	{
		final String S_ProcName = "deleteISOLangByCode2Idx";
		ResultSet resultSet = null;
		try {
			Connection cnx = schema.getCnx();
			String sql = "call " + schema.getLowerDbSchemaName() + ".sp_delete_iso_lang_by_code2idx( ?, ?, ?, ?, ?" + ", "
				+		"?" + " )";
			if( stmtDeleteByCode2Idx == null ) {
				stmtDeleteByCode2Idx = cnx.prepareStatement( sql );
			}
			int argIdx = 1;
			stmtDeleteByCode2Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByCode2Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecUserId().toString() );
			stmtDeleteByCode2Idx.setString( argIdx++, ( Authorization == null ) ? "" : Authorization.getSecSessionId().toString() );
			stmtDeleteByCode2Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecClusterId() );
			stmtDeleteByCode2Idx.setLong( argIdx++, ( Authorization == null ) ? 0 : Authorization.getSecTenantId() );
			if( argISO6391Code != null ) {
				stmtDeleteByCode2Idx.setString( argIdx++, argISO6391Code );
			}
			else {
				stmtDeleteByCode2Idx.setNull( argIdx++, java.sql.Types.VARCHAR );
			}
			stmtDeleteByCode2Idx.executeUpdate();
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

	public void deleteISOLangByCode2Idx( CFSecAuthorization Authorization,
		CFSecISOLangByCode2IdxKey argKey )
	{
		deleteISOLangByCode2Idx( Authorization,
			argKey.getOptionalISO6391Code() );
	}

	/**
	 *	Release the prepared statements.
	 *	<p>
	 *	When the schema changes connections, the prepared statements
	 *	have to be released because they contain connection-specific
	 *	information for most databases.
	 */
	public void releasePreparedStatements() {
		S_sqlSelectISOLangDistinctClassCode = null;
		S_sqlSelectISOLangBuff = null;
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
		if( stmtDeleteByCode3Idx != null ) {
			try {
				stmtDeleteByCode3Idx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByCode3Idx = null;
		}
		if( stmtDeleteByCode2Idx != null ) {
			try {
				stmtDeleteByCode2Idx.close();
			}
			catch( SQLException e ) {
			}
			stmtDeleteByCode2Idx = null;
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
		if( stmtReadBuffByCode3Idx != null ) {
			try {
				stmtReadBuffByCode3Idx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByCode3Idx = null;
		}
		if( stmtReadBuffByCode2Idx != null ) {
			try {
				stmtReadBuffByCode2Idx.close();
			}
			catch( SQLException e ) {
			}
			stmtReadBuffByCode2Idx = null;
		}
	}
}
