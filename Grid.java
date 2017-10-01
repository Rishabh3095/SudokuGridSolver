package me;

import java.util.*;

// THE FOLLOWING IS THE TWO LOOP METHOD. AFTER THIS CLASS, THE THREE LOOP SOLUTION IS PRESENT AS COMMENTS IN THE END.
public class Grid {
private int[][] values;

// See TestGridSupplier for examples of input.
// Dots in input strings become 0s in values[][].
// Constructs a grid with an array of rows
public Grid(String[] rows) {
    values = new int[9][9];
    for (int j = 0; j < 9; j++) {
        String row = rows[j];
        char[] charray = row.toCharArray();
        for (int i = 0; i < 9; i++) {
            char ch = charray[i];
            if (ch != '.')
                values[j][i] = ch - '0';
        }
    }
}

// Copies grid into a new grid
public Grid(Grid src) {
    values = new int[src.values.length][src.values[0].length];
    for (int i = 0; i < values.length; i++) {
        for (int j = 0; j < values[0].length; j++) {
            values[i][j] = src.values[i][j];
        }
    }
}

// a method to change an array to a string
public String toString() {
    String s = "";
    for (int j = 0; j < 9; j++) {
        for (int i = 0; i < 9; i++) {
            int n = values[j][i];
            if (n == 0)
                s += '.';
            else
                s += (char) ('0' + n);
        }
        s += "\n";
    }
    return s;
}

// Gets the next empty cell in grid
private int[] getIndicesOfNextEmptyCell() {
    int[] yx = new int[2];

    for (yx[0] = 0; yx[0] < values.length; yx[0]++)
        for (yx[1] = 0; yx[1] < values.length; yx[1]++)
            if (values[yx[0]][yx[1]] == 0)
                return yx;

    return null;
}

// Finds an empty member of values[][]. Returns an array list of 9 grids
// that look like the current grid,
// except the empty member contains 1, 2, 3 .... 9. Returns null if the
// current grid is full.
//
// Example: if this grid = 1........
// .........
// .........
// .........
// .........
// .........
// .........
// .........
// .........
//
// Then the returned array list would contain:
//
// 11....... 12....... 13....... 14....... and so on 19.......
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// ......... ......... ......... ......... .........
// Generates the next 9 grids with digits 1-9
public ArrayList<Grid> next9Grids() {
    int[] indicesOfNext = getIndicesOfNextEmptyCell();
    ArrayList<Grid> nextGrids = new ArrayList<>();

    for (int n = 1; n <= values.length; n++) {
        Grid grid = new Grid(this);
        grid.values[indicesOfNext[0]][indicesOfNext[1]] = n;
        nextGrids.add(grid);
    }

    return nextGrids;
}

// Returns true if the input list contains a repeated value that isn't zero.
private boolean containsNonZeroRepeats(ArrayList<Integer> ints) {
    // remove 0
    ArrayList<Integer> remove = new ArrayList<Integer>();
    remove.add(0);
    ints.removeAll(remove);

    HashSet<Integer> noDuplicates = new HashSet<Integer>(ints);
    // If sizes don't differ than there we no non zero repeats
    if (ints.size() == noDuplicates.size()) {
        return true;
    }
    return false;
}

// Returns true if this grid is legal. A grid is legal if no row, column, or
// zone contains
// a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
//
// THIS IS THE TWO LOOP METHOD
public boolean isLegal() {

    // checks the rows
    for (int i = 0; i < values.length; i++) {
        ArrayList<Integer> row = new ArrayList<Integer>();
        for (int j = 0; j < values[0].length; j++) {
            row.add(values[i][j]);
            if (row.size() == values[0].length) {
                if (!containsNonZeroRepeats(row)) {
                    return false;
                }
            }
        }
    }

    // Checks the columns
    for (int j = 0; j < values[0].length; j++) {
        ArrayList<Integer> column = new ArrayList<Integer>();
        for (int i = 0; i < values.length; i++) {
            column.add(values[i][j]);
            if (column.size() == values.length) {
                if (!containsNonZeroRepeats(column)) {
                    return false;
                }
            }
        }
    }

     //Checks different zones of the Sudoku Grid

    ArrayList<Integer> y = new ArrayList<Integer>();
    int m = 3;
    int k = 3;
    int j = 0;
    int a = 0;
    int b = 0;
    int count = 0;
    for (int i = a; i < m; i++) {
        if(i == 9)
        {
            break;
        }
        for (j = b; j < k; j++) {

            y.add(values[i][j]);
            count++;
        }
        ArrayList<Integer> mo;
        if (y.size() == 9) {
            mo = new ArrayList<>(y);
            if (!containsNonZeroRepeats(mo)) {
                return false;
            }
            y.clear();

            m = m + 3;

            count = 0;
            if (m > 9 && j == 3) {
                m = 3;
                b = 3;
                a = 0;
                k = k + 3;
                i=-1;
               
                count = 0;
            } else if (m > 9 && j == 6) {
                m = 3;
                b=6;
                a = 0;
                k = k + 3;
               
                count = 0;
                i = -1;
            }

        } else if (count == 9 && y.size() == 8) {
            return false;
        }
        //System.out.println(y);

    }
    return true;
}

// Returns true if every cell member of values[][] is a digit from 1-9.
public boolean isFull() {
    for (int i = 0; i < values.length; i++) {
        for (int j = 0; j < values[0].length; j++) {
            if (values[i][j] < 1 || values[i][j] > 9) {
                return false;
            }
        }
    }
    return true;
}

// Returns true if x is a Grid and, for every (i,j)
public boolean equals(Grid x) {
    Grid that = (Grid) x;

    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (that.values[i][j] == this.values[i][j]) {
                return true;
            }
        }
    }

    return false;
}
}


