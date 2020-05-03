package lab2;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.pow;


  //двумерный класс точки.
 
public class Point3d {
    public static void main(String[] args) {
        System.out.println();
    }

    //координаты
    private double xCord;
    private double yCord;
    private double zCord;
     // Конструктор инициализации
    public Point3d(double x, double y, double z) 
    {
        xCord = x;
        yCord = y;
        zCord = z;
    }
    public Point3d() 
    {
//Вызовите конструктор с двумя параметрами и определите источник.
        this(0, 0, 0);
    }
    public double distanceTo(Point3d point) 
    {
        return BigDecimal.valueOf(Math.sqrt(pow(xCord - point.getX(), 2)
                                            + pow(yCord - point.getY(), 2)
                                            + pow(zCord - point.getZ(), 2)))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point3d)) return false;

        Point3d point = (Point3d) obj;
        return xCord == point.getX()
                && yCord == point.getY()
                && zCord == point.getZ();
    }

    /**
     * Возвращение координаты X
     * Возвращение координаты Y
     * Возвращение координаты Z
     **/
    public double getX() {
        return xCord;
    }
    public double getY() {
        return yCord;
    }
    public double getZ() {
        return zCord;
    }

    /**
     * Установка значения координаты X.
     * Установка значения координаты Y.
     * Установка значения координаты Z.
     **/
    public void setX(double val) {
        xCord = val;
    }
    public void setY(double val) {
        yCord = val;
    }
    public void setZ(double val) {
        zCord = val;
    }

}