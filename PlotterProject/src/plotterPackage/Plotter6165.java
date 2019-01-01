/**
 * superseded by plotterPackage.PlotterTest
 */

package plotterPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;

/**
 * @author 46ac4334
 *
 */
public class Plotter6165 extends JFrame {

	public class LegendItem {
		private final Font font;
		private final double h;
		private final String text;
		private final double w;
		private final double x;
		private final double y;

		public LegendItem(final String text0, final Font font0) {
			this.font = font0;
			this.text = text0;
			this.x = Double.NaN;
			this.y = Double.NaN;
			this.w = Double.NaN;
			this.h = Double.NaN;
		}

		public LegendItem(final String text2, final Font font2, final double x, final double y, final double w,
				final double h) {
			this.font = font2;
			this.text = text2;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}

	/**
	 * @author 46ac4334
	 *
	 */
	public class PlotterPane extends JPanel
			implements MouseMotionListener, MouseListener, MouseWheelListener, ComponentListener {

		public static final int ALL = 5;

		public static final int BOT = 4;

		public static final int LEFT = 3;

		private static final int NONE = 0;

		public static final int RIGHT = 1;

		private static final long serialVersionUID = 1L;

		public static final int TOP = 2;

		/**
		 * Bounds the plot. Anything outside this box is legend, label, title, etc.
		 */
		private Shape boundingBox = null;

		private final boolean boxSelected = false;

		private final BasicStroke boxStroke = new BasicStroke(0.5f);

		public List<Color> colors = new ArrayList<>();

		private final int dragSide = PlotterPane.NONE;

		private boolean drawMarkers = false;

		private Insets insets;

		private final JTextArea jTextArea = new JTextArea("Edit the Caption here.");

		private final Color majorGridColor = Color.getHSBColor(.5f, .5f, .75f);

		private final Color[] markerColors = new Color[] { Color.getHSBColor(.3f, 1f, .85f),
				Color.getHSBColor(.3f, 1f, .65f), Color.getHSBColor(.3f, 1f, .45f) };

		private final List<Shape> markers = new ArrayList<>();

		private int mouseDownButton;

		private Point mouseDownPoint;

		private Point mousePoint;

		private final Insets oldInsets = new Insets(0, 0, 0, 0);

		private AffineTransform oldPan;

		/**
		 * If true, an old transient object is on screen.
		 */
		public boolean oldShape = false;

		// private AffineTransform pathToUnitBoxTransform;

		private double oldTx = 0;

		private double oldTy = 0;

		private final AffineTransform pan = AffineTransform.getTranslateInstance(0, 0);

		private Shape path = null;

		public List<Shape> paths = new ArrayList<>();

		private Color plotColor = Color.getHSBColor(0f, 0f, 0f);

		private BasicStroke plotStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		/**
		 * Transforms the plot path from its original unit square to the area over which
		 * it is to be plotted.
		 */
		public AffineTransform plotTransform;

		private AffineTransform plotTransform0;

		private double s = 1;

		private AffineTransform scaleInstance;

		private final Color selectedColor = Color.getHSBColor(.75f, 1f, 1f);

		private Shape selectedMarker;

		private final BasicStroke selectedStroke = new BasicStroke(2);

		private final Stroke stroke2 = new BasicStroke(1.4f);

		private final Stroke stroke3 = new BasicStroke(1.0f);

		public List<Stroke> strokes = new ArrayList<>();

		private final JPopupMenu textAreaPopup = new JPopupMenu();

		private AffineTransform transform;

		public Color transientColor = Color.WHITE;

		public Shape transientShape = null;

		public String transientText;

		public int transientTextX;

		public int transientTextY;

		private AffineTransform translateInstance;

		private int tx = 0;

		private int ty = 0;

		/**
		 * Constructor.
		 */
		public PlotterPane() {
			this.setPreferredSize(Plotter6165.this.getPlotterPaneDefaultDimension());
			this.setBackground(Color.WHITE);
			this.insets = Plotter6165.this.insets;
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
			this.addMouseWheelListener(this);
			this.addComponentListener(this);
			this.setLayout(null);
			this.jTextArea.setLineWrap(true);
			this.jTextArea.setWrapStyleWord(true);
			this.jTextArea.setComponentPopupMenu(this.textAreaPopup);
			this.jTextArea.setEditable(false);
			final JMenuItem fontItem = new JMenuItem("Font");
			this.textAreaPopup.add(fontItem);
			final ActionListener actionListener = arg0 -> PlotterPane.this.chooseFontFor(PlotterPane.this.jTextArea);
			fontItem.addActionListener(actionListener);
			final JMenuItem sizeItem = new JMenuItem("Font Size");
			this.textAreaPopup.add(sizeItem);
			sizeItem.addActionListener(arg0 -> PlotterPane.this.chooseFontSizeFor(this.jTextArea));
			this.add(this.jTextArea);
		}

		/**
		 * @param arg0
		 */
		public PlotterPane(final boolean arg0) {
			super(arg0);
			this.setPreferredSize(Plotter6165.this.getPlotterPaneDefaultDimension());
		}

		/**
		 * @param arg0
		 */
		public PlotterPane(final LayoutManager arg0) {
			super(arg0);
			this.setPreferredSize(Plotter6165.this.getPlotterPaneDefaultDimension());
		}

		/**
		 * @param arg0
		 * @param isDoubleBuffered
		 */
		public PlotterPane(final LayoutManager arg0, final boolean isDoubleBuffered) {
			super(arg0, isDoubleBuffered);
			this.setPreferredSize(Plotter6165.this.getPlotterPaneDefaultDimension());
		}

		protected void chooseFontFor(final JTextArea textArea) {
			final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			final String[] availableFontFamilyNames = ge.getAvailableFontFamilyNames();
			final Object selectedFont = JOptionPane.showInternalInputDialog(this,
					"Choose Font (Currently " + textArea.getFont().getFontName() + "):", "Font Selector",
					JOptionPane.PLAIN_MESSAGE, null, availableFontFamilyNames, availableFontFamilyNames[0]);
			if (selectedFont != null) {
				final Font oldFont = textArea.getFont();
				final int b = oldFont.getStyle();
				final int c = oldFont.getSize();
				final Font newFont = new Font((String) selectedFont, b, c);
				textArea.setFont(newFont);
			}
		}

		protected void chooseFontSizeFor(final JTextArea textArea) {
			final Object selectedSize = JOptionPane.showInternalInputDialog(this,
					"Set Font Size (currently " + textArea.getFont().getSize() + "):", "Font Size",
					JOptionPane.PLAIN_MESSAGE);
			if (selectedSize != null) {
				final float size = Float.parseFloat((String) selectedSize);
				final Font oldFont = textArea.getFont();
				final Font newFont = oldFont.deriveFont(size);
				textArea.setFont(newFont);
			}
		}

		@Override
		public void componentHidden(final ComponentEvent e) {
		}

		@Override
		public void componentMoved(final ComponentEvent e) {
		}

		@Override
		public void componentResized(final ComponentEvent e) {
			this.repaint();
			final int n = this.getComponentCount();
			for (int i = 0; i < n; ++i) {
				this.getComponent(i).invalidate();
				this.getComponent(i).repaint();
			}
		}

		@Override
		public void componentShown(final ComponentEvent e) {
		}

		/**
		 * @param g
		 * @param pathIterator2
		 * @param sigDig
		 */
		private void drawXTickLabels(final Graphics2D g, final PathIterator pathIterator2, final int sigDig) {

			while (!pathIterator2.isDone()) {
				final double[] coords = new double[6];
				pathIterator2.currentSegment(coords);
				final String format = "%." + String.valueOf(sigDig) + "g";
				final String tickText = String.format(format, coords[0]).replaceFirst("(\\.\\d*)0+($|e)", "$1$2");
				final FontMetrics fontMetrics = g.getFontMetrics();
				final Rectangle2D stringBounds = fontMetrics.getStringBounds(tickText, g);
				final Point2D.Double labelPoint = new Point2D.Double(coords[0], coords[1]);
				this.plotTransform.transform(labelPoint, labelPoint);
				g.drawString(tickText, (float) (labelPoint.x - stringBounds.getCenterX()),
						(float) (this.getHeight() - this.insets.bottom - stringBounds.getMinY()));
				pathIterator2.next();
				pathIterator2.next();
			}
		}

		/**
		 * @param g
		 * @param pathIteratorY
		 * @param plotTransform1
		 */
		private void drawYTickLabels(final Graphics2D g, final PathIterator pathIteratorY, final int sigDig) {
			while (!pathIteratorY.isDone()) {
				final double[] coords = new double[6];
				pathIteratorY.currentSegment(coords);
				final String format = "%." + String.valueOf(sigDig) + "g";
				final String tickText = String.format(format, coords[1]).replaceFirst("(\\.\\d*)0+($|e)", "$1$2") + " ";
				final FontMetrics fontMetrics = g.getFontMetrics();
				final Rectangle2D stringBounds = fontMetrics.getStringBounds(tickText, g);
				final Point2D.Double labelPoint = new Point2D.Double(coords[0], coords[1]);
				this.plotTransform.transform(labelPoint, labelPoint);
				g.drawString(tickText, (float) (this.insets.left - stringBounds.getMaxX()),
						(float) (labelPoint.y - stringBounds.getCenterY()));
				pathIteratorY.next();
				pathIteratorY.next();
			}
		}

		/**
		 * @return the plotColor
		 */
		public Color getPlotColor() {
			return this.plotColor;
		}

		/**
		 * @return the plotStroke
		 */
		public BasicStroke getPlotStroke() {
			return this.plotStroke;
		}

		private AffineTransform getPlotTransform(final double s, final AffineTransform pan) {
			final AffineTransform plotTransform = new AffineTransform(this.transform);
			if (Plotter6165.this.pathToUnitBoxTransform == null) {
				System.out.println("pathToUnitBoxTransform is null");
			} else {
				plotTransform.concatenate(Plotter6165.this.pathToUnitBoxTransform);
			}
			plotTransform.scale(s, s);
			if (pan != null) {
				plotTransform.concatenate(this.pan);
			}
			return plotTransform;
		}

		/**
		 * @return the drawMarkers
		 */
		public boolean isDrawMarkers() {
			return this.drawMarkers;
		}

		@Override
		public void mouseClicked(final MouseEvent mouseEvent) {
		}

		@Override
		public void mouseDragged(final MouseEvent mouseEvent) {

			final int x = mouseEvent.getX();
			final int y = mouseEvent.getY();
			final int oldX = this.mouseDownPoint.x;
			final int oldY = this.mouseDownPoint.y;
			final double dx = (x - oldX) / this.plotTransform.getScaleX();
			final double dy = (y - oldY) / this.plotTransform.getScaleY();
			if (this.mouseDownButton == 1) {
				this.pan.setToTranslation(this.oldPan.getTranslateX() + dx, this.oldPan.getTranslateY() + dy);
				this.repaint();
			}
			if (this.boxSelected) {
				switch (this.dragSide) {
				case ALL:
					this.tx = x - oldX;
					this.ty = y - oldY;
					this.pan.setToTranslation(this.tx + this.oldTx, this.ty + this.oldTy);
					// this.insets.left = (this.oldInsets.left + x) - oldX;
					// this.insets.top = (this.oldInsets.top + y) - oldY;
					// this.insets.right = (this.oldInsets.right - x) + oldX;
					// this.insets.bottom = (this.oldInsets.bottom - y) + oldY;
					break;
				case LEFT:
					this.insets.left = (this.oldInsets.left + x) - oldX;
					this.repaint();
					break;
				case TOP:
					this.insets.top = (this.oldInsets.top + y) - oldY;
					this.repaint();
					break;
				case RIGHT:
					this.insets.right = (this.oldInsets.right - x) + oldX;
					this.repaint();
					break;
				case BOT:
					this.insets.bottom = (this.oldInsets.bottom - y) + oldY;
					this.repaint();
					break;
				case NONE:
					break;
				}
			}
		}

		@Override
		public void mouseEntered(final MouseEvent mouseEvent) {
		}

		@Override
		public void mouseExited(final MouseEvent mouseEvent) {
		}

		@Override
		public void mouseMoved(final MouseEvent mouseEvent) {

			final Shape oldSelectedMarker = this.selectedMarker;
			this.selectedMarker = null;
			if (this.markers != null) {
				int index = 0;
				this.setToolTipText(null);
				for (final Shape marker : this.markers) {
					if (marker.contains(this.mousePoint)) {
						this.selectedMarker = marker;
						this.setToolTipText(String.format("%,d", index));
						break;
					}
					++index;
				}
			}
			if (this.selectedMarker != oldSelectedMarker) {
				this.repaint();
			}
		}

		@Override
		public void mousePressed(final MouseEvent mouseEvent) {
			this.mouseDownPoint = mouseEvent.getPoint();
			this.mouseDownButton = mouseEvent.getButton();
			this.oldPan = new AffineTransform(this.pan);
			this.oldInsets.left = this.insets.left;
			this.oldInsets.right = this.insets.right;
			this.oldInsets.top = this.insets.top;
			this.oldInsets.bottom = this.insets.bottom;
			this.oldTx = this.pan.getTranslateX();
			this.oldTy = this.pan.getTranslateY();
		}

		@Override
		public void mouseReleased(final MouseEvent arg0) {
			this.zoom(0, arg0.getPoint());
		}

		@Override
		public void mouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
			final int modifiersEx = mouseWheelEvent.getModifiersEx();
			if ((modifiersEx & InputEvent.CTRL_DOWN_MASK) == 0) {
				final Point point = mouseWheelEvent.getPoint();
				this.zoom(mouseWheelEvent.getWheelRotation(), point);
			}
		}