//THIS IS THE SOLUTION FOR THREE LOOPS. ABOVE THE SOLUTION IS MADE IN TWO LOOPS.
/*
public class Grid 
{
	private int[][]						values;
	
	
	//
	// Ctor for converting string array to a Grid instance. Intended for use with
	// puzzles and test cases generated by TestGridSupplier.  See TestGridSupplier for 
	// examples of input format. Dots in input strings become 0s in values[][].
	//
	Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// Copy ctor. Duplicates its source.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}
	
	
	// Finds indices of a cell that contains 0. Returns indices in a size=2 array.
	private int[] yxOfNextEmptyCell()
	{
		int[] yx = new int[2];
		
		for (yx[0]=0; yx[0]<9; yx[0]++)
			for (yx[1]=0; yx[1]<9; yx[1]++)
				if (values[yx[0]][yx[1]] == 0)
					return yx;
		
		return null;
	}
	
	
	// Finds an empty cell. Returns a list of 9 Grid instances that are identical to
	// this instance except that the empty cell has been filled with 1, 2, through 9.
	ArrayList<Grid> next9Grids()
	{		
		int[] yxOfNext = yxOfNextEmptyCell();
		int xOfNext = yxOfNext[0];
		int yOfNext = yxOfNext[1];
		
		ArrayList<Grid> next9 = new ArrayList<Grid>();
		
		for (int x=1; x<=9; x++)
		{
			Grid nextGrid = new Grid(this);
			nextGrid.values[xOfNext][yOfNext] = x;
			next9.add(nextGrid);
		}
		
		return next9;
	}
	
	
	//
	// Returns true if the input array contains a repeated value in the range [1..9]. Sets
	// observed[i] to true the first time any i is observed; returns true the second time
	// any i is observed.
	//
	private boolean containsRepeated1Thru9(int[] ints)
	{
		boolean[] observed = new boolean[10];
		
		for (int i: ints)
		{
			if (i == 0)
				continue;				// repeated 0s are ok, so ignore 0s
			else if (observed[i])
				return true;			// i has been seen before
			else
				observed[i] = true;		// first observation of i
		}
		return false;
	}
	
	
	//
	// Returns true if this Grid is legal, i.e. no repeated values in any row,
	// column, or zone.
	//
	boolean isLegal()
	{
		// Check all rows.
		for (int j=0; j<9; j++)
		{
			int[] ints = new int[9];
			for (int i=0; i<9; i++)
				ints[i] = values[j][i];
			if (containsRepeated1Thru9(ints))
				return false;
		}
		
		// Check all cols.
		for (int j=0; j<9; j++)
		{
			int[] ints = new int[9];
			for (int i=0; i<9; i++)
				ints[i] = values[i][j];
			if (containsRepeated1Thru9(ints))
				return false;
		}
		
		// Check all zones. The two outer loops generate j = { 0, 3, 6 } and i = { 0, 3, 6 }.
		// These i,j pairs are the upper-left corners of each zone. The two inner loops compute
		// all 9 index pairs for the cells in the zone defined by i,j.
		for (int j=0; j<9; j+=3)
		{
			for (int i=0; i<9; i+=3)
			{
				int[] ints = new int[9];
				int n = 0;
				for (int jj=j; jj<j+3; jj++)
				{
					for (int ii=i; ii<i+3; ii++)
					{
						ints[n++] = values[jj][ii];
						if (containsRepeated1Thru9(ints))
							return false;
					}
				}
			}
		}
		
		return true;
	}
	
	
	public boolean isFull()
	{
		int nFilled = 0;
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				if (values[j][i] != 0)
					nFilled++;
		return nFilled == 81;
	}
	
	
	public boolean equals(Object x)
	{
		Grid that = (Grid)x;
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (this.values[i][j] != that.values[i][j])
					return false;
		return true;
	}
	
	
	// Not part of the assignment, but I found this useful for debugging.
	int nFull()
	{
		int n = 0;
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				if (values[i][j] >0)
					n++;
			}
		}
		return n;
	}
	
	
	// I used this to convert a grid solution to Java code that defines an array
	// of strings. I used it to generate the SOLUTION arrays in TestGridSupplier.
	String toArrayDef(String name)
	{
		String s = "private final static String[]       " + name + " =\n    {\n";
		for (int[] row: values)
		{
			s += "        \"";
			for (int i: row)
				if (i >= 1)
					s += "" + i;
				else
					s += ".";
			s += "\",\n";
		}
		s = s.trim();
		s = s.substring(0, s.length()-1);
		s = "    " + s;
		s += "\n    };\n";
		return s;
	}
	
	
	// Since Grid is not the main class of this app, you can put any test code you like in main().
	// Here I verify that next9Grids() works correctly for a simple case.
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getPuzzle1();
		System.out.println("ORIGINAL GRID:\n" + g);
		ArrayList<Grid> nexts = g.next9Grids();
		for (Grid next: nexts)
			System.out.println("-------\n" + next);
	}
	*/

