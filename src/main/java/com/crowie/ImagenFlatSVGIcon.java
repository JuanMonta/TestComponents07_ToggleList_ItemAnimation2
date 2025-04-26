
package com.crowie;

import com.formdev.flatlaf.extras.FlatSVGIcon;


public class ImagenFlatSVGIcon {
    
    public static FlatSVGIcon svgImagen(String rutaSVG, int width, int height){
        return new FlatSVGIcon(rutaSVG, width, height, ImagenFlatSVGIcon.class.getClassLoader());
    }
    
}
