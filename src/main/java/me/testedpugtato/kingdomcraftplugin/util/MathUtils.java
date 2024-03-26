package me.testedpugtato.kingdomcraftplugin.util;

import org.bukkit.Location;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class MathUtils
{
    public static Vector rotateFunction(Vector v, Location loc)
    {
        // Turn yaw into Radians
        double yawR = loc.getYaw()/180*Math.PI;
        double pitchR = loc.getPitch()/180*Math.PI;

        // rotate vector
        v = rotateAroundX(v,pitchR);
        v = rotateAroundY(v,-yawR);
        return v;
    }
    public static Vector rotateAroundX(Vector v, double a)
    {
        double Y = Math.cos(a)*v.getY() - Math.sin(a)*v.getZ();
        double Z = Math.sin(a)*v.getY() + Math.cos(a)*v.getZ();
        return v.setY(Y).setZ(Z);
    }
    public static Vector rotateAroundY(Vector v, double a)
    {
        double X = Math.cos(a)*v.getX() + Math.sin(a)*v.getZ();
        double Z = -Math.sin(a)*v.getX() + Math.cos(a)*v.getZ();
        return v.setX(X).setZ(Z);
    }
    public static Vector rotateAroundZ(Vector v, double a)
    {
        double X = Math.cos(a)*v.getX() - Math.sin(a)*v.getY();
        double Y = Math.sin(a)*v.getX() + Math.cos(a)*v.getY();
        return v.setX(X).setY(Y);
    }

    public static float levelInter(float low, float high, int level)
    {
        float percent = (float) level / 100;
        float difference = high - low;
        return low + (difference * percent);

    }
    public static float levelInter(double low, double high, int level)
    {
        float percent = (float) level / 100;
        float difference = (float)high - (float)low;
        return (float)low + (difference * percent);
    }

    public static List<LivingEntity> getEntitiesInCone(Location location,int distance,int degree)
    {
        List<LivingEntity> entitiesInCone = new ArrayList<>();
        Vector playerDirection = location.getDirection();
        Vector playerLocation = location.toVector();

        // Get all entities in a cone facing out from the players direction
        for (LivingEntity target : location.getNearbyLivingEntities(distance*1.5f,distance*1.5f,distance*1.5f)) {

            Vector targetLocation = target.getLocation().toVector();
            Vector directionToTarget = targetLocation.subtract(playerLocation);

            // Calculate the angle between the player's direction and the direction to the target
            double angleToTarget = playerDirection.angle(directionToTarget);

            // Convert the angle parameter to radians for comparison
            double angleRadians = Math.toRadians(degree);

            // Check if the target is within the distance and the angle
            if (directionToTarget.length() <= distance && angleToTarget <= angleRadians) {
                entitiesInCone.add(target);
            }
        }
        return entitiesInCone;
    }
    public static List<LivingEntity> getEntitiesInCone(Location location)
    {
        return getEntitiesInCone(location,5,100);
    }

    public static List<LivingEntity> getEntitiesInSphere(Location location, int radius, Vector boundingBox)
    {
        List<LivingEntity> entities = new ArrayList<>();

        for(LivingEntity entityInBox : location.getNearbyLivingEntities(boundingBox.getX(),boundingBox.getY(),boundingBox.getZ()))
        {
            if(entityInBox.getLocation().distance(location) < radius)
            {
                entities.add(entityInBox);
            }
        }

        return entities;
    }

    public static List<LivingEntity> getEntitiesInSphere(Location location, int radius)
    {
        return getEntitiesInSphere(location,radius,new Vector(radius,radius,radius));
    }
}
