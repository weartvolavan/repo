import java.util.HashMap;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    /**  открытые путевые точки **/
    private HashMap<Location, Waypoint> openWaypoints;

    /**  закрытые путевые точки  **/
    private HashMap<Location, Waypoint> closedWaypoints;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;

        openWaypoints = new HashMap<Location, Waypoint>();
        closedWaypoints = new HashMap<Location, Waypoint>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

/**возвращает вершину с наименьшей общей стоимостью из набора открытых вершин.**/
    public Waypoint getMinOpenWaypoint()
    {
        Waypoint sol = null;

        float min = Float.POSITIVE_INFINITY;
        float totalCost = 0;

        for(Waypoint p : openWaypoints.values())
        {
            totalCost = p.getTotalCost();

            if(min > totalCost)
            {
                min = totalCost;
                sol = p;
            }
        }

        return sol;
    }
/**добавляет вершину в набор открытых вершин в случае если такой вершины в наборе нет,
 *  или заменяет вершину из набора на переданную в метод, если вершина с такими координатами есть,
 *  но имеет большую стоимость.**/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Waypoint other = openWaypoints.get(newWP.getLocation());

        if(other == null || newWP.getPreviousCost() < other.getPreviousCost())
        {
            openWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }

        return false;
    }


    /** Возвращает количество вершин **/
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }
/**перемещает вершину из набора открытых вершин а набор закрытых вершин.**/
    public void closeWaypoint(Location loc)
    {
        Waypoint point = openWaypoints.remove(loc);

        if(point != null)
        {
            closedWaypoints.put(loc, point);
        }
    }
/**проверяет, является ли данная вершина закрытой.**/
    public boolean isLocationClosed(Location loc)
    {
        return closedWaypoints.containsKey(loc);
    }
}