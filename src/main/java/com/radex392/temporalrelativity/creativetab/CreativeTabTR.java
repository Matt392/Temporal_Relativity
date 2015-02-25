package com.radex392.temporalrelativity.creativetab;

import com.radex392.temporalrelativity.init.ModOres;
import com.radex392.temporalrelativity.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabTR
{
	public static final CreativeTabs TR_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
	{
		@Override
		public Item getTabIconItem()
		{
			return ModOres.temporalIron.getIngot();
		}

		@Override
		public String getTranslatedTabLabel()
		{
			return Reference.MOD_NAME;
		}
	};
}
