// Created: 17.10.2013
package de.freese.jripper.swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;

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
    private static final long serialVersionUID = 9216852015033867169L;

    /**
     * Erstellt ein neues {@link GbcBuilder} Object.<br>
     * Defaults:
     * <ul>
     * <li>anchor = WEST</li>
     * <li>insets = new Insets(2, 2, 2, 2)</li>
     * </ul>
     *
     * @param gridx int
     * @param gridy int
     */
    public GbcBuilder(final int gridx, final int gridy)
    {
        super();

        this.gridx = gridx;
        this.gridy = gridy;

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

        weightx(1.0D);
        weighty(1.0D);

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder fillHorizontal()
    {
        this.fill = HORIZONTAL;

        weightx(1.0D);
        weighty(0.0D);

        return this;
    }

    /**
     * @return {@link GbcBuilder}
     */
    public GbcBuilder fillVertical()
    {
        this.fill = VERTICAL;

        weightx(0.0D);
        weighty(1.0D);

        return this;
    }

    /**
     * @param gridheight int
     *
     * @return {@link GbcBuilder}
     */
    public GbcBuilder gridheight(final int gridheight)
    {
        this.gridheight = gridheight;

        return this;
    }

    /**
     * @param gridwidth int
     *
     * @return {@link GbcBuilder}
     */
    public GbcBuilder gridwidth(final int gridwidth)
    {
        this.gridwidth = gridwidth;

        return this;
    }

    /**
     * @param top int
     * @param left int
     * @param bottom int
     * @param right int
     *
     * @return {@link GbcBuilder}
     */
    public GbcBuilder insets(final int top, final int left, final int bottom, final int right)
    {
        this.insets = new Insets(top, left, bottom, right);

        return this;
    }

    /**
     * @param weightx double
     *
     * @return {@link GbcBuilder}
     */
    public GbcBuilder weightx(final double weightx)
    {
        this.weightx = weightx;

        return this;
    }

    /**
     * @param weighty double
     *
     * @return {@link GbcBuilder}
     */
    public GbcBuilder weighty(final double weighty)
    {
        this.weighty = weighty;

        return this;
    }
}
