import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class QTree
{
	
	
	Scanner in;
	PrintStream out;
	QNode root;
	
    //initializes the game
	public QTree(InputStream in,PrintStream out)
	{
		this.out=out;
		this.in=new Scanner(in);
		
		root = new QNode("Is it alive?", null, null, null);
		root.yes = new QNode("Is it a Duck?", null, null, root);
		root.no = new QNode("Is it a Rock?", null, null, root);
	}
	
    
	private class QNode {
		String val;
		QNode yes;
		QNode no;
		QNode parent;
		
		public QNode(String val, QNode left, QNode right, QNode parent) {
			this.val = val;
			this.yes = left;
			this.no = right;
			this.parent = parent;
		}
	}
	
	public void swap(QNode a, QNode b, boolean yes) {
		QNode curr = a;
		
		a.val = b.val;
		a.yes = null;
		a.no = null;
		a.parent = b;
		
		b.val = curr.val;
		b.parent = curr.parent;
		
		if(yes) {
			b.yes = a;
			b.no = curr.no;
		}
		
		else
		{
			b.yes = curr.yes;
			b.no = a;
		}
	}
	
	public void insert(QNode a, String question, boolean yes) {
		if(yes) {
			a.yes = new QNode(question, null, null, a);
			swap(a, a.yes, true);
		}
		else {
			a.no = new QNode(question, null, null, a);
			swap(a, a.no, false);
		}
	}
	
	public QNode yesno(QNode curr, String scanned) {
		if(scanned == "Y" || scanned == "y") {
			return curr.yes;
		}
		else {
			return curr.no;
		}
	}
	
    //plays the game, be sure to grab input from the Scanner "in", and send your output to "out".
	public void playGame()
	{
		boolean end = false;
		out.print(root.val);
		String scanned = in.next();
		QNode curr = yesno(root, scanned);
		playround(curr);
		
        return;
	}
	
	
	private void playround(QNode node) {
		
	}
	
	public static void main(String[] args)
	{
		QTree t = new QTree(System.in, System.out);
		t.playGame();
	}
	
	
}
