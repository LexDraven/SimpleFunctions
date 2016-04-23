
public class Recursion {

    public int getTriangleDigit(int num){
        if (num==1){
            return 1;
        }
        else return (num+getTriangleDigit(num-1));
    }

    public int getFactorial(int num){
        if (num==0){
            return 1;
        }
        else return (num*getFactorial(num-1));
    }
}