		@Override
		public void paint(final Graphics g0) {
			this.transientShape = null;
			super.paint(g0);
			final Graphics2D g = (Graphics2D) g0;
			this.insets = new Insets(Plotter6165.this.top, Plotter6165.this.left, Plotter6165.this.bottom,
					Plotter6165.this.right);
			this.painter(g, this.getWidth(), this.getHeight());
		}

		/**
		 * Assumes that the shape to be painted, {@link #path}, is scaled to fit in a
		 * unit box.
		 *
		 * @param g the Graphics context on which to paint.
		 * @param w the overall width of the plot, including margins and legends.
		 * @param h the overall height of the plot, including margins and legends.
		 */
		private void painter(final Graphics2D g, final int width, final int height) {

			for (int i = 0; i < this.getComponentCount(); ++i) {
				this.remove(i);
			}

			/*
			 * Width and height of plot window in pixels.
			 */
			final int w = width - this.insets.left - this.insets.right;
			final int h = height - this.insets.top - this.insets.bottom;

			/*
			 * Scaling factors to transform a unit square to the plot window.
			 */
			final double sx = w < 0 ? 0 : w;
			final double sy = h < 0 ? 0 : h;

			super.paint(g);
			this.jTextArea.setBounds(this.insets.left, (height - this.insets.bottom) + 3, w, this.insets.bottom - 3);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			/*
			 * Create a transform which maps a unit square to the plot window
			 */
			this.scaleInstance = AffineTransform.getScaleInstance(sx, -sy);
			this.translateInstance = AffineTransform.getTranslateInstance(this.insets.left, h + this.insets.top);
			this.transform = this.translateInstance;
			this.transform.concatenate(this.scaleInstance);
			/*
			 * "transform" now maps the unit square to the available plotting area, leaving
			 * the margins specified in "insets".
			 */

			this.boundingBox = this.transform.createTransformedShape(new Rectangle2D.Double(0, 0, 1, 1));

			g.setStroke(this.boxSelected ? this.selectedStroke : this.boxStroke);
			g.setColor(this.boxSelected ? this.selectedColor : this.majorGridColor);
			g.draw(this.boundingBox);

			/*
			 * Create a transform which maps the Shape object to the plot window
			 */
			if (this.path != null) {

				this.plotTransform = this.getPlotTransform(this.s, this.pan);
				this.plotTransform0 = this.getPlotTransform(1, null);

				final Shape transformedXGrid = this.plotTransform.createTransformedShape(Plotter6165.this.xGrid);
				final Shape transformedXGrid2 = this.plotTransform.createTransformedShape(Plotter6165.this.xGrid2);
				final Shape transformedXGrid3 = this.plotTransform.createTransformedShape(Plotter6165.this.xGrid3);
				final Shape transformedYGrid = this.plotTransform.createTransformedShape(Plotter6165.this.yGrid);
				final Shape transformedYGrid2 = this.plotTransform.createTransformedShape(Plotter6165.this.yGrid2);
				final Shape transformedYGrid3 = this.plotTransform.createTransformedShape(Plotter6165.this.yGrid3);

				final Color oldColor = g.getColor();
				g.setColor(Color.BLACK);

				final Rectangle2D xBounds = Plotter6165.this.xGrid.getBounds2D();
				final double minX = xBounds.getMinX();
				final double maxX = xBounds.getMaxX();
				final int sigDigX = (int) (2.4
						+ Math.log10((Math.max(Math.abs(minX), Math.abs(maxX)) / (maxX - minX))));
				this.drawXTickLabels(g, Plotter6165.this.xGrid2.getPathIterator(null), sigDigX);
				if (Plotter6165.this.xTicks2.length < 2) {
					this.drawXTickLabels(g, Plotter6165.this.xGrid3.getPathIterator(null), sigDigX);
				}
				if ((Plotter6165.this.xTicks2.length + Plotter6165.this.xTicks3.length) < 2) {
					this.drawXTickLabels(g, Plotter6165.this.xGrid.getPathIterator(null), sigDigX);
				}

				final Rectangle2D yBounds = Plotter6165.this.yGrid.getBounds2D();
				final double minY = yBounds.getMinY();
				final double maxY = yBounds.getMaxY();
				final int sigDigY = (int) (2.4
						+ Math.log10((Math.max(Math.abs(minY), Math.abs(maxY)) / (maxY - minY))));
				this.drawYTickLabels(g, Plotter6165.this.yGrid2.getPathIterator(null), sigDigY);
				if (Plotter6165.this.yTicks2.length < 2) {
					this.drawYTickLabels(g, Plotter6165.this.yGrid3.getPathIterator(null), sigDigY);
				}
				if ((Plotter6165.this.yTicks2.length + Plotter6165.this.yTicks3.length) < 2) {
					this.drawYTickLabels(g, Plotter6165.this.yGrid.getPathIterator(null), sigDigY);
				}

				g.setColor(oldColor);
				g.setClip(this.boundingBox);

				g.draw(transformedXGrid);
				g.draw(transformedYGrid);

				g.setStroke(this.stroke3);
				g.draw(transformedXGrid3);
				g.draw(transformedYGrid3);

				g.setColor(g.getColor().darker());
				g.setStroke(this.stroke2);
				g.draw(transformedXGrid2);
				g.draw(transformedYGrid2);

				final Shape transformedShape = this.plotTransform.createTransformedShape(this.path);
				g.setStroke(this.plotStroke);
				g.setColor(this.plotColor);
				g.draw(transformedShape);

				if (this.drawMarkers) {
					final PathIterator pathIterator = transformedShape.getPathIterator(null);
					final double[] coords = new double[2];
					final double r0 = 3;
					int i = 0;
					final int n = this.markerColors.length;
					this.markers.clear();
					while (!pathIterator.isDone()) {
						pathIterator.currentSegment(coords);
						final int j = i++ % n;
						g.setColor(this.markerColors[j]);
						final double r = r0 - (0.5f * j);
						final Shape marker = new Ellipse2D.Double(coords[0] - r, coords[1] - r, 2 * r, 2 * r);
						this.markers.add(marker);
						g.fill(marker);
						pathIterator.next();
					}
					if (this.selectedMarker != null) {
						g.setColor(Color.RED.darker());
						g.fill(this.selectedMarker);
						g.draw(this.selectedMarker);
					}
				}
			}

			g.setClip(this.boundingBox);
			for (int i = 0; i < this.paths.size(); ++i) {
				final Shape transformedShape = this.plotTransform.createTransformedShape(this.paths.get(i));
				g.setStroke(this.strokes.get(i));
				g.setColor(this.colors.get(i));
				g.draw(transformedShape);
			}

			g.setClip(null);
			if (Plotter6165.this.textItems != null) {
				final Iterator<TextItem> iterator = Plotter6165.this.textItems.iterator();
				while (iterator.hasNext()) {
					iterator.next().plotText(g, this.plotTransform);
				}
			}
			if ((Plotter6165.this.plotTitle != null) && !Plotter6165.this.plotTitle.isEmpty()) {
				this.plotTheTitle(g, w);
			}

			for (final LegendItem li : Plotter6165.this.legendItems) {
				final JTextArea area = new JTextArea(li.text);
				area.setEditable(false);
				if (li.font != null) {
					area.setFont(li.font);
				}
				final FontMetrics fontMetrics = g.getFontMetrics(area.getFont());

				final int atop = fontMetrics.getHeight() / 2;
				final int aleft = fontMetrics.charWidth(' ');
				final int abottom = fontMetrics.getHeight() / 3;
				final int aright = aleft;
				area.setBorder(BorderFactory.createEmptyBorder(atop, aleft, abottom, aright));
				final String[] split = li.text.split("(?m)^");// enable multiline mode, match beginning of a line.
				int maxWidth = 0;
				int h1 = atop + abottom;
				for (final String s : split) {
					final int stringWidth = fontMetrics.stringWidth(s);
					h1 += fontMetrics.getHeight();
					if (stringWidth > maxWidth) {
						maxWidth = stringWidth;
					}
				}
				for (int i = split.length - 1; i >= 0; --i) {
					if (split[i].trim().isEmpty()) {
						h1 -= fontMetrics.getHeight();
					} else {
						break;
					}
				}
				maxWidth += aleft + aright;
				this.add(area);
				// area.setOpaque(false);
				area.setBackground(Color.WHITE);
				area.setWrapStyleWord(true);
				area.setLineWrap(true);
				area.setInheritsPopupMenu(true);
				area.setOpaque(true);
				final int textAreaLeftx = (int) (Double.isNaN(li.x) ? ((this.insets.left + w) - maxWidth)
						: this.plotTransform0.transform(new Point2D.Double(li.x, 0), null).getX());
				final int textAreaTopy = (int) (Double.isNaN(li.y) ? this.insets.top
						: this.plotTransform0.transform(new Point2D.Double(0, li.y), null).getY());
				final int textAreaWidth = (int) (Double.isNaN(li.w) ? maxWidth
						: (li.w * this.plotTransform0.getScaleX()));
				final int textAreaHeight = (int) (Double.isNaN(li.h) ? h1 : (-li.h * this.plotTransform0.getScaleY()));
				area.setBounds(textAreaLeftx, textAreaTopy, textAreaWidth, textAreaHeight);
			}
			super.paintChildren(g);
		}

