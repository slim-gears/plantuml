/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2023, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.skin.rose;

import net.sourceforge.plantuml.ISkinSimple;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.graphic.SymbolContext;
import net.sourceforge.plantuml.skin.AbstractTextualComponent;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.style.Style;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UPolygon;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;

final public class ComponentRoseNoteHexagonal extends AbstractTextualComponent {

	private final int cornersize = 10;
	private final SymbolContext symbolContext;

	public ComponentRoseNoteHexagonal(Style style, Display strings, ISkinSimple spriteContainer) {
		super(style, spriteContainer.wrapWidth(), 12, 12, 4, spriteContainer, strings, false);

		this.symbolContext = style.getSymbolContext(spriteContainer.getThemeStyle(), getIHtmlColorSet());

	}

	@Override
	final public double getPreferredWidth(StringBounder stringBounder) {
		final double result = getTextWidth(stringBounder) + 2 * getPaddingX();
		return result;
	}

	@Override
	final public double getPreferredHeight(StringBounder stringBounder) {
		return getTextHeight(stringBounder) + 2 * getPaddingY();
	}

	@Override
	public double getPaddingX() {
		return 5;
	}

	@Override
	public double getPaddingY() {
		return 5;
	}

	@Override
	protected void drawInternalU(UGraphic ug, Area area) {
		final StringBounder stringBounder = ug.getStringBounder();
		final int textHeight = (int) getTextHeight(stringBounder);

		int x2 = (int) getTextWidth(stringBounder);
		final double diffX = area.getDimensionToUse().getWidth() - getPreferredWidth(stringBounder);
		if (diffX < 0)
			throw new IllegalArgumentException();

		if (area.getDimensionToUse().getWidth() > getPreferredWidth(stringBounder))
			x2 = (int) (area.getDimensionToUse().getWidth() - 2 * getPaddingX());

		final UPolygon polygon = new UPolygon();
		polygon.addPoint(cornersize, 0);
		polygon.addPoint(x2 - cornersize, 0);
		polygon.addPoint(x2, textHeight / 2);
		polygon.addPoint(x2 - cornersize, textHeight);
		polygon.addPoint(cornersize, textHeight);
		polygon.addPoint(0, textHeight / 2);
		polygon.addPoint(cornersize, 0);

		ug = symbolContext.apply(ug);
		polygon.setDeltaShadow(symbolContext.getDeltaShadow());
		ug.draw(polygon);
		ug = ug.apply(new UStroke());

		getTextBlock().drawU(ug.apply(new UTranslate(getMarginX1() + diffX / 2, getMarginY())));

	}

}
