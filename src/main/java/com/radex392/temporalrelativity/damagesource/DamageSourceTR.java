package com.radex392.temporalrelativity.damagesource;

import com.radex392.temporalrelativity.reference.Reference;
import net.minecraft.util.DamageSource;

/**
 * Created by OEM on 28/02/2015.
 */

public class DamageSourceTR extends DamageSource{

    public static DamageSourceTR oldAge;

    public static  void registerDamageEffects(){
        oldAge = new DamageSourceTR(Reference.MOD_ID + ".oldAge");
    }

    protected DamageSourceTR(String par1Str) {
        super(par1Str);
    }
}
