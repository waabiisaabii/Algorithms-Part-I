/**
 * Algorithms, Part I
 * Assignment 3.
 *
 * @author Yachen Lin
 * @date Jan 31, 2018
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * BruteCollinearPoints class.
 * @author Yachen Lin
 */
public class BruteCollinearPoints {
    /**
     * store line segments
     */
    private LineSegment[] lineSegments;

    /**
     * Find left most point.
     *
     * @param points points
     * @param p index p
     * @param q index q
     * @param r index r
     * @param s index s
     * @return min and max points
     */
    private Point[] findMinMax(Point[] points, int p, int q, int r, int s) {
        Point[] tmp = {points[p], points[q], points[r], points[s]};
        Arrays.sort(tmp, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        });
        Point[] result = {tmp[0], tmp[tmp.length - 1]};
        return result;
    }

    /**
     * Finds all line segments containing 4 points.
     *
     * @param points given points
     */
    public BruteCollinearPoints(Point[] points) {

        // time O(N^4)
        // space O(N)
        if (points == null) {
            throw new IllegalArgumentException("Illegal argument");
        }

        // check if points exist null point
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Illegal argument");
            }
        }

        // check if points exist repeated input
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Illegal argument");
                }
            }
        }

        lineSegments = new LineSegment[(int) Math.pow(points.length, 4)];
        int indexLineSegment = 0;

        // find line segments
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[p] == null || points[q] == null
                                || points[r] == null || points[s] == null) {
                            throw new IllegalArgumentException("Illegal argument");
                        }

                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                                && points[p].slopeTo(points[r]) == points[r].slopeTo(points[s])) {
                            Point[] edges = findMinMax(points, p, q, r, s);
                            LineSegment lineSegment = new LineSegment(edges[0], edges[1]);

//                            LineSegment lineSegment = new LineSegment(points[p], points[s]);
                            lineSegments[indexLineSegment] = lineSegment;
                            indexLineSegment++;
                        }

                    }
                }
            }
        }

        // cut off extra space
        LineSegment[] newLineSegments = new LineSegment[indexLineSegment];
        for (int i = 0; i < indexLineSegment; i++) {
            newLineSegments[i] = lineSegments[i];
        }
        lineSegments = newLineSegments;

    }

    /**
     * The number of line segments.
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * The line segments.
     *
     * @return the line segments
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    /**
     * test client.
     *
     * @param args command line input
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
