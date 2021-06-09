package Module.Graphics;

import Module.Math.Vector2DConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ModuleShape {
    private static final double ellipseWidth = 50;
    private static double prevTheta = 0;
    private static BufferedImage image;

    public static Shape createShape(final Point2D.Double state) {
        //rescale to given position
        double r = ellipseWidth * Vector2DConverter.getScale();
        return new Ellipse2D.Double(state.x - (r / 2), state.y - (r / 2), r, r);
    }

    public static BufferedImage load() {
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(ModuleShape.class.getClassLoader().getResource("rocket.png")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage createImage(double theta) {
        if (theta == prevTheta) return image;
        prevTheta = theta;
        final double rads = Math.toRadians(theta);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2., h / 2.);
        at.rotate(rads, 0, 0);
        at.translate(-image.getWidth() / 2., -image.getHeight() / 2.);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        rotateOp.filter(image, rotatedImage);
        return rotatedImage;
    }

}
