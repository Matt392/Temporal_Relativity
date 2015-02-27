package com.radex392.temporalrelativity.utility;

import com.radex392.temporalrelativity.reference.Reference;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper
{
	public static ResourceLocation getResourceLocation(String modId, String path)
	{
		return new ResourceLocation(modId, path);
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return getResourceLocation(Reference.LOWERCASE_MOD_ID, path);
	}
}
