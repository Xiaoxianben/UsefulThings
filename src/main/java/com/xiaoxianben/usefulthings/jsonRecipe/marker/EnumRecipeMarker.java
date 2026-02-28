package com.xiaoxianben.usefulthings.jsonRecipe.marker;

public enum EnumRecipeMarker {
    READER1(new RecipeMarker1(), 0),
    ;

    public final IRecipeMarker marker;
    public final int index;

    EnumRecipeMarker(IRecipeMarker marker, int index) {
        this.marker = marker;
        this.index = index;
    }
}
