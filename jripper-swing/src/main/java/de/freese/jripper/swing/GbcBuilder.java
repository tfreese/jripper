// Created: 17.10.2013
package de.freese.jripper.swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.Serial;

/**
 * Extends the {@link GridBagConstraints} by the Builder-Pattern.
 *
 * @author Thomas Freese
 */
public final class GbcBuilder extends GridBagConstraints {
    @Serial
    private static final long serialVersionUID = 9216852015033867169L;

    /**
     * Defaults:
     * <ul>
     * <li>anchor = WEST</li>
     * <li>insets = new Insets(2, 2, 2, 2)</li>
     * </ul>
     */
    public static GbcBuilder of(final int gridX, final int gridY) {
        GbcBuilder gbcBuilder = new GbcBuilder(gridX, gridY);
        gbcBuilder.anchorWest();
        gbcBuilder.insets(2, 2, 2, 2);

        return gbcBuilder;
    }

    private GbcBuilder(final int gridX, final int gridY) {
        super();

        this.gridx = gridX;
        this.gridy = gridY;
    }

    public GbcBuilder anchorCenter() {
        this.anchor = CENTER;

        return this;
    }

    public GbcBuilder anchorEast() {
        this.anchor = EAST;

        return this;
    }

    public GbcBuilder anchorNorth() {
        this.anchor = NORTH;

        return this;
    }

    public GbcBuilder anchorSouth() {
        this.anchor = SOUTH;

        return this;
    }

    public GbcBuilder anchorWest() {
        this.anchor = WEST;

        return this;
    }

    public GbcBuilder fillBoth() {
        this.fill = BOTH;

        weightX(1.0D);
        weightY(1.0D);

        return this;
    }

    public GbcBuilder fillHorizontal() {
        this.fill = HORIZONTAL;

        weightX(1.0D);
        weightY(0.0D);

        return this;
    }

    public GbcBuilder fillVertical() {
        this.fill = VERTICAL;

        weightX(0.0D);
        weightY(1.0D);

        return this;
    }

    public GbcBuilder gridHeight(final int gridHeight) {
        this.gridheight = gridHeight;

        return this;
    }

    public GbcBuilder gridWidth(final int gridWidth) {
        this.gridwidth = gridWidth;

        return this;
    }

    public GbcBuilder insets(final int top, final int left, final int bottom, final int right) {
        this.insets = new Insets(top, left, bottom, right);

        return this;
    }

    public GbcBuilder weightX(final double weightX) {
        this.weightx = weightX;

        return this;
    }

    public GbcBuilder weightY(final double weightY) {
        this.weighty = weightY;

        return this;
    }
}
