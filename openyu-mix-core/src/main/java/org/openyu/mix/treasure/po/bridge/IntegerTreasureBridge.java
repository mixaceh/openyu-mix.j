package org.openyu.mix.treasure.po.bridge;

import org.openyu.commons.hibernate.search.bridge.supporter.BaseStringBridgeSupporter;
import org.openyu.mix.treasure.po.usertype.IntegerTreasureUserType;

//--------------------------------------------------
//reslove: Hibernate search
//--------------------------------------------------
public class IntegerTreasureBridge extends BaseStringBridgeSupporter
{

	private IntegerTreasureUserType userType = new IntegerTreasureUserType();

	public IntegerTreasureBridge()
	{

	}

	public String objectToString(Object value)
	{
		return userType.marshal(value, null);
	}
}
