package com.radex392.temporalrelativity.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

/**
 * Created by OEM on 25/02/2015.
 */
public class TimeChicken extends EntityAnimal{

    public TimeChicken( World world ) {
        super( world );

        this.setSize(0.3f, 0.7f);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4d));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0d));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0d, Items.wheat_seeds, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1d));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0d));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    public boolean isAIEnabled(){
        return true;
    }

    protected void applyEntityAttributes(){
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0d);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }


    @Override
    public EntityAgeable createChild( EntityAgeable entity ) {

        return new TimeChicken( worldObj ) ;
    }

}
