package me.testedpugtato.kingdomcraftplugin.util;

import org.bukkit.Location;

import org.bukkit.util.Vector;


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
    public static float distanceBetween(Location loc1, Location loc2) {
        return (float) Math.sqrt(Math.pow(loc1.getX() - loc2.getX(), 2) + Math.pow(loc1.getY() - loc2.getY(), 2) + Math.pow(loc1.getZ() - loc2.getZ(), 2));
    }
}
