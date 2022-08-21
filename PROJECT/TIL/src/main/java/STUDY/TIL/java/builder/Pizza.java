package STUDY.TIL.java.builder;

import java.util.EnumSet;
import java.util.Objects;

public abstract class Pizza {
    public enum Topping{
        Ham,Mushroom,Onion
    }
    final EnumSet<Topping> toppings;

    //Builder 자신의 하위 타입을 상속 받는다.
    abstract static class Builder<T extends Builder<T>>{
        EnumSet<Topping> builderToppings = EnumSet.noneOf(Topping.class); //처음은 비어있다.
        //빌더 내부 함수
        public T addTopping(Topping topping){
            builderToppings.add(Objects.requireNonNull(topping));
            return self();
        }
        abstract Pizza build();
        protected abstract T self();
    }

    Pizza(Builder<?> builder){
        toppings = builder.builderToppings;
    }
}
