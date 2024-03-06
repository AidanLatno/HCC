package me.testedpugtato.kingdomcraftplugin.barriers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.util.ParticleMaker;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class Domain {
    public Location center;
    public float radius;
    public Player caster;
    public ArrayList<LivingEntity> participants = new ArrayList<>();
    public ArrayList<Particle> particles = new ArrayList<>();
    public int count;
    public float offSetX;
    public float offSetY;
    public float offSetZ;
    public float particleSpeed;
    public int tickRate;
    public float upTime; //seconds
    boolean expanded = false;

    public Domain(Player caster, float upTime, Location center, float radius, int tickRate,int count, float offSetX, float offSetY,float offSetZ, float particleSpeed)
    {
        this.caster = caster;
        this.upTime = upTime;
        this.center = center;
        this.radius = radius;
        this.count = count;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.offSetZ = offSetZ;
        this.tickRate = tickRate;
        this.particleSpeed = particleSpeed;
        construct();
    }

    //TO BE OVERRIDEN
    public void construct()
    {
        // Add particles
    }

    final public void ExpandDomain()
    {
        center.getWorld().playSound(center, Sound.ENTITY_WITHER_SPAWN, SoundCategory.MASTER,100,2);
        Collection<LivingEntity> list = center.getNearbyLivingEntities(radius,radius,radius);
        for(Entity entity : list)
        {
            if(entity.getLocation().distance(center) <= radius && entity != caster) {
                participants.add((LivingEntity) entity);
                caster.sendMessage(entity.getName());
            }
        }

        // Store the task ID

        final int[] taskId = new int[1]; // Use an array to hold the taskId

        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks+=tickRate;
                float time = (ticks / 20f)*4;
                if (time > radius){
                    time = radius;
                    expanded = true;
                }

                center.getWorld().playSound(center, Sound.BLOCK_BEACON_POWER_SELECT, SoundCategory.MASTER,1,2);

                for(Particle p : particles) {
                    ParticleMaker.createSphere(p, center, time, count, time / 3, offSetX, offSetY, offSetZ, particleSpeed);
                }

                if(time >= radius) {
                    mainLoop();
                    Bukkit.getScheduler().cancelTask(taskId[0]); // Use the taskId from the array
                }
            }
        }, 0, tickRate);

    }

    final public void renderDomain()
    {
        for(Particle p : particles) {
            ParticleMaker.createSphere(p, center, radius, count, radius / 3, offSetX, offSetY, offSetZ, particleSpeed);
        }
    }

    final public void mainLoop()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;
            @Override
            public void run() {
                ticks++;

                caster.sendMessage("MAIN LOOP");

                if(ticks % tickRate == 0) renderDomain();


                if(Logic() && ticks/20f < upTime) Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this,1);
                else End();
            }
        },1);
    }

    // TO BE OVERRIDEN
    public boolean Logic()
    {
        return true;
    }

    public void End()
    {
        caster.sendMessage("End");
    }

}
