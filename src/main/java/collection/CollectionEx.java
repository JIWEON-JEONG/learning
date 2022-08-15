package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Info implements Comparable<Info>{
    public int score;
    public String name;

    public Info(int score,String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public int compareTo(Info o) {
        return this.score - o.score;
    }

    @Override
    public String toString() {
        return "Info{" +
                "score=" + score +
                ", name='" + name + '\'' +
                '}';
    }
}

public class CollectionEx{
    public void test() {
        List<Info> info = new ArrayList<>();
        info.add(new Info(95, "Jiweon"));
        info.add(new Info(88, "Rookey"));

        Collections.sort(info);
        Iterator<Info> iter = info.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
