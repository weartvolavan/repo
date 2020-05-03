/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
import java.util.Objects;

public class Location
{
    /** X координата. **/
    public int xCord;

    /** Y координата. **/
    public int yCord;


    public Location(int x, int y)
    {
        xCord = x;
        yCord = y;
    }

    public Location()
    {
        this(0, 0);
    }
/** equals() и hashCode() для сравнения двух объектов и корректной работы коллекций,
 * содержащих объекты класса Location. **/
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;

        if (!(obj instanceof Location)) return false;
        Location loc = (Location) obj;
        return xCord == loc.xCord && yCord == loc.yCord;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(xCord, yCord);
    }

}
