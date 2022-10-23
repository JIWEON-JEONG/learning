package TIL.java.builder;

import java.util.Objects;

public class NyPizza extends Pizza {
    public enum Size{
        SMALL,MEDIUM,LARGE
    }

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder>{
        private final Size size;

        public Builder(Size size){
            //Null인지 아닌지 체크한 후 세팅 해준다.
            this.size = Objects.requireNonNull(size);
        }

        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }
}
