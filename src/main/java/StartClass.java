import Net.NetSpider;
import Net.NetSpiderConcurrent;

public class StartClass {

    public static void main(String[] args) {
//        NetSpider netSpider = new NetSpider("http://moiglaza.ru/");
//        netSpider.checkLinksInDomain();
        NetSpiderConcurrent spider = new NetSpiderConcurrent("http://www.camping.ru/");
        spider.checkLinksInDomain();

    }

    public static int [] getArray (int size){
        int [] mass = new int[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*(30000));
        }
        return mass;
    }

    public static long [] getLongArray (int size){
        long [] mass = new long[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (long) (Math.random()*(size*3));
        }
        return mass;
    }

    public static Integer [] getArrayInteger (int size){
        Integer [] mass = new Integer[size];
        for (int i=0; i<mass.length;i++){
            mass[i] = (int)(Math.random()*(size*3));
        }
        return mass;
    }

}
