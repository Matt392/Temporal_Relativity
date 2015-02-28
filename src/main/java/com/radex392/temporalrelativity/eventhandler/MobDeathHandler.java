package com.radex392.temporalrelativity.eventhandler;

import com.radex392.temporalrelativity.entity.TimeChicken;
import com.radex392.temporalrelativity.potion.PotionTR;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * Created by OEM on 27/02/2015.
 */
public class MobDeathHandler {

    @SubscribeEvent
    public void onDeath(LivingDeathEvent e) {

        if((e.entityLiving instanceof TimeChicken) && (e.source.getSourceOfDamage() instanceof EntityPlayer)) {
            EntityPlayer p = (EntityPlayer) e.source.getSourceOfDamage();
            TimeChicken b = (TimeChicken) e.entityLiving;

            p.addPotionEffect(new PotionEffect(PotionTR.oldAge.id, 200, 4));
        }
    }
}
