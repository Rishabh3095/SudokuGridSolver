package me;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	private int							nRecursions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
	
	// Returns if grid is illegal or a legal solutions, otherwise recurses on all possible extensions from the arg.
	private void solveRecurse(Grid grid)
	{		
		nRecursions++;
		if (nRecursions % 100_000  == 0)
			System.out.println(new Date() + "\n" + grid + "\n-----------------");
		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
			return;
		else if (eval == Evaluation.ACCEPT)
			solutions.add(grid);
		else
		{
			for (Grid next: grid.next9Grids())
				solveRecurse(next);
		}
	}
	
	
	public Evaluation evaluate(Grid grid)
	{
		if (!grid.isLegal())
			return Evaluation.ABANDON;
		else if (grid.isFull())
			return Evaluation.ACCEPT;
		else
			return Evaluation.CONTINUE;
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		System.out.println(new Date());
		Grid g = TestGridSupplier.getInkala();
		System.out.println(g);
		Solver solver = new Solver(g);
		solver.solve();
		System.out.println("===========================");
		System.out.println("      SOLUTION");
		System.out.println("===========================");
		System.out.println(solver.getSolutions().get(0));
		System.out.println(new Date());
	}
}
