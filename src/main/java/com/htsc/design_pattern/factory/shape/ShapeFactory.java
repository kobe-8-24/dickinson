package com.htsc.design_pattern.factory.shape;

import org.thymeleaf.util.StringUtils;

public class ShapeFactory {

    public Shape getShape(String shapeType) {
        if (StringUtils.isEmpty(shapeType)) {
            return null;
        }

        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        }

        if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }

        if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }

        return null;
    }
}
