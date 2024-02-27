package me.testedpugtato.kingdomcraftplugin.barriers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Domain {
    public Location center;
    public float radius;
    public ArrayList<LivingEntity> participants;
    public Particle particle;
    public int count;
    public float offSetX;
    public float offSetY;
    public float offSetZ;
    public float particleSpeed;
    boolean expanded = false;

    public Domain(Location center, float radius, Particle particle,int count, float offSetX, float offSetY,float offSetZ, float particleSpeed)
    {
        this.center = center;
        this.radius = radius;
        this.particle = particle;
        this.count = count;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.offSetZ = offSetZ;
        this.particleSpeed = particleSpeed;
    }

    public void ExpandDomain()
    {
        ArrayList<Entity> list = CombatManager.getEntitiesAroundPoint(center,radius);
        for(Entity entity : list)
        {
            if(entity instanceof LivingEntity) participants.add((LivingEntity) entity);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;
                float time = ticks/20;
                if (time > radius){
                    time = radius;
                    expanded = true;
                }
                ParticleMaker.createSphere(particle,center,time,count,offSetX,offSetY,offSetZ,particleSpeed);
                if(!(ticks/20 > radius+5)) Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
            }
        },1);

    }

}
