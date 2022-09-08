// Created: 17.10.2013
package de.freese.jripper.swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.Serial;

/**
 * Erweitert die {@link GridBagConstraints} um das Builder-Pattern.
 *
 * @author Thomas Freese
 */
public class GbcBuilder extends GridBagConstraints
{
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 9216852015033867169L;

    /**
     * Erstellt ein neues {@link GbcBuilder} Object.<br>
     * Defaults:
     * <ul>
     * <li>anchor = WEST</li>
     * <li>insets = new Insets(2, 2, 2, 2)</li>
     * </ul>
     */
    public GbcBuilder(final int gridX, final int gridY)
    {
        super();

        this.gridx = gridX;
        this.gridy = gridY;

        anchorWest();
        insets(2, 2, 2, 2);
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder anchorCenter()
    {
        this.anchor = CENTER;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder anchorEast()
    {
        this.anchor = EAST;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder anchorNorth()
    {
        this.anchor = NORTH;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder anchorSouth()
    {
        this.anchor = SOUTH;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder anchorWest()
    {
        this.anchor = WEST;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder fillBoth()
    {
        this.fill = BOTH;

        weightX(1.0D);
        weightY(1.0D);

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder fillHorizontal()
    {
        this.fill = HORIZONTAL;

        weightX(1.0D);
        weightY(0.0D);

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder fillVertical()
    {
        this.fill = VERTICAL;

        weightX(0.0D);
        weightY(1.0D);

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder gridHeight(final int gridHeight)
    {
        this.gridheight = gridHeight;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder gridWidth(final int gridWidth)
    {
        this.gridwidth = gridWidth;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder insets(final int top, final int left, final int bottom, final int right)
    {
        this.insets = new Insets(top, left, bottom, right);

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder weightX(final double weightX)
    {
        this.weightx = weightX;

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder weightY(final double weightY)
    {
        this.weighty = weightY;

        return this;
    }
}
