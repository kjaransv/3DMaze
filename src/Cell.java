import java.awt.Point;
import java.util.*;

public class Cell {
	private boolean FEastWall;
	private boolean FSouthWall;
	
	public int FX, FY;
	
	public Cell(boolean AEastWall, boolean ASouthWall, int AX, int AY){
		FEastWall = AEastWall;
		FSouthWall = ASouthWall;
		
		FX = AX;
		FY = AY;
	}
	
	public boolean EastWall(){
		return FEastWall;
	}
	
	public boolean SouthWall(){
		return FSouthWall;
	}
	
	public static Cell[][] kruskalMaze(int AWidth, int AHeight, Point[] ADeadEnds)
	{
		int counter = 0;
		Cell[][] result = new Cell[AWidth][AHeight];
		int[][] trees = new int[AWidth-1][AHeight-1];
		
		LinkedList<Point> walls = new LinkedList<Point>();
		
		for (int i=0; i<AWidth; i++){
			for (int j=0; j<AHeight; j++){
				result[i][j] = new Cell(true, true, i, j);
				
				if (i<AWidth-1 && j<AHeight-1){
					trees[i][j] = counter++;
					
					boolean deadend = false;
					boolean entre = false;
					for (int k=0; k<ADeadEnds.length; k++){
						if (ADeadEnds[k].x == i && ADeadEnds[k].y == j){
							deadend = true;
							break;
						} else if (ADeadEnds[k].x+1 == i && ADeadEnds[k].y == j){
							entre = true;
						}
					}
					
					if (!deadend){
						if (i>0 && !entre) // east wall
							walls.add(new Point(i, j));
						if (j>0) // south wall
							walls.add(new Point(i, -j));
					}
				}
			}
		}
		
		while(!walls.isEmpty())
		{
			int randomWall = (int)(Math.random() * walls.size());
			
			Point wall = walls.remove(randomWall);
			
			//east
			if(wall.y >= 0)
			{
				if(trees[wall.x][wall.y] != trees[wall.x-1][wall.y])
				{
					combine(trees, wall.x, wall.y, wall.x-1, wall.y);
					result[wall.x][wall.y].FEastWall = false;
				}
			}
			//south
			else
			{
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
		
		if(x2 > 0 && tree[x2 - 1][y2] == temp) combine(tree,x2,y2,x2-1,y2);
		if(x2 < tree.length - 1 && tree[x2 + 1][y2] == temp) combine(tree,x2,y2,x2+1,y2);
		if(y2 > 0 && tree[x2][y2 - 1] == temp) combine(tree,x2,y2,x2,y2 - 1);
		if(y2 < tree[0].length-1 && tree[x2][y2 + 1] == temp) combine(tree,x2,y2,x2,y2+1);
	}
	
	public static Cell[][] ExampleMaze(int AWidth, int AHeight){
		Cell[][] result = new Cell[AWidth][AHeight];
		
		for (int i=0; i<AWidth; i++){
			for (int j=0; j<AHeight; j++){
				boolean w = i == 0 || i == AWidth-1;
				boolean s = j == 0 || j == AHeight-1;
				
				result[i][j] = new Cell(w, s, i, j);
			}
		}
		
		result[5][3].FSouthWall = true;
		result[3][5].FEastWall = true;
		
		return result;
	}
}
