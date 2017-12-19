package tststring;

public class NodeString {
	NodeString left;
	NodeString middle;
	NodeString right;
	StringBuilder val=null;
	boolean isWordEnd,isLeaf;
	int arraypos=0;
	
	/*boolean isMiddleTravered=false;
	boolean isLeftTraversed=false;
	boolean isRightTraversed=false;*/
	
public NodeString(char value){
		addCharToSB(value);
	}

public NodeString(String value){
	addStringToSB(value);
}

public void addStringToSB(String value){
	if(null==this.val){
		this.val=new StringBuilder();
	}
	this.val.append(value);

}

public void addCharToSB(char value){
	if(null==this.val){
		this.val=new StringBuilder();
	}
	this.val.append(value);

}

}
