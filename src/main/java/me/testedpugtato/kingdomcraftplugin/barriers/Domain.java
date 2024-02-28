package me.testedpugtato.kingdomcraftplugin.barriers;

import me.testedpugtato.kingdomcraftplugin.KingdomCraftPlugin;
import me.testedpugtato.kingdomcraftplugin.util.CombatManager;
import me.testedpugtato.kingdomcraftplugin.util.MathUtils;
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
    public Player caster;
    public ArrayList<LivingEntity> participants;
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
       /* Bukkit.getLogger().info("WORKING");
        ArrayList<Entity> list = CombatManager.getEntitiesAroundPoint(center,radius);
        for(Entity entity : list)
        {
            if(entity instanceof LivingEntity) participants.add((LivingEntity) entity);
        }*/

        // Store the task ID

        final int[] taskId = new int[1]; // Use an array to hold the taskId

        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks+=tickRate;
                float time = ticks / 20f; // Ensure floating-point division
                if (time > radius){
                    time = radius;
                    expanded = true;
                }

                for(Particle p : particles) {
                    ParticleMaker.createSphere(p, center, time, count, time / 3, offSetX, offSetY, offSetZ, particleSpeed);
                }

                caster.sendMessage("Domain Expanding");

                if(ticks / 20f > radius) {
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

                if(ticks % tickRate == 0) renderDomain();

                caster.sendMessage("Main Loop");

                if(Logic() && ticks/20f < upTime) Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this);
                else End();
            }
        });
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
