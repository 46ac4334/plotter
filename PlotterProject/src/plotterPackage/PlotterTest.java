/**
 *
 */
package plotterPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import methodsPackage.Methods;

/**
 * @author 46ac4334
 *
 */
public class PlotterTest implements Runnable {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new Thread(new PlotterTest()).start();
	}

	private static void testAddLegend(final long t0, final List<String> failureList, final List<String> passList,
			final Plotter6165 plotter) {
		plotter.addLegend("Legend at -0.5, 0.9", -0.5, 0.9, 1.1, 0.2);
		plotter.repaint();

		final int sizeChange = JOptionPane.showConfirmDialog(null,
				"Did the legend appear with upper left corner at -0.5,0.9, width 1.1, height 0.2?");
		switch (sizeChange) {
		case JOptionPane.YES_OPTION:
			passList.add("Legend appeared.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Legend did not appear");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return;
		}
	}

	private static void testAddPlotPath(final long t0, final List<String> failureList, final List<String> passList,
			final Plotter6165 plotter) {
		final float width = 1;
		final int cap = BasicStroke.CAP_ROUND;
		final int join = BasicStroke.JOIN_ROUND;
		final float miterlimit = 1.5f;
		final float[] dash = new float[] { 10f, 5f, 2f, 5f };
		final float dash_phase = 0f;
		final Stroke stroke = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);

		final Path2D.Double plotpath = new Path2D.Double();
		final double r = 0.5;
		final double xc = 0;
		final double yc = 0.5;
		double x = r + xc;
		double y = yc;
		plotpath.moveTo(x, y);
		final double dphi = 1;
		for (double phi = dphi; phi < 360.00001; phi += dphi) {
			final double alpha = Math.toRadians(phi);
			x = (r * Math.cos(alpha)) + xc;
			y = (r * Math.sin(alpha)) + yc;
			plotpath.lineTo(x, y);
		}

		plotter.addPlotPath(Color.BLUE, plotpath, stroke);
		plotter.repaint();

		final int addCircle = JOptionPane.showConfirmDialog(null,
				"Did an ellipse appear with radius 0.5 and center at (0, 0.5)?");
		switch (addCircle) {
		case JOptionPane.YES_OPTION:
			passList.add("Ellipse appeared.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Ellipse did not appear");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return;
		}

	}

	private static void testAddText(final long t0, final List<String> failureList, final List<String> passList,
			final Plotter6165 plotter) {
		plotter.addText("text", -0.9, 0.1, new Font("consolas", Font.BOLD, 14), Color.RED);
		plotter.repaint();

		final int sizeChange = JOptionPane.showConfirmDialog(null, "Did red \"text\" appear centered at -0.9,0.1?");
		switch (sizeChange) {
		case JOptionPane.YES_OPTION:
			passList.add("Text appeared.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Text did not appear");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return;
		}
	}

	private static void testAddTextBox(final long t0, final List<String> failureList, final List<String> passList,
			final Plotter6165 plotter) {
		plotter.addTextBox(Arrays.asList("Text box", "line 2"), new Font("consolas", Font.PLAIN, 14), -0.5, 0.6, 0.8,
				0.2);
		plotter.repaint();

		final int sizeChange = JOptionPane.showConfirmDialog(null,
				"Did the text box appear with upper left corner at -0.5,0.6, width 0.8, height 0.2?");
		switch (sizeChange) {
		case JOptionPane.YES_OPTION:
			passList.add("Text box appeared.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Text box did not appear");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return;
		}
	}

	private static boolean testMainPlotPath(final long t0, final List<String> failureList, final List<String> passList,
			final Plotter6165 plotter) {
		final Path2D.Double plotpath = new Path2D.Double();
		double x = -1;
		double y = x * x;
		plotpath.moveTo(x, y);
		final double dx = 0.01;
		for (x = x + dx; x < 1.00001; x += dx) {
			y = x * x;
			plotpath.lineTo(x, y);
		}
		plotter.setMainPlotPath(plotpath);

		plotter.setMainPlotPath(plotpath);
		plotter.repaint();

		final int showsParabola = JOptionPane.showConfirmDialog(null, "Does the plotter show a parabola?");
		switch (showsParabola) {
		case JOptionPane.YES_OPTION:
			passList.add("Plotter shows a parabola.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Plotter does not show a parabola");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return false;
		}
		return true;
	}

	private static boolean testSetPlotterPaneDimension(final long t0, final List<String> failureList,
			final List<String> passList, final Plotter6165 plotter) {
		final Path2D.Double plotpath = new Path2D.Double();
		double x = -1;
		double y = x * x;
		plotpath.moveTo(x, y);
		final double dx = 0.01;
		for (x = x + dx; x < 1.00001; x += dx) {
			y = x * x;
			plotpath.lineTo(x, y);
		}
		plotter.setPlotterPaneDimension(new Dimension(500, 600));

		plotter.repaint();

		final int answer = JOptionPane.showConfirmDialog(null, "Did the plot size change?");
		switch (answer) {
		case JOptionPane.YES_OPTION:
			passList.add("Plot size changed.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Plot size did not change");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return false;
		}
		return true;
	}

	private static void testSetPlotterVisible(final long t0, final List<String> failureList,
			final List<String> passList, final Plotter6165 plotter) {
		plotter.pack();
		plotter.setVisible(true);

		final int visible = JOptionPane.showConfirmDialog(null, "Is the plotter visible?");
		switch (visible) {
		case JOptionPane.YES_OPTION:
			passList.add("Plotter is visible.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Plotter is not visible");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return;
		}
	}

	private static void testSetPlotWindowDimension(final long t0, final List<String> failureList,
			final List<String> passList, final Plotter6165 plotter) {
		plotter.setPlotWindowDimension(new Dimension(600, 500));
		plotter.repaint();

		final int sizeChange = JOptionPane.showConfirmDialog(null, "Did the plotter size change?");
		switch (sizeChange) {
		case JOptionPane.YES_OPTION:
			passList.add("Plotter size changed.");
			break;
		case JOptionPane.NO_OPTION:
			failureList.add("Plotter size did not change");
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("cancel");
			System.out.println(Methods.endMessage(t0));
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		final long t0 = System.currentTimeMillis();
		System.out.println(Methods.startMessage(t0));

		final List<String> failureList = new ArrayList<>();
		final List<String> passList = new ArrayList<>();

		final Plotter6165 plotter = new Plotter6165();

		PlotterTest.testSetPlotterVisible(t0, failureList, passList, plotter);
		PlotterTest.testMainPlotPath(t0, failureList, passList, plotter);
		PlotterTest.testSetPlotWindowDimension(t0, failureList, passList, plotter);
		PlotterTest.testSetPlotterPaneDimension(t0, failureList, passList, plotter);
		PlotterTest.testAddLegend(t0, failureList, passList, plotter);
		PlotterTest.testAddTextBox(t0, failureList, passList, plotter);
		PlotterTest.testAddPlotPath(t0, failureList, passList, plotter);
		PlotterTest.testAddText(t0, failureList, passList, plotter);

		final int failureCount = failureList.size();
		System.out.println(String.format("%,d %s:", failureCount, failureCount == 1 ? "failure" : "failures"));
		for (final String msg : failureList) {
			System.out.println("  \u2022 " + msg);
		}

		final int successCount = passList.size();
		System.out.println(String.format("%,d %s:", successCount, successCount == 1 ? "success" : "successes"));
		for (final String msg : passList) {
			System.out.println("  \u2022 " + msg);
		}

		System.out.println(Methods.endMessage(t0));
	}
}
