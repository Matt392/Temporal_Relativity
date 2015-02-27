package com.radex392.temporalrelativity.reference;

import com.radex392.temporalrelativity.utility.ResourceLocationHelper;
import net.minecraft.util.ResourceLocation;

public final class Textures
{
	public  static  final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

	public static final class Gui
	{
		private static final String GUI_SHEET_LOCATION = "textures/gui/";
		public static final ResourceLocation TEMPORAL_INFUSOR = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "temporal_infusor.png");
	}
}
