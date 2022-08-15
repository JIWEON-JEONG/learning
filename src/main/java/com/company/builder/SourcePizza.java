package com.company.builder;

public class SourcePizza extends Pizza{
    private final boolean sourceInside;

    public static class Builder extends Pizza.Builder<Builder> {

        //기본값
        private boolean builderSourceInside = false;

        public Builder sourceIn() {
            builderSourceInside = true;
            return this;
        }

        @Override
        SourcePizza build() {
            return new SourcePizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private SourcePizza(Builder builder) {
        super(builder);
        sourceInside = builder.builderSourceInside;
    }
}
