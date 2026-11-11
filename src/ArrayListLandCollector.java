import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ArrayListLandCollector implements Collector<Land, ArrayList<Land>, ArrayList<Land>> {
    
    public static ArrayListLandCollector toArrayListLand()
    {
        return new ArrayListLandCollector();
    }

    public Supplier<ArrayList<Land>> supplier()
    {
        return ArrayList::new;
    }

    public BiConsumer<ArrayList<Land>, Land> accumulator()
    {
        return ArrayList::add;
    }

    public BinaryOperator<ArrayList<Land>> combiner()
    {
        return (listX, listY) -> {listX.addAll(listY); return listX;};
    }

    public Function<ArrayList<Land>, ArrayList<Land>> finisher()
    {
        return listX -> listX;
    }

    public Set<Characteristics> characteristics() 
    {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