		/**
		 * @param g The graphics context
		 * @param w The net width of the plot area, excluding margins.
		 */
		private void plotTheTitle(final Graphics2D g, final int w) {
			double x = 0;
			boolean fitDone = false;
			Rectangle2D stringBounds = null;
			Font font = Plotter6165.this.titleFont;
			int count = 0;
			do {
				++count;
				if (!fitDone) {
					/*
					 * If title has not been fitted to the allocated space, reduce the font size by
					 * 0.9 and try again.
					 */
					final float oldSize = font.getSize2D();
					font = (font.deriveFont(oldSize * 0.9f));
				}
				final FontMetrics fontMetrics = g.getFontMetrics(font);
				stringBounds = fontMetrics.getStringBounds(Plotter6165.this.plotTitle, g);
				x = ((w / 2) - stringBounds.getCenterX()) + Plotter6165.this.left;
				fitDone = (count > 100) || ((x > 0)
						&& ((x + stringBounds.getWidth()) < (Plotter6165.this.left + w + Plotter6165.this.right)));
			} while (!fitDone);
			final double y = (Plotter6165.this.top / 2) - stringBounds.getCenterY();
			final Font oldFont = g.getFont();
			final Color oldColor = g.getColor();
			g.setFont(font);
			g.setColor(Plotter6165.this.titleColor);
			g.drawString(Plotter6165.this.plotTitle, (float) x, (float) y);
			g.setColor(oldColor);
			g.setFont(oldFont);
		}

