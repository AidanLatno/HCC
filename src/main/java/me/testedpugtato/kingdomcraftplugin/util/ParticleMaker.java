package me.testedpugtato.kingdomcraftplugin.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ParticleMaker
{

    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ, double speed, double multiplier)
    {
        multiplier += Math.PI/200;

        for(double i = 0; i <= 2*Math.PI; i += Math.PI/(4*particleDensity))
        {
            double y = radius*Math.sin(i);
            double z = 0.2*multiplier;
            double x = radius*Math.cos(i);

            Vector v = new Vector(x,y,z);
            v = MathUtils.rotateFunction(v,loc);
            loc.add(v.getX(),v.getY(),v.getZ());

            GeneralUtils.SpawnParticle(loc, particle, particleAmount,(float)offsetX,(float)offsetY,(float)offsetZ,(float)speed);
            loc.subtract(v.getX(),v.getY(),v.getZ());
        }
    }
    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ,double speed)
    {
        createCircle(particle,loc,radius,particleAmount,particleDensity,offsetX,offsetY,offsetZ,speed,30);
    }
    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ)
    {
        createCircle(particle,loc,radius,particleAmount,particleDensity,offsetX,offsetY,offsetZ,0);
    }
    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, Vector offset, double speed)
    {
        createCircle(particle,loc,radius,particleAmount,particleDensity,offset.getX(), offset.getY(), offset.getX(), speed);
    }
    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, Vector offset)
    {
        createCircle(particle,loc,radius,particleAmount,particleDensity,offset,0);
    }
    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount, double particleDensity)
    {
        createCircle(particle,loc,radius,particleAmount,particleDensity,0,0,0);
    }
    public static void createCircle(Particle particle, Location loc, double radius, int particleAmount)
    {
        createCircle(particle,loc,radius,particleAmount,1);
    }
    public static void createCircle(Particle particle, Location loc, double radius)
    {
        createCircle(particle,loc,radius,1);
    }
    public static void createCircle(Particle particle, Location loc)
    {
        createCircle(particle,loc, 1);
    }



    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ, double speed, double multiplier)
    {

        for(double i = 0; i <= 2*Math.PI; i += Math.PI/(4*particleDensity))
        {
            double z = multiplier*i;
            double y = multiplier*radius*Math.sin(i);
            double x = multiplier*radius*Math.cos(i);

            Vector v = new Vector(x,y,z);
            v = MathUtils.rotateFunction(v,loc);
            loc.add(v.getX(),v.getY(),v.getZ());

            GeneralUtils.SpawnParticle(loc, particle, particleAmount,(float)offsetX,(float)offsetY,(float)offsetZ, (float)speed);
            loc.subtract(v.getX(),v.getY(),v.getZ());
        }
    }
    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ,double speed)
    {
        createHelix(particle,loc,radius,particleAmount,particleDensity,offsetX,offsetY,offsetZ,speed,30);
    }
    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ)
    {
        createHelix(particle,loc,radius,particleAmount,particleDensity,offsetX,offsetY,offsetZ,0);
    }
    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, Vector offset, double speed)
    {
        createHelix(particle,loc,radius,particleAmount,particleDensity,offset.getX(), offset.getY(), offset.getX(), speed);
    }
    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, Vector offset)
    {
        createHelix(particle,loc,radius,particleAmount,particleDensity,offset.getX(), offset.getY(), offset.getX());
    }
    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount, double particleDensity)
    {
        createHelix(particle,loc,radius,particleAmount,particleDensity,0,0,0);
    }
    public static void createHelix(Particle particle, Location loc, double radius, int particleAmount)
    {
        createHelix(particle,loc,radius,particleAmount,1);
    }
    public static void createHelix(Particle particle, Location loc, double radius)
    {
        createHelix(particle,loc,radius,1);
    }
    public static void createHelix(Particle particle, Location loc)
    {
        createHelix(particle,loc, 1);
    }


    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ, double speed, double multiplier)
    {
        multiplier += Math.PI/200;

        for(double theta = 0; theta <= 2*Math.PI; theta += Math.PI/(4*particleDensity))
        {
            for(double p = 0; p <= 2*Math.PI; p += Math.PI/(4*particleDensity)) {
                double y = radius * Math.sin(theta)*Math.sin(p);
                double z = radius * Math.cos(theta);
                double x = radius * Math.sin(theta)*Math.cos(p);

                Vector v = new Vector(x, y, z);
                v = MathUtils.rotateFunction(v, loc);
                loc.add(v.getX(), v.getY(), v.getZ());

                GeneralUtils.SpawnParticle(loc, particle, particleAmount, (float)offsetX, (float)offsetY, (float)offsetZ, (float)speed);
                loc.subtract(v.getX(), v.getY(), v.getZ());
            }
        }
    }
    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ,double speed)
    {
        createSphere(particle,loc,radius,particleAmount,particleDensity,offsetX,offsetY,offsetZ,speed,30);
    }
    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, double offsetX, double offsetY, double offsetZ)
    {
        createSphere(particle,loc,radius,particleAmount,particleDensity,offsetX,offsetY,offsetZ,0);
    }
    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, Vector offset, double speed)
    {
        createSphere(particle,loc,radius,particleAmount,particleDensity,offset.getX(), offset.getY(), offset.getX(), speed);
    }
    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount, double particleDensity, Vector offset)
    {
        createSphere(particle,loc,radius,particleAmount,particleDensity,offset.getX(), offset.getY(), offset.getX());
    }
    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount, double particleDensity)
    {
        createSphere(particle,loc,radius,particleAmount,particleDensity,0,0,0);
    }
    public static void createSphere(Particle particle, Location loc, double radius, int particleAmount)
    {
        createSphere(particle,loc,radius,particleAmount,1);
    }
    public static void createSphere(Particle particle, Location loc, double radius)
    {
        createSphere(particle,loc,radius,1);
    }
    public static void createSphere(Particle particle, Location loc)
    {
        createSphere(particle,loc, 1);
    }



    public static void createLine(Particle particle, Location start, Location end, float spacing, int particleAmount, double offsetX, double offsetY, double offsetZ, double speed)
    {
        double distance = start.distance(end);
        Vector p1 = start.toVector();
        Vector p2 = end.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(spacing);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            Location loc = new Location(start.getWorld(),p1.getX(), p1.getY(), p1.getZ());
            GeneralUtils.SpawnParticle(loc, particle, particleAmount,(float)offsetX,(float)offsetY,(float)offsetZ,(float)speed);
            length += spacing;
        }
    }
    public static void createLine(Particle particle, Location start, Location end, float spacing, int particleAmount, Vector offset, double speed)
    {
        createLine(particle,start,end,spacing,particleAmount,offset.getX(), offset.getY(), offset.getZ(),speed);
    }
    public static void createLine(Particle particle, Location start, Location end, float spacing, int particleAmount, double offsetX, double offsetY, double offsetZ)
    {
        createLine(particle,start,end,spacing,particleAmount,offsetX,offsetY,offsetZ,0);
    }
    public static void createLine(Particle particle, Location start, Location end, float spacing, int particleAmount, Vector offset)
    {
        createLine(particle,start,end,spacing,particleAmount,offset,0);
    }
    public static void createLine(Particle particle, Location start, Location end, float spacing, int particleAmount)
    {
        createLine(particle,start,end,spacing,particleAmount,0,0,0);
    }
    public static void createLine(Particle particle, Location start, Location end, float spacing)
    {
        createLine(particle,start,end,spacing,0);
    }
    public static void createLine(Particle particle, Location start, Location end)
    {
        createLine(particle,start,end,1);
    }

}
