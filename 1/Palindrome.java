public class Palindrome {

    public static String reverseString(String stroka){
        return new StringBuilder(stroka).reverse().toString(); //Разворот
    }

    static boolean isPalindrome(String stroka)
    {
        return reverseString(stroka).equals(stroka); //Сравнение с разворотом
    }

    public static void main(String[] args)
    {
        String stroka = "довод";

        System.out.print(isPalindrome(stroka.toString())); //Вывод
    }
}