		/**
		 * Reset the zoom and pan to original values.
		 *
		 */
		private void resetZoom() {

			try {
				/*
				 * Reset the scale factor s and the pan transform:
				 */
				this.s = 1;
				this.pan.setToIdentity();

				/*
				 * The new transform.
				 */
				final AffineTransform plotTransform3 = this.getPlotTransform(this.s, this.pan);

				/*
				 * Calculate new bounds for the data in the window, after zooming
				 */
				final Point2D upperLeftPoint = new Point2D.Double(this.insets.left, this.insets.top);
				final Point2D dataAtUpperLeft = plotTransform3.inverseTransform(upperLeftPoint, null);
				final Point2D bottomRightPoint = new Point2D.Double(this.getWidth() - this.insets.right,
						this.getHeight() - this.insets.bottom);
				final Point2D dataAtBottomRight = plotTransform3.inverseTransform(bottomRightPoint, null);
				final Rectangle2D.Double dataBounds = new Rectangle2D.Double(dataAtUpperLeft.getX(),
						dataAtUpperLeft.getY(), dataAtBottomRight.getX() - dataAtUpperLeft.getX(),
						dataAtBottomRight.getY() - dataAtUpperLeft.getY());

				/*
				 * Create a grid to cover the zoomed data in the window
				 */
				Plotter6165.this.createGrid(dataBounds, true);

			} catch (final NoninvertibleTransformException e) {
				e.printStackTrace();
			}

			this.repaint();
		}

