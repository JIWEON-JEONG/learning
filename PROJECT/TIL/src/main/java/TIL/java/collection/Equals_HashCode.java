package TIL.java.collection;

import java.util.*;

public class Equals_HashCode {
    public static void main(String[] args) {
        Product a = new Product("신라면", 600);
        Product b = new Product("신라면", 600);

        // 두 객체의 내부값은 같으므로.. 동일하다고 보는게 맞음
        System.out.println(a.equals(b));

        System.out.println(a.hashCode() + " , " + b.hashCode());
//        List<Integer> c = Arrays.asList(1,2,3);
//        List<Integer> d = Arrays.asList(1,2,3);
//
//        System.out.println(c.equals(d));

        //같이 오버라이딩 되야하는 이유
        HashSet<Product> set = new HashSet<>();
        set.add(a);
        set.add(b);

        for (Product product : set) {
            System.out.println(product.name + " , " + product.price);
        }

        System.out.println(a);
        System.out.println(a.hashCode());

    }

    static class Product {
        private String name;
        private Integer price;

        public Product(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public Integer getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Objects.equals(name, product.name) && Objects.equals(price, product.price);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }
    }
}
