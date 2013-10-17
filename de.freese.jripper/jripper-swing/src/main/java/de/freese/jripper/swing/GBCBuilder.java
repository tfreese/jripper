/**
 * Created: 17.10.2013
 */

package de.freese.jripper.swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Erweitert die {@link GridBagConstraints} um das Builder-Pattern.
 * 
 * @author Thomas Freese
 */
public class GBCBuilder extends GridBagConstraints
{
	/**
	 *
	 */
	private static final long serialVersionUID = 9216852015033867169L;

	/**
	 * Erstellt ein neues {@link GBCBuilder} Object.<br>
	 * Defaults:
	 * <ul>
	 * <li>anchor = WEST</li>
	 * <li>insets = new Insets(2, 2, 2, 2)</li>
	 * </ul>
	 * 
	 * @param gridx int
	 * @param gridy int
	 */
	public GBCBuilder(final int gridx, final int gridy)
	{
		super();

		this.gridx = gridx;
		this.gridy = gridy;
		anchorWest();
		insets(2, 2, 2, 2);
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder anchorCenter()
	{
		this.anchor = CENTER;

		return this;
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder anchorNorth()
	{
		this.anchor = NORTH;

		return this;
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder anchorSouth()
	{
		this.anchor = SOUTH;

		return this;
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder anchorWest()
	{
		this.anchor = WEST;

		return this;
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder fillBoth()
	{
		this.fill = BOTH;

		weightx(1.0D);
		weighty(1.0D);

		return this;
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder fillHorizontal()
	{
		this.fill = HORIZONTAL;

		weightx(1.0D);
		weighty(0.0D);

		return this;
	}

	/**
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder fillVertical()
	{
		this.fill = VERTICAL;

		weightx(0.0D);
		weighty(1.0D);

		return this;
	}

	/**
	 * @param gridheight int
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder gridheight(final int gridheight)
	{
		this.gridheight = gridheight;

		return this;
	}

	/**
	 * @param gridwidth int
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder gridwidth(final int gridwidth)
	{
		this.gridwidth = gridwidth;

		return this;
	}

	/**
	 * @param top int
	 * @param left int
	 * @param bottom int
	 * @param right int
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder insets(final int top, final int left, final int bottom, final int right)
	{
		this.insets = new Insets(top, left, bottom, right);

		return this;
	}

	/**
	 * @param weightx double
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder weightx(final double weightx)
	{
		this.weightx = weightx;

		return this;
	}

	/**
	 * @param weighty double
	 * @return {@link GBCBuilder}
	 */
	public GBCBuilder weighty(final double weighty)
	{
		this.weighty = weighty;

		return this;
	}
}
