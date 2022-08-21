package STUDY.TIL.java.builder;

public class ClientPizza {
    public static void main(String[] args) {
        NyPizza nyPizza = new NyPizza.Builder(NyPizza.Size.MEDIUM)
                .addTopping(Pizza.Topping.Ham)
                .addTopping(Pizza.Topping.Mushroom)
                .build();

        SourcePizza sourcePizza = new SourcePizza.Builder()
                .addTopping(Pizza.Topping.Ham)
                .addTopping(Pizza.Topping.Onion)
                .sourceIn()
                .build();

    }
}
