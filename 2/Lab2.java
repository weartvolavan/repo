package lab2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class Lab2 {

    /**
     * creates three points defined by args;
     * prints Area of triangle based o these points or
     * prints if two of them are equal
     * @param args
     */
    public static void main(String[] args) {
        List<Point3d> points = createThreePoints(args);
        if (points.get(0).equals(points.get(1)) || points.get(1).equals(points.get(2)) || points.get(2).equals(points.get(0))) 
        {
            System.out.println("Две точки равны");
        } 
        else 
            {
            System.out.println("Площадь = " + computeArea(points.get(0), points.get(1), points.get(2)));
        }
    }

    public static List<Point3d> createThreePoints(String[] strings) {
        List<Point3d> points = new ArrayList<>();
        for (int i = 0; i < strings.length; i += 3) {
            points.add(new Point3d(parseDouble(strings[i]), parseDouble(strings[i + 1]), parseDouble(strings[i + 2])));
        }
        return points;
    }

    public static double computeArea(Point3d p1, Point3d p2, Point3d p3) {
        double a = p1.distanceTo(p2);
        double b = p2.distanceTo(p3);
        double c = p3.distanceTo(p1);
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}