		/**
		 * @param drawMarkers the drawMarkers to set
		 */
		public void setDrawMarkers(final boolean drawMarkers) {
			this.drawMarkers = drawMarkers;
		}

		public void setPlotColor(final Color color) {
			this.plotColor = color;
		}

		/**
		 * @param plotStroke the plotStroke to set
		 */
		public void setPlotStroke(final BasicStroke plotStroke) {
			this.plotStroke = plotStroke;
		}

		/**
		 * Zooms in or out in response to mouse wheel rotation.
		 *
		 * @param wheelRotation
		 * @param point
		 */
		private void zoom(final int wheelRotation, final Point point) {

			try {
				/*
				 * Get the current plot transform:
				 */
				final AffineTransform plotTransform1 = this.getPlotTransform(this.s, this.pan);
				/*
				 * "plotTransform1" now maps the given shape into the plot window.
				 */

				/*
				 * The data coordinates currently under the mouse pointer
				 */
				final Point2D dataAtMouse1 = point == null ? null : plotTransform1.inverseTransform(point, null);

				/*
				 * Update the scale factor s:
				 */
				this.s *= Math.pow(1.1, -wheelRotation);

				/*
				 * Apply new zoom and old pan:
				 */
				final AffineTransform plotTransform2 = this.getPlotTransform(this.s, this.pan);

				/*
				 * If new scaling but old pan were applied, the data under the mouse after zoom
				 * would be this:
				 */
				final Point2D newDataAtMouse = point == null ? null : plotTransform2.inverseTransform(point, null);

				final boolean noNewDataAtMouse = newDataAtMouse == null;
				/*
				 * The additional amount to translate to bring the same data coordinates under
				 * the mouse as before the zoom:
				 */
				final boolean noDataAtMouse1 = dataAtMouse1 == null;
				final double dx = (noNewDataAtMouse || noDataAtMouse1) ? 0
						: newDataAtMouse.getX() - dataAtMouse1.getX();
				final double dy = (noNewDataAtMouse || noDataAtMouse1) ? 0
						: newDataAtMouse.getY() - dataAtMouse1.getY();
				this.pan.translate(dx, dy);

				/*
				 * The new transform.
				 */

				final AffineTransform plotTransform3 = this.getPlotTransform(this.s, this.pan);

				/*
				 * Calculate new bounds for the data in the window, after zooming
				 */
				final Point2D upperLeftPoint = new Point2D.Double(this.insets.left, this.insets.top);
				final Point2D dataAtUpperLeft = plotTransform3.inverseTransform(upperLeftPoint, null);
				final Point2D bottomRightPoint = new Point2D.Double(this.getWidth() - this.insets.right,
						this.getHeight() - this.insets.bottom);
				final Point2D dataAtBottomRight = plotTransform3.inverseTransform(bottomRightPoint, null);
				final Rectangle2D.Double dataBounds = new Rectangle2D.Double(dataAtUpperLeft.getX(),
						dataAtUpperLeft.getY(), dataAtBottomRight.getX() - dataAtUpperLeft.getX(),
						dataAtBottomRight.getY() - dataAtUpperLeft.getY());

				/*
				 * Create a grid to cover the zoomed data in the window
				 */
				Plotter6165.this.createGrid(dataBounds, true);

			} catch (final NoninvertibleTransformException e) {
				e.printStackTrace();
			}

			this.repaint();
		}

	}

