package com.radex392.temporalrelativity.potion;

import com.radex392.temporalrelativity.damagesource.DamageSourceTR;
import com.radex392.temporalrelativity.reference.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

/**
 * Created by OEM on 28/02/2015.
 */

public class PotionTR extends Potion {

    public static int STARTING_POTION_ID = 64;

    public static void expandPotionTypes(){
        Potion[] potionTypes = null;

        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[128];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
            }
        }
    }

    public static PotionTR oldAge;

    public static  void registerPotions(){
        oldAge = (PotionTR)((new PotionTR(STARTING_POTION_ID+0, false, 16262179)).setPotionName("potionTR.oldAge"));
    }


    //non static variables/ methods

    protected Random rand;

    protected PotionTR(int p_i1569_1_, boolean p_i1569_2_, int p_i1569_3_)
    {
        super(p_i1569_1_, p_i1569_2_, p_i1569_3_);

        rand = new Random();
    }

    @Override
    public void performEffect(EntityLivingBase entity, int p_76394_2_)
    {
        if( id == oldAge.id ){
            int deathChance = rand.nextInt(99);

           if( deathChance <= 1 ){
                entity.attackEntityFrom(DamageSourceTR.oldAge, 100f);
          }
        }
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_)
    {
        if( id == oldAge.id ){
            return true;
        }

        return false;
    }

}
