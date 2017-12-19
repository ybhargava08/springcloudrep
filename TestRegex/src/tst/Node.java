package tst;

public class Node {

	Node left;
	Node middle;
	Node right;
	char val;
	boolean isWordEnd,isLeaf;
	
	boolean isMiddleTravered=false;
	boolean isLeftTraversed=false;
	boolean isRightTraversed=false;
	
	int arrayPos=0;
	//double size=0.0;	
	
public Node(char value){
	this.val = value;
}

}
