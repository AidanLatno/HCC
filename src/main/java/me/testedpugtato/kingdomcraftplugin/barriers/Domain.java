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
import java.util.List;

public class Domain {
    public Location center;
    public float radius;
    public Player caster;
    public List<LivingEntity> participants;
    public ArrayList<Particle> particles = new ArrayList<>();
    public int count;
    public float offSetX;
    public float offSetY;
    public float offSetZ;
    public float particleSpeed;
    public int tickRate;
    public int Energy;
    boolean expanded = false;

    public Domain(Player caster, int Energy, Location center, float radius, int tickRate,int count, float offSetX, float offSetY,float offSetZ, float particleSpeed)
    {
        this.caster = caster;
        this.Energy = Energy;
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
        Bukkit.getLogger().info("WORKING");
        participants = MathUtils.getEntitiesInSphere(center,(int)radius);


        // Store the task ID

        final int[] taskId = new int[1]; // Use an array to hold the taskId

        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomCraftPlugin.getInstance(), new Runnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks+=tickRate;
                float time = ticks / 20f; // Ensure floating-point division
                if (time > radius/5){
                    time = radius/5;
                    expanded = true;
                }
                float temp = time*5;
                for(Particle p : particles) {
                    ParticleMaker.createSphere(p, center, temp, count, temp / 5, offSetX, offSetY, offSetZ, particleSpeed);
                }

                caster.sendMessage("Domain Expanding");

                if(expanded) {
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
                Energy--;
                if(ticks % tickRate == 0) renderDomain();



                if(Logic() && Energy > 0) Bukkit.getScheduler().scheduleSyncDelayedTask(KingdomCraftPlugin.getInstance(),this);
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