	/**
	 * @author Raimo Bakis
	 *
	 */
	public class TextItem {

		private final Color color;
		private final Font font;
		private final String text;
		private final double x;
		private final double y;

		public TextItem(final String string, final double x, final double y, final Font font, final Color color) {
			this.text = string;
			this.x = x;
			this.y = y;
			this.font = font;
			this.color = color;
		}

		public void plotText(final Graphics2D g, final AffineTransform t) {
			final Color oldColor = g.getColor();
			g.setColor(this.color);
			final Font oldFont = g.getFont();
			g.setFont(this.font);
			final Point2D plotPoint = t.transform(new Point2D.Double(this.x, this.y), null);
			final FontMetrics fontMetrics = g.getFontMetrics();
			final Rectangle2D stringBounds = fontMetrics.getStringBounds(this.text, g);
			g.drawString(this.text, (float) (plotPoint.getX() - stringBounds.getCenterX()),
					(float) (plotPoint.getY() - stringBounds.getCenterY()));
			g.setFont(oldFont);
			g.setColor(oldColor);
		}

	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param x0
	 * @param x1
	 * @return
	 */
	public static int[] getRoundRange(final double x0, final double x1) {
		final boolean reverse = x1 < x0;

		/*
		 * The lower end of the range
		 */
		final double xa = reverse ? x1 : x0;

		/*
		 * The upper end of the range
		 */
		final double xb = reverse ? x0 : x1;

		final double interval = xb - xa;

		/*
		 * The smallest power of 10 that fits in the interval at least 10 times
		 */
		final int i = (int) Math.ceil(Math.log10(interval)) - 2;

		/*
		 * Interval between major tick marks
		 */
		final double unit2 = Math.pow(10, i);

		/*
		 * The value at the first major tick mark divided by the major tick interval.
		 */
		final int floor2 = (int) Math.floor(xa / unit2);

		/*
		 * The value at the last major tick mark divided by the major tick interval.
		 */
		final int ceil2 = (int) Math.ceil(xb / unit2);

		/*
		 * Return the values of the first and last major tick marks, divided by the
		 * major tick interval, and the base-10 logarithm of the size of the the major
		 * tick interval.
		 */
		return new int[] { floor2, ceil2, i };
	}

	/**
	 * @return The current timestamp in format yyymmddHHMMSSLLL
	 */
	public static String getTimeStamp() {
		return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", System.currentTimeMillis());
	}

	/**
	 * inset of plot window from the bottom edge of the pane
	 */
	private int bottom = 40;

	private final Insets insets = new Insets(this.top, this.left, this.bottom, this.getRight());

	/**
	 * inset of plot window from the left edge of the pane
	 */
	private int left = 60;

	private final List<LegendItem> legendItems = new ArrayList<>();

	private Rectangle2D pathBounds;

	private AffineTransform pathToUnitBoxTransform;

	private final PlotterPane plotterPane = new PlotterPane();

	/**
	 * The default size of the plotter pane.
	 */
	private Dimension plotterPaneDefaultDimension = new Dimension(400, 300);

	private final JPopupMenu plotterPopupMenu = new JPopupMenu();

	public String plotTitle = null;

	/**
	 * inset of plot window from the right edge of the pane
	 */
	private int right = 20;

	private final List<TextItem> textItems = new ArrayList<>();

	private final Color titleColor = Color.BLACK;

	private final Font titleFont = new Font(Font.SERIF, Font.BOLD, 24);

	/**
	 * inset of plot window from the top edge of the pane
	 */
	private int top = 40;

	private final Path2D xGrid = new Path2D.Double();

	private final Path2D xGrid2 = new Path2D.Double();

	private final Path2D xGrid3 = new Path2D.Double();

	private double[] xTicks;

	private double[] xTicks2;

	private double[] xTicks3;

	private final Path2D yGrid = new Path2D.Double();

	private final Path2D yGrid2 = new Path2D.Double();

	private final Path2D yGrid3 = new Path2D.Double();

	private double[] yTicks;

	private double[] yTicks2;

	private double[] yTicks3;

	/**
	 * Default constructor
	 */
	public Plotter6165() {
		super();
		this.init();
	}

