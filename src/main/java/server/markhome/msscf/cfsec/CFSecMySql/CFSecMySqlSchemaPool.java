// Description: Java 11 implementation of a MySQL CFSec schema pool.

/*
 *	server.markhome.msscf.CFSec
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfsec.CFSecMySql;

import java.util.*;

import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;

public class CFSecMySqlSchemaPool
extends CFSecSchemaPool
{
	public CFSecMySqlSchemaPool() {
		setJndiName( "java:comp/env/CFSec31ConnectionPool" );
	}

	/**
	 *	You need to overload the implementation of newInstance() to return
	 *	connected instances of your backing store.
	 */
	public ICFSecSchema newInstance() {
		ICFSecSchema inst = new CFSecMySqlSchema();
		return( inst );
	}
}
