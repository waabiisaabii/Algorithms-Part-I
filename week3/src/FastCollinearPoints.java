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
 * FastCollinearPoints class.
 * @author Yachen Lin
 */
public class FastCollinearPoints {
    /**
     * store line segments
     */
    private LineSegment[] lineSegments;

    /**
     * store max index of lineSegments.
     */
    private int lineSegmentsIndex;

    /**
     * Finds all line segments containing 4 or more points.
     *
     * @param points finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        // time O(N^2 logN)
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

        // initialize linesegments
        lineSegments = new LineSegment[points.length * points.length];
        lineSegmentsIndex = 0;

        // sorting
        for (int i = 0; i < points.length; i++) {
            Point[] subPoints = new Point[points.length];
            int subPointsIndex = 0;
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                subPoints[subPointsIndex++] = q;
            }
            if (subPointsIndex < 3) {
                continue;
            }

            Arrays.sort(subPoints, 0, subPointsIndex, points[i].slopeOrder());

            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p.
            // If so, these points, together with p, are collinear.
            for (int idx = 0; idx < subPointsIndex - 3 + 1; idx++) {
                Point p = points[i];
                Point q = subPoints[idx], r = subPoints[idx + 1], s = subPoints[idx + 2];
                if (p == null || q == null || r == null || s == null) {
                    continue;
                }
                if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                    Point[] edges = findMinMax(p, q, r, s);
                    LineSegment lineSegment = new LineSegment(edges[0], edges[edges.length - 1]);
                    lineSegments[lineSegmentsIndex++] = lineSegment;

                }
            }

//            boolean breakFlag;
//            for (int left = 0; left < subPointsIndex; left++) {
//                breakFlag = false;
//                for (int right = subPointsIndex - 1; right > left + 2; right--) {
//                    Point p = points[i];
//                    if (p.slopeTo(subPoints[left]) == p.slopeTo(subPoints[right])) {
//                        Point[] edges = findMinMax(p, subPoints[left], subPoints[right]);
//                        LineSegment lineSegment = new LineSegment(edges[0], edges[edges.length - 1]);
//                        lineSegments[lineSegmentsIndex++] = lineSegment;
//                    }
//                }
//            }
        }

        // cut off extra space
        LineSegment[] newLineSegments = new LineSegment[lineSegmentsIndex];
        for (int i = 0; i < lineSegmentsIndex; i++) {
            newLineSegments[i] = lineSegments[i];
        }
        lineSegments = newLineSegments;
    }

    /**
     * Find edge points.
     *
     * @param p point p
     * @param q point q
     * @param r point r
     * @return left most and right most points
     */
    private Point[] findMinMax(Point p, Point q, Point r, Point s) {
        Point[] tmp = {p, q, r, s};
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
     * The number of line segments
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