	/**
	 * @param arg0
	 */
	public Plotter6165(final String arg0) {
		super(arg0);
		this.init();
	}

	public void addLegend(final String text, final double x, final double y, final double w, final double h) {
		this.legendItems.add(new LegendItem(text, this.plotterPane.getFont(), x, y, w, h));
	}

	private LegendItem addLegend(final String text, final double x, final double y, final double w, final double h,
			final Font font) {
		final LegendItem legendItem = new LegendItem(text, font, x, y, w, h);
		this.legendItems.add(legendItem);
		return legendItem;
	}

	public void addPlotPath(final Color color, final Shape path, final Stroke stroke) {
		this.addPlotPath(stroke, path, color);
	}

	public void addPlotPath(final Color color, final Stroke stroke, final Shape path) {
		this.addPlotPath(stroke, path, color);
	}

	public void addPlotPath(final Shape path, final Color color, final Stroke stroke) {
		this.addPlotPath(stroke, path, color);
	}

	public void addPlotPath(final Shape path, final Stroke stroke, final Color color) {
		this.addPlotPath(stroke, path, color);
	}

	public void addPlotPath(final Stroke stroke, final Color color, final Shape path) {
		this.addPlotPath(stroke, path, color);
	}

	public void addPlotPath(final Stroke stroke, final Shape path, final Color color) {
		this.plotterPane.paths.add(path);
		this.plotterPane.strokes.add(stroke);
		this.plotterPane.colors.add(color);
	}

	public TextItem addText(final String string, final double x, final double y, final Font font, final Color color) {
		final TextItem textItem = new TextItem(string, x, y, font, color);
		this.textItems.add(textItem);
		return textItem;
	}

	/**
	 * Concatenate the <code>textLines</code> to one string with line separators.
	 * Then invoke {@link #addLegend(String, double, double, double, double, Font)}.
	 * The {@link #plotterPane} painter method then creates a text area for each
	 * legend item, and sets its text to the text lines.
	 *
	 * @param textLines The lines of text to be displayed
	 * @param font      The font for the lines of text
	 * @param x         The
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public LegendItem addTextBox(final List<String> textLines, final Font font, final double x, final double y,
			final double w, final double h) {
		final StringBuilder textB = new StringBuilder();
		final String lineSeparator = System.lineSeparator();
		final Iterator<String> textLinesIterator = textLines.iterator();
		if (textLinesIterator.hasNext()) {
			textB.append(textLinesIterator.next());
		}
		while (textLinesIterator.hasNext()) {
			textB.append(lineSeparator);
			textB.append(textLinesIterator.next());
		}
		return this.addLegend(textB.toString(), x, y, w, h, font);
	}

	public void addTitle(final String string) {
		this.plotTitle = string;
	}

	protected void copyImageToFile() throws IOException {
		final int imageWidth = this.plotterPane.getWidth();
		final int imageHeight = this.plotterPane.getHeight();
		final BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		this.plotterPane.painter((Graphics2D) (bi.getGraphics()), bi.getWidth(), bi.getHeight());
		final String outFileName = Plotter6165.getTimeStamp() + ".png";
		final File outDir = new File("data", "out");
		outDir.mkdirs();
		final File outFile = new File(outDir, outFileName);
		ImageIO.write(bi, "png", outFile);
	}

	private void createGrid(final Rectangle2D gridBounds, final boolean zooming) {
		final int[] xRoundRange = Plotter6165.getRoundRange(gridBounds.getX(),
				gridBounds.getX() + gridBounds.getWidth());
		final int[] yRoundRange = Plotter6165.getRoundRange(gridBounds.getY(),
				gridBounds.getY() + gridBounds.getHeight());
		final double xUnit = Math.pow(10., xRoundRange[2]);
		final double yUnit = Math.pow(10., yRoundRange[2]);

		final double x = xRoundRange[0] * xUnit;
		final double y = yRoundRange[0] * yUnit;
		final double w = (xRoundRange[1] * xUnit) - x;
		final double h = (yRoundRange[1] * yUnit) - y;

		final Rectangle2D roundBounds = new Rectangle2D.Double(x, y, w, h);

		if (!zooming) {
			this.pathToUnitBoxTransform = AffineTransform.getScaleInstance(1. / roundBounds.getWidth(),
					1. / roundBounds.getHeight());
			this.pathToUnitBoxTransform
					.concatenate(AffineTransform.getTranslateInstance(-roundBounds.getX(), -roundBounds.getY()));
		}

		final int xTickcount = (int) ((1.5 + xRoundRange[1]) - xRoundRange[0]);
		this.xTicks = new double[xTickcount];
		int xTick2count = 0;
		int xTick3count = 0;
		for (int i = 0; i < this.xTicks.length; ++i) {
			final int j = xRoundRange[0] + i;
			if ((j % 5) == 0) {
				if ((j % 10) == 0) {
					++xTick2count;
				} else {
					++xTick3count;
				}
			}
			this.xTicks[i] = j * xUnit;
		}

		this.xTicks2 = new double[xTick2count];
		this.xTicks3 = new double[xTick3count];
		int k2 = 0;
		int k3 = 0;
		for (int i = 0; i < this.xTicks.length; ++i) {
			final int j = xRoundRange[0] + i;
			if ((j % 5) == 0) {
				if ((j % 10) == 0) {
					this.xTicks2[k2++] = j * xUnit;
				} else {
					this.xTicks3[k3++] = j * xUnit;
				}
			}
		}

		final int yTickcount = (int) ((1.5 + yRoundRange[1]) - yRoundRange[0]);
		this.yTicks = new double[yTickcount];
		int yTick2count = 0;
		int yTick3count = 0;
		for (int i = 0; i < this.yTicks.length; ++i) {
			final int j = yRoundRange[0] + i;
			if ((j % 5) == 0) {
				if ((j % 10) == 0) {
					++yTick2count;
				} else {
					++yTick3count;
				}
			}
			this.yTicks[i] = j * yUnit;
		}

		this.yTicks2 = new double[yTick2count];
		this.yTicks3 = new double[yTick3count];
		k2 = 0;
		k3 = 0;
		for (int i = 0; i < this.yTicks.length; ++i) {
			final int j = yRoundRange[0] + i;
			if ((j % 5) == 0) {
				if ((j % 10) == 0) {
					this.yTicks2[k2++] = j * yUnit;
				} else {
					this.yTicks3[k3++] = j * yUnit;
				}
			}
		}

		this.xGrid.reset();
		this.xGrid2.reset();
		this.xGrid3.reset();
		final double y0 = this.yTicks[0];
		final double y1 = this.yTicks[this.yTicks.length - 1];
		for (final double xTick : this.xTicks) {
			this.xGrid.moveTo(xTick, y0);
			this.xGrid.lineTo(xTick, y1);
		}
		for (final double xTick : this.xTicks2) {
			this.xGrid2.moveTo(xTick, y0);
			this.xGrid2.lineTo(xTick, y1);
		}
		for (final double xTick : this.xTicks3) {
			this.xGrid3.moveTo(xTick, y0);
			this.xGrid3.lineTo(xTick, y1);
		}

		this.yGrid.reset();
		this.yGrid2.reset();
		this.yGrid3.reset();
		final double x0 = this.xTicks[0];
		final double x1 = this.xTicks[this.xTicks.length - 1];
		for (final double yTick : this.yTicks) {
			this.yGrid.moveTo(x0, yTick);
			this.yGrid.lineTo(x1, yTick);
		}
		for (final double yTick : this.yTicks2) {
			this.yGrid2.moveTo(x0, yTick);
			this.yGrid2.lineTo(x1, yTick);
		}
		for (final double yTick : this.yTicks3) {
			this.yGrid3.moveTo(x0, yTick);
			this.yGrid3.lineTo(x1, yTick);
		}
	}

	/**
	 * @return the bottom
	 */
	public int getBottom() {
		return this.bottom;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return this.left;
	}

