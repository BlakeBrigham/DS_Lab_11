import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class QTree
{
	public static final String ANSWERING_YES = "Answering yes to ";
	
	
	Scanner in;
	PrintStream out;
	QNode root;
	
    //initializes the game
	public QTree(InputStream in,PrintStream out)
	{
		this.out=out;
		this.in=new Scanner(in);
		
		root = new QNode("Is it alive?", null, null, null);
		root.yes = new QNode("Duck", null, null, root);
		root.no = new QNode("Rock", null, null, root);
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
	
	public void swap(QNode a, QNode b, boolean yes, boolean parentyes) {
		QNode curr = new QNode(a.val, a.yes, a.no, a.parent);
		QNode parent = curr.parent;
		
		a.yes = b.yes; 
		a.no = b.no;
		a.parent = b; //is tree parent to duck
		b.parent = parent;
		
		if(parentyes) 
		{
			b.no = a;
			if(yes) 
			{
				parent.yes = b;
			}
			else 
			{
				parent.no = b;
			}
		}
		
		else
		{
			b.yes = a;
			if(yes) 
			{
				parent.yes = b;
			}
			else 
			{
				parent.no = b;
			}
		}
	}
	
	public void insert(QNode a, String question, String answer, boolean yes, boolean parentyes) {
		if(yes) {
			a.no = new QNode(question, null, null, a);
			swap(a, a.no, yes, parentyes);
			a.parent.yes = new QNode(answer, null, null, a.parent);
			
			
		}
		else {
			a.yes = new QNode(question, null, null, a);
			swap(a, a.yes, yes, parentyes);
			a.parent.no = new QNode(answer, null, null, a.parent);
		}
	}
	
	public QNode yesno(QNode curr, String scanned) {
		if(scanned.equals("Y") || scanned.equals("y")) {
			return curr.yes;
		}
		else {
			return curr.no;
		}
	}
	
	public boolean strYes(String scanned)
	{
		if(scanned.equals("Y") || scanned.equals("y")) {
			return true;
		}
		else {
			return false;
		}
	}
	
    //plays the game, be sure to grab input from the Scanner "in", and send your output to "out".
	public void playGame()
	{
		out.println(root.val);
		String scanned = in.nextLine();
		QNode curr = yesno(root, scanned);
		playround(curr, strYes(scanned));
		
        return;
	}
	
	
	private void playround(QNode node, boolean yes) {
		if (node.yes == null && node.no == null)
		{
			out.println(Strings.IS_IT_A + node.val + "?");
		}
		else
		{
			out.println(node.val);
		}
		
		String answer;
		String question;
		String scanned = in.nextLine();
		boolean scanyes = strYes(scanned);
		QNode curr = yesno(node, scanned);
		if (scanyes && curr == null) 
		{
			out.println(Strings.I_WIN);
		}
		else if (!scanyes && curr == null)
		{
			out.println(Strings.WHAT_IS_THE_ANSWER);
			answer = in.nextLine();
			out.println(Strings.NEW_QUESTION + node.val + " and a " + answer);
			question = in.nextLine();
			out.println(ANSWERING_YES + question + " means "+ answer + "?");
			scanned = in.nextLine();
			insert(node, question, answer, strYes(scanned), yes);
			out.println(Strings.THANKS);
		}
		else if (scanyes)
		{
			playround(node.yes, scanyes);
		}
		else
		{
			playround(node.no, scanyes);
		}
		
		out.println(Strings.PLAY_AGAIN);
		scanned = in.nextLine();
		if (strYes(scanned))
		{
			playGame();
			return;
		}
		else
		{
			return;
		}
		
	}
	
	public static void main(String[] args)
	{
		QTree t = new QTree(System.in, System.out);
		t.playGame();
	}
	
	
}
