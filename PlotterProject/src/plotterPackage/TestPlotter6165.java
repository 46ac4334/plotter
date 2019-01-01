/**
 *
 */
package plotterPackage;

import java.awt.Dimension;
import java.awt.geom.Path2D;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import methodsPackage.Methods;

/**
 * @author 46ac4334
 *
 */
class TestPlotter6165 {

	private static Plotter6165 plotter;
	private static long t00;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TestPlotter6165.t00 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(TestPlotter6165.t00));

		TestPlotter6165.plotter = new Plotter6165();
		TestPlotter6165.plotter.pack();
		TestPlotter6165.plotter.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Methods.endMessage(TestPlotter6165.t00));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(TestPlotter6165.t00));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Methods.endMessage(t0));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));
		TestPlotter6165.plotter.pack();
		TestPlotter6165.plotter.repaint();
		System.out.println(Methods.endMessage(t0));
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#setMainPlotPath(java.awt.Shape)}.
	 */
	@Test
	final void testSetMainPlotPath() {
//		Assertions.fail("Not yet implemented"); // TODO
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		final Path2D.Double plotpath = new Path2D.Double();
		double x = -1;
		double y = x * x;
		plotpath.moveTo(x, y);
		final double dx = 0.01;
		for (x = x + dx; x < 1.00001; x += dx) {
			y = x * x;
			plotpath.lineTo(x, y);
		}
		TestPlotter6165.plotter.setMainPlotPath(plotpath);

		System.out.println(Methods.endMessage(t0));
	}
																																																																																						
	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addLegend(java.lang.String, double, double, double, double)}.
	 */
	@Test
	final void testAddLegend() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addPlotPath(java.awt.Color, java.awt.Shape, java.awt.Stroke)}.
	 */
	@Test
	final void testAddPlotPathColorShapeStroke() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addPlotPath(java.awt.Color, java.awt.Stroke, java.awt.Shape)}.
	 */
	@Test
	final void testAddPlotPathColorStrokeShape() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addPlotPath(java.awt.Shape, java.awt.Color, java.awt.Stroke)}.
	 */
	@Test
	final void testAddPlotPathShapeColorStroke() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addPlotPath(java.awt.Shape, java.awt.Stroke, java.awt.Color)}.
	 */
	@Test
	final void testAddPlotPathShapeStrokeColor() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addPlotPath(java.awt.Stroke, java.awt.Color, java.awt.Shape)}.
	 */
	@Test
	final void testAddPlotPathStrokeColorShape() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addPlotPath(java.awt.Stroke, java.awt.Shape, java.awt.Color)}.
	 */
	@Test
	final void testAddPlotPathStrokeShapeColor() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addText(java.lang.String, double, double, java.awt.Font, java.awt.Color)}.
	 */
	@Test
	final void testAddText() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addTextBox(java.util.List, java.awt.Font, double, double, double, double)}.
	 */
	@Test
	final void testAddTextBox() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#addTitle(java.lang.String)}.
	 */
	@Test
	final void testAddTitle() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getBottom()}.
	 */
	@Test
	final void testGetBottom() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getLeft()}.
	 */
	@Test
	final void testGetLeft() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getPlotterPane()}.
	 */
	@Test
	final void testGetPlotterPane() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#getPlotterPaneDefaultDimension()}.
	 */
	@Test
	final void testGetPlotterPaneDefaultDimension() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getPlotterPopupMenu()}.
	 */
	@Test
	final void testGetPlotterPopupMenu() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getRight()}.
	 */
	@Test
	final void testGetRight() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#getRoundRange(double, double)}.
	 */
	@Test
	final void testGetRoundRange() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getTimeStamp()}.
	 */
	@Test
	final void testGetTimeStamp() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		final String timestamp = Plotter6165.getTimeStamp();
		System.out.println(timestamp);
		if ((timestamp == null) || timestamp.isEmpty()) {
			Assertions.fail("Not yet implemented"); // TODO
		}
		System.out.println(Methods.endMessage(t0));
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#getTop()}.
	 */
	@Test
	final void testGetTop() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#Plotter6165()}.
	 */
	@Test
	final void testPlotter6165() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));
		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#Plotter6165(java.lang.String)}.
	 */
	@Test
	final void testPlotter6165String() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#removeText(plotterPackage.Plotter6165.TextItem)}.
	 */
	@Test
	final void testRemoveText() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#removeTextBox(plotterPackage.Plotter6165.LegendItem)}.
	 */
	@Test
	final void testRemoveTextBox() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#setBottom(int)}.
	 */
	@Test
	final void testSetBottom() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#setDrawMarkers(boolean)}.
	 */
	@Test
	final void testSetDrawMarkers() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#setLeft(int)}.
	 */
	@Test
	final void testSetLeft() {
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#setPlotterPaneDimension(java.awt.Dimension)}.
	 */
	@Test
	final void testSetPlotterPaneDimension() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));
		TestPlotter6165.plotter.setPlotterPaneDimension(new Dimension(500, 600));
		System.out.println(Methods.endMessage(t0));
//		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#setPlotTitle(java.lang.String)}.
	 */
	@Test
	final void testSetPlotTitle() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		TestPlotter6165.plotter.setPlotTitle("Plot Title");
		System.out.println(Methods.endMessage(t0));
//		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link plotterPackage.Plotter6165#setPlotWindowDimension(java.awt.Dimension)}.
	 */
	@Test
	final void testSetPlotWindowDimension() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));
		TestPlotter6165.plotter.setPlotWindowDimension(new Dimension(500, 600));
		System.out.println(Methods.endMessage(t0));
//		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#setRight(int)}.
	 */
	@Test
	final void testSetRight() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));
		TestPlotter6165.plotter.setRight(3);
		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link plotterPackage.Plotter6165#setTop(int)}.
	 */
	@Test
	final void testSetTop() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		System.out.println(Methods.endMessage(t0));
		Assertions.fail("Not yet implemented"); // TODO
	}

}