	/**
	 * @return the plotterPane
	 */
	public PlotterPane getPlotterPane() {
		return this.plotterPane;
	}

	/**
	 * @return the plotterPaneDefaultDimension
	 */
	public Dimension getPlotterPaneDefaultDimension() {
		return this.plotterPaneDefaultDimension;
	}

	/**
	 * @return the plotterPopupMenu
	 */
	public JPopupMenu getPlotterPopupMenu() {
		return this.plotterPopupMenu;
	}

	/**
	 * @return the right
	 */
	public int getRight() {
		return this.right;
	}

	/**
	 * @return the top
	 */
	public int getTop() {
		return this.top;
	}

	/**
	 * @param desktop
	 */
	private void init() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setContentPane(this.plotterPane);
		this.plotterPane.setPreferredSize(this.plotterPaneDefaultDimension);
		this.plotterPane.setComponentPopupMenu(this.plotterPopupMenu);

		final ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.setInitialDelay(1);
		toolTipManager.setDismissDelay(10000);

		final JMenuItem menuItemSave = new JMenuItem("Save image to file");
		menuItemSave.addActionListener(arg0 -> {
			try {
				Plotter6165.this.copyImageToFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		});
		this.plotterPopupMenu.add(menuItemSave);

		final JMenuItem menuItemReset = new JMenuItem("Reset zoom and pan");
		menuItemReset.addActionListener(arg0 -> {
			try {
				this.plotterPane.resetZoom();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		this.plotterPopupMenu.add(menuItemReset);

		this.plotterPopupMenu.addSeparator();
		final JMenuItem menuItemQuit = new JMenuItem("QUIT");
		menuItemQuit.addActionListener(arg0 -> Plotter6165.this.shutdown());
		this.plotterPopupMenu.add(menuItemQuit);
	}

	public void removeText(final TextItem textItem) {
		this.textItems.remove(textItem);
	}

	public void removeTextBox(final LegendItem textBox) {
		if (textBox != null) {
			this.legendItems.remove(textBox);
		}
	}

	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(final int bottom) {
		this.bottom = bottom;
	}

	public void setDrawMarkers(final boolean b) {
		this.plotterPane.setDrawMarkers(b);
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(final int left) {
		this.left = left;
	}

	public void setMainPlotPath(final Shape path) {
		this.plotterPane.path = path;
		this.pathBounds = this.plotterPane.path.getBounds2D();

		this.createGrid(this.pathBounds, false);

	}

	/**
	 * @param plotterPaneDimension
	 *
	 *                             the plotterPaneDimension to set
	 */
	public void setPlotterPaneDimension(final Dimension plotterPaneDimension) {
		this.plotterPaneDefaultDimension = plotterPaneDimension;
	}

	public void setPlotTitle(final String string) {
		this.plotTitle = string;
	}

	public void setPlotWindowDimension(final Dimension plotWindowDimension) {
		final PlotterPane plotterPane = this.getPlotterPane();
		final Insets insets = plotterPane.getInsets();
		final Dimension preferredDimension = new Dimension(plotWindowDimension.width + insets.left + insets.right,
				plotWindowDimension.height + insets.bottom + insets.top);
		plotterPane.setPreferredSize(preferredDimension);
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(final int right) {
		this.right = right;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(final int top) {
		this.top = top;
	}

	protected void shutdown() {
		this.dispose();
	}

}
