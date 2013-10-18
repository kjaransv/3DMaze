import java.awt.Point;
import java.util.*;

public class Cell {
	private boolean FWestWall;
	private boolean FSouthWall;
	
	public int FX, FY;
	
	public Cell(boolean AWestWall, boolean ASouthWall, int AX, int AY){
		FWestWall = AWestWall;
		FSouthWall = ASouthWall;
		
		FX = AX;
		FY = AY;
	}
	
	public boolean WestWall(){
		return FWestWall;
	}
	
	public boolean SouthWall(){
		return FSouthWall;
	}
	
	public static Cell[][] kruskalMaze()
	{
		int counter = 0;
		Cell[][] result = new Cell[20][10];
		int[][] trees = new int[20][10];
		
		LinkedList<Point> walls = new LinkedList<Point>();
		
		for (int i=0; i<result.length; i++){
			for (int j=0; j<result[i].length; j++){
				trees[i][j] = counter++;
				
				result[i][j] = new Cell(true,true, i, j);
				
				if (i>0)
					walls.add(new Point(i,j));
				if (j>0)
					walls.add(new Point(i,-j));
				
			}
		}
				
		
		while(!walls.isEmpty())
		{
			int randomWall = (int)(Math.random() * walls.size());
			
			Point wall = walls.remove(randomWall);
			
			//west
			if(wall.y > 0)
			{
				if(trees[wall.x][wall.y] != trees[wall.x -1][wall.y])
				{
					combine(trees, wall.x, wall.y, wall.x -1, wall.y);
					result[wall.x][wall.y].FWestWall = false;
				}
			}
			//south
			else
			{
				if(wall.y == 0) continue;
				if(trees[wall.x][-wall.y] != trees[wall.x][-wall.y - 1])
				{
					combine(trees, wall.x, -wall.y, wall.x, -wall.y - 1);
					result[wall.x][-wall.y].FSouthWall = false;
				}
			}	
		}	
		
		return result;
	}
	
	public static void combine(int[][] tree, int x1, int y1, int x2, int y2)
	{
		int temp = tree[x2][y2];
		
		tree[x2][y2] = tree[x1][y1];
		
		if(x2 > 1 && tree[x2 - 1][y2] == temp)
		{
			combine(tree,x2,y2,x2-1,y2);
		}
		if(y2 < tree[0].length-2 && tree[x2][y2 + 1] == temp)
		{
			combine(tree,x2,y2,x2,y2+1);
		}
		if(x2 < tree.length - 2 && tree[x2 + 1][y2] == temp)
		{
			combine(tree,x2,y2,x2+1,y2);
		}
		if(y2 > 1 && tree[x2][y2 - 1] == temp)
		{
			combine(tree,x2,y2,x2,y2 - 1);
		}
	}
}
