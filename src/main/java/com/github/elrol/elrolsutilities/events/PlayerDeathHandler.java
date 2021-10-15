package com.github.elrol.elrolsutilities.events;

import com.github.elrol.elrolsutilities.Main;
import com.github.elrol.elrolsutilities.config.CommandConfig;
import com.github.elrol.elrolsutilities.data.PlayerData;
import com.github.elrol.elrolsutilities.libs.Methods;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerDeathHandler {
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            PlayerData data = Main.database.get(player.getUUID());
            if(CommandConfig.back_enable_on_death.get()) data.prevLoc = Methods.getPlayerLocation(player);
        }
    }
}

